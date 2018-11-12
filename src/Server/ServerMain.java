package Server;

import java.sql.SQLException;

/**
 * Java 2. Homework to Lesson 7.
 * @author Ilya Kukushkin
 * @version 12 Nov 2018
 */

public class ServerMain {
    public static void main(String[] args) {
        try {
            new Server();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
