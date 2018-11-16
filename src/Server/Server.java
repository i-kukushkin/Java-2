package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Java 2. Homework to Lesson 7.
 *
 * @author Ilya Kukushkin
 * @version 12 Nov 2018
 */

public class Server {

    private Vector<ClientHandler> clients;
    private ServerSocket server = null;
    private Socket socket = null;

    public Server() throws SQLException {
        try {
            clients = new Vector<>();
            AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен...");
            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился...");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    /**
     * Проверка занятости nickname
     */
    public boolean nickIsBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) return true;
        }
        return false;
    }

    /**
     * Отправка whisper сообщения
     */
    public void sendPersonalMsg(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nickTo)) {
                if (!o.checkBlacklist(from.getNick())) {
                    o.sendMsg("(whisper) from " + from.getNick() + ": " + msg);
                    from.sendMsg("(whisper) to " + nickTo + ": " + msg);
                }
            }
            return;
        }
        from.sendMsg("Клиент с таким ником не найден.");
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    public void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler o : clients) {
            if (!o.checkBlacklist(from.getNick())) o.sendMsg(msg);
        }
    }
}
