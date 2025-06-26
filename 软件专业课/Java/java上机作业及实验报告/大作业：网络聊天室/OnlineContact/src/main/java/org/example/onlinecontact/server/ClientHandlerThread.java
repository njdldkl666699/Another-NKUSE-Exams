package org.example.onlinecontact.server;

import com.alibaba.fastjson2.JSONObject;
import org.example.onlinecontact.dto.MessageDTO;
import org.example.onlinecontact.mapper.MessageMapper;
import org.example.onlinecontact.mapper.UserMapper;
import org.example.onlinecontact.pojo.Response;
import org.example.onlinecontact.pojo.User;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandlerThread extends Thread {
    private final Socket socket;
    private final Map<Integer, Socket> clients;
    private final Integer clientId;
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;
    private static ConcurrentHashMap<Integer, PrintWriter> userMap;
    private volatile boolean isRunning = true; // 控制循环的状态标志
    static{
        userMap = new ConcurrentHashMap<>();
    }
    public ClientHandlerThread(
            Socket socket,
            Map<Integer, Socket> clients,
            Integer clientId,
            MessageMapper messageMapper,
            UserMapper userMapper) {
        this.socket = socket;
        this.clients = clients;
        this.clientId = clientId;
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
        this.setDaemon(true); // 设置为守护线程
        setName(clientId.toString());
    }

    @Override
    public void run() {

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);)
        {
            userMap.put(clientId, out);
            while (isRunning && !socket.isClosed()) { // 检查 Socket 是否已关闭

                String message = reader.readLine();
                if (message == null) { // 客户端主动关闭连接
                    continue;
                }
                String[] messages = message.split(" ");
                if(messages[0].equals("getUser"))
                {
                    List<User> users = selectUser(Integer.parseInt(messages[1]));
                    out.println(JSONObject.toJSONString(new Response(Response.ResponseType.USER_LIST,users)));
                    continue;
                }
                if(messages[0].equals("getmessage"))
                {
                    List<MessageDTO> messageDTOS=getMessage(Integer.parseInt(messages[1]),Integer.parseInt(messages[2]));
                    out.println(JSONObject.toJSONString(new Response(Response.ResponseType.MESSAGE_LIST,messageDTOS)));

                    continue;
                }
                if(messages[0].equals("sendMessage"))
                {
                    int targetUserId;
                    try {
                        targetUserId = Integer.parseInt(messages[2]);
                    } catch (NumberFormatException e) {
                        System.err.println("目标用户ID格式错误: " + messages[2]);
                        continue;
                    }

                    String content = messages[1];
                    MessageDTO messageDTO = buildMessageDTO(clientId, targetUserId, content);
                    messageMapper.insert(messageDTO); // 存储消息到数据库

                    // 转发消息给目标用户
                    Socket targetSocket = clients.get(targetUserId);
                    if (targetSocket != null && !targetSocket.isClosed()) {
                        PrintWriter printWriter = userMap.get(targetUserId);
                        printWriter.println(JSONObject.toJSONString(new Response(Response.ResponseType.SINGLE_MESSAGE,messageDTO.getSendUserId())));
                    } else {
                        System.out.println("目标用户 " + targetUserId + " 不在线");
                    }
                    System.out.println(userMap.get(targetUserId).toString());
                }

            }
        } catch (SocketException e) {
            System.out.println("客户端 " + clientId + " 连接异常断开: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("客户端 " + clientId + " 处理异常: " + e.getMessage());
        } finally {
            //closeResources();
        }
    }

    private List<MessageDTO> getMessage(int sendId, int getId) {
        return messageMapper.page(sendId, getId);
    }

    // 安全关闭资源
    private void closeResources() {
        isRunning = false; // 停止循环
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("关闭 Socket 失败: " + e.getMessage());
        }
        clients.remove(clientId); // 从在线列表中移除
        System.out.println("客户端 " + clientId + " 资源已释放");
    }

    // 构建消息DTO
    private MessageDTO buildMessageDTO(Integer senderId, Integer receiverId, String content) {
        return MessageDTO.builder()
                .sendUserId(senderId)
                .getUserId(receiverId)
                .message(content)
                .sendTime(LocalDateTime.now())
                .sendUsername(userMapper.getUsernameById(senderId))
                .getUsername(userMapper.getUsernameById(receiverId))
                .build();
    }
    private ArrayList<User> selectUser(Integer id) {
        return userMapper.getExcludeId(id);
    }
}
