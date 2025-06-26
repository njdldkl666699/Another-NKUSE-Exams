package org.example.onlinecontact.client;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.example.onlinecontact.dto.MessageDTO;
import org.example.onlinecontact.exception.LoginErrorException;
import org.example.onlinecontact.frame.MainFrame;
import org.example.onlinecontact.pojo.Response;
import org.example.onlinecontact.pojo.User;


import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Client {
    public static final String serverAddress = "localhost";
    public static final int serverPort = 8888;
    public Socket clientSocket;
    public User user;

    public MainFrame mainFrame;

    private final BlockingQueue<List<MessageDTO>> messageQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<User>> userQueue = new LinkedBlockingQueue<>();

    public Client(String userName,String password,MainFrame mainFrame) throws IOException, ClassNotFoundException {


        clientSocket = new Socket(serverAddress, serverPort);
        this.mainFrame = mainFrame;
        String login="login "+userName+" "+password;

        PrintWriter pw=new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);
        pw.println(login);
        User user=null;
        ObjectInputStream ois=new ObjectInputStream(clientSocket.getInputStream());

        user=(User) ois.readObject();
        if(user.getId()==-2) throw new LoginErrorException("密码错误");
        this.user = user;
        Thread thread=new Thread(()->{
            try(BufferedReader br=new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
            {
               while(true){
                   String json=br.readLine();
                   Response response=JSONObject.parseObject(json,Response.class);
                   System.out.println(response.getData().getClass());
                   switch(response.getType())
                   {
                       case SINGLE_MESSAGE -> mainFrame.getMessage((Integer)response.getData());
                       case MESSAGE_LIST -> {
                           List<MessageDTO> messages = JSONArray.parseArray(response.getData().toString(), MessageDTO.class);
                           messageQueue.put(messages);
                       }
                       case USER_LIST -> {
                           List<User> userList =  JSONArray.parseArray(response.getData().toString(),User.class);
                           userQueue.put(userList);
                       }
                   }
               }
            }
            catch(IOException e){
                System.out.println(clientSocket.isClosed());
                e.printStackTrace();
                //throw new RuntimeException();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setName(user.getUserName());
        thread.setDaemon(true);
        thread.start();

    }
    public Integer getId() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        String login="login "+username+" "+password;
        PrintWriter pw=new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);
        pw.println(login);
        User user=null;
        ObjectInputStream ois=new ObjectInputStream(clientSocket.getInputStream());
        while((user= (User) ois.readObject())==null){}
        if(user.getId()==-2) throw new LoginErrorException("密码错误");
        return user.getId();
    }

    public List<User> getUser()  {
        try
        {
            PrintWriter pw=new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);
            pw.println("getUser "+user.getId());

            return userQueue.take();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MessageDTO> getMessage(Integer nowId) {
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            pw.println("getmessage " + user.getId() + " " + nowId);

            // 阻塞直到队列中有数据
            return messageQueue.take();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String s) {
        try{
            PrintWriter pw=new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);
            pw.println(s);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

