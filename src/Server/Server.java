package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    final private int PORT;
    private Scanner scanner = null;
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Server(int PORT) {
        this.PORT = PORT;


        try {
            this.server = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            this.socket = server.accept();
            System.out.println("Client connected...");
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.scanner = new Scanner(System.in);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("client: /end")) {
                                break;
                            }
                            System.out.println(str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            System.out.println("Server stopped...");
                            in.close();
                            out.close();
                            socket.close();
                            server.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String msg = scanner.nextLine();
                            out.writeUTF("server: " + msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
