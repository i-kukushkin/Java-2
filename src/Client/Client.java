package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String IP_ADDRESS = "localhost";
    private int PORT;
    Socket socket = null;
    DataInputStream in = null;
    DataOutputStream out = null;
    Scanner scanner = null;

    public Client(int PORT) {
        this.PORT = PORT;

        try {
            this.socket = new Socket(IP_ADDRESS, PORT);
            System.out.println("Connect to server: " + IP_ADDRESS + " port " + PORT);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.scanner = new Scanner(System.in);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("server: /end")) {
                                out.writeUTF("client: /end");
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
                            scanner.close();
                            socket.close();
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
                            out.writeUTF("client: " + msg);
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

