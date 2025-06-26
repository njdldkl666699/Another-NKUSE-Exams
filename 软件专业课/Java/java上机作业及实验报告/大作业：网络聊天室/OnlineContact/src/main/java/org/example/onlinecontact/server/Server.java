package org.example.onlinecontact.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.onlinecontact.client.login.Login;
import org.example.onlinecontact.mapper.MessageMapper;
import org.example.onlinecontact.mapper.UserMapper;
import org.example.onlinecontact.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


@Component // 标记为 Spring 组件
public class Server {
    public static final int PORT = 8888;
    private ServerSocket serverSocket;
    private volatile boolean isRunning = true;
    private HashMap<Integer, Socket> clients = new HashMap<>();


    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    Login login;
    @PostConstruct // Spring 容器初始化后自动调用
    public void start() {
        System.out.println("服务端已创建");
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                System.out.println("TCP 服务器已启动，监听端口: " + PORT);
                while (isRunning) {
                    Socket socket = serverSocket.accept();
                    System.out.println(socket);
                    String loginString;
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while((loginString= in.readLine())==null){}
                    String[] s = loginString.split(" ");
                    User user=login(s[1],s[2]);
                    System.out.println("登陆成功：id="+user.getId());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(user);
                    out.flush();
                    if(user.getId()==-2){
                        System.out.println("登陆失败");
                        continue;
                    }

                    clients.put(user.getId(), socket);
                    new ClientHandlerThread(socket, clients,user.getId(),messageMapper,userMapper).start();
                }
            } catch (IOException e) {
                if (!isRunning) {
                    System.out.println("TCP 服务器已正常关闭");
                } else {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    @PreDestroy // Spring 容器销毁前自动调用
    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            // 关闭所有客户端连接
            for (Socket socket : clients.values()) {
                if (!socket.isClosed()) {
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User login(String username, String password) {
        return login.login(username, password);
    }
}
