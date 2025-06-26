package GameSimulation;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);//socket
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//socket的输入流
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);//socket输出流
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {//键盘输入

            System.out.println("Connected to server. Enter messages:");

            // 启动线程监听服务器响应
            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("Server response: " + response);

                        // 检测到服务端发送 "-1" 时关闭客户端
                        if ("-1".equals(response)) {
                            System.out.println("Server requested termination. Closing client.");
                            socket.close(); // 关闭 Socket 触发主线程退出

                            throw new IOException();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // 发送用户输入的消息
            String input;
            while ((input = userInput.readLine()) != null) {
                out.println(input);
            }

        } catch (IOException e) {
            // 捕获到 Socket 关闭的异常后正常退出
            System.out.println("Client terminated.");
        }
    }
}
