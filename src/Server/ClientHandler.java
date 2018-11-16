package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Java 2. Homework to Lesson 7.
 *
 * @author Ilya Kukushkin
 * @version 12 Nov 2018
 */

public class ClientHandler {

    private Socket socket = null;
    private DataInputStream in;
    private DataOutputStream out;
    private Server server;
    private String nick;
    private ArrayList<String> blacklist;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.blacklist = new ArrayList<>();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null) {
                                    nick = newNick;
                                    /* Блок проверки занятости nickname */
                                    if (!server.nickIsBusy(nick)) {
                                        sendMsg("/authok");
                                        server.subscribe(ClientHandler.this);
                                        break;
                                    } else {
                                        sendMsg("Данный пользователь уже вошел в чат!");
                                    }
                                } else {
                                    sendMsg("Неверный логин/пароль!");
                                }
                            }
                            if (str.equals("/checkFill")) {
                                sendMsg("Нужно заполнить оба поля (логин/пароль)!");
                            }
                        }
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.equals("/end")) {
                                    out.writeUTF("/serverClosed");
                                    break;
                                }
                                if (str.startsWith("/w ")) {
                                    String[] tokens = str.split(" ", 3);
                                    server.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                                }
                                if (str.startsWith("/blacklist")) {
                                    String[] tokens = str.split(" ");
                                    blacklist.add(tokens[1]);
                                    sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список.");
                                }
                            } else {
                                server.broadcastMsg(ClientHandler.this, nick + ": " + str);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        server.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkBlacklist(String nick) {
        return blacklist.contains(nick);
    }

    public String getNick() {
        return nick;
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
