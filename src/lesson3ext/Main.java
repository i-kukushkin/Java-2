package lesson3ext;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java 2. Extended homework to Lesson 3.
 * @author Ilya Kukushkin
 * @version 27 Oct 2018
 * Необходимо из консоли считать пароль и проверить валидность,
 * результат будет true или false
 * Требования:
 * 1. Пароль должен быть не менее 8ми символов.
 * 2. В пароле должно быть число
 * 3. В пароле должна быть хотя бы 1 строчная буква
 * 4. В пароле должна быть хотя бы 1 заглавная буква
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("1. Пароль должен быть не менее 8ми символов\n" +
                "2. В пароле должно быть число\n" +
                "3. В пароле должна быть хотя бы 1 строчная буква\n" +
                "4. В пароле должна быть хотя бы 1 заглавная буква\n" +
                "Введите пароль для проверки соответствия условиям:");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        System.out.println(checkPassword(password));
    }

    public static boolean checkPassword(String password) {
        return checkPasswordNumber(password) && checkPasswordUpperCase(password) &&
                checkPasswordLowerCase(password) && checkPasswordLength(password);
    }

    public static boolean checkPasswordNumber(String password) {
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(password);
        return m.find();
    }

    public static boolean checkPasswordUpperCase(String password) {
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(password);
        return m.find();
    }

    public static boolean checkPasswordLowerCase(String password) {
        Pattern p = Pattern.compile("[a-z]");
        Matcher m = p.matcher(password);
        return m.find();
    }

    public static boolean checkPasswordLength(String password) {
        return password.length() >= 8;
    }
}
