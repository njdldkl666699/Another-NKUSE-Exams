package GameSimulation;
import java.io.*;
import java.net.*;
import java.util.concurrent.CyclicBarrier;

public class TwoClientServer {

    // 共享数据类，保存两个客户端的消息
    static class SharedData {
        static boolean ifFirst = true;
        private String client1Message;
        private String client2Message;
        private final CyclicBarrier barrier;
        private static Game2 game;
        public SharedData() {
            // 当两个线程到达屏障时，触发处理并回复
            this.barrier = new CyclicBarrier(2, this::processAndRespond);
        }

        public synchronized void setClient1Message(String msg) {
            this.client1Message = msg;
        }

        public synchronized void setClient2Message(String msg) {
            this.client2Message = msg;
        }

        // 处理消息并回复客户端
        private void processAndRespond() {
            String processed = "Processed: " + client1Message + " & " + client2Message;
            // 假设每个客户端处理程序持有自己的输出流
            if(ifFirst) {game=new Game2(client1Message,client2Message);ifFirst=false;}
            else{
                game.Play(client1Message,client2Message);
            }
            if(game.player1.ifdead()||game.player2.ifdead()) {processed="-1";
                System.out.println(game.player1);
                System.out.println(game.player2);
            }
            ClientHandler.client1Out.println(processed);
            ClientHandler.client2Out.println(processed);
            System.out.println("Sent response: " + processed);
        }
    }

    // 客户端处理线程
    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        public static PrintWriter client1Out;
        public static PrintWriter client2Out;
        private final SharedData sharedData;
        private final int clientId;

        public ClientHandler(Socket socket, SharedData sharedData, int clientId) {
            this.socket = socket;
            this.sharedData = sharedData;
            this.clientId = clientId;
            try {
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // 根据客户端ID保存输出流
                if (clientId == 1) {
                    client1Out = out;
                } else {
                    client2Out = out;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String message = in.readLine();
                    if (message == null) break; // 客户端断开连接

                    // 存储消息到共享数据
                    if (clientId == 1) {
                        sharedData.setClient1Message(message);
                    } else {
                        sharedData.setClient2Message(message);
                    }

                    System.out.println("Received from Client " + clientId + ": " + message);

                    // 等待另一个客户端的消息
                    sharedData.barrier.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SharedData sharedData = new SharedData();
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started. Waiting for clients...");

            // 等待第一个客户端连接
            Socket client1 = serverSocket.accept();
            System.out.println("Client 1 connected.");
            new Thread(new ClientHandler(client1, sharedData, 1)).start();

            // 等待第二个客户端连接
            Socket client2 = serverSocket.accept();
            System.out.println("Client 2 connected.");
            new Thread(new ClientHandler(client2, sharedData, 2)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}