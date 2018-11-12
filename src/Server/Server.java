package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

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

    public boolean nickIsBusy(String nick) {
        boolean isBusy = false;
        for (ClientHandler o : clients) {
            isBusy = o.getNick().equals(nick);
        }
        return isBusy;
    }

    public void whisperMsg(String nick, String msg) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) o.sendMsg(msg);
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
}
