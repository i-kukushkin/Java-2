package lesson3;

import java.util.*;

/**
 * Java 2. Homework to Lesson 3.
 * @version 27 Oct 2018
 * @author Ilya Kukushkin
 *
 * 1. Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся).
 * Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
 * Посчитать, сколько раз встречается каждое слово.
 *
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
 * В этот телефонный справочник с помощью метода add() можно добавлять записи.
 * С помощью метода get() искать номер телефона по фамилии.
 * Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
 * тогда при запросе такой фамилии должны выводиться все телефоны.
 */

public class Main {
    public static void main(String[] args) {
        String[] wordsArray = {"Orange", "Apple", "Peach", "Melon", "Watermelon",
                "Plum", "Avocado", "Mago", "Qiwi", "Lime", "Apple",
                "Peach", "Watermelon", "Banana", "Cherry", "Apple",
                "Grapes", "Apricot", "Cherry", "Pineapple", "Coconut"};
        findUniqueWords(wordsArray);
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Ivanov", "8(916)111-11-11");
        phoneBook.add("ivanov", "+79261111111");
        phoneBook.add("Petrov", "84953333333");
        phoneBook.get("ivanov");
        phoneBook.get("Petrov");
        phoneBook.get("sidorov");
    }

    private static void findUniqueWords(String[] wordsArray) {
        Map<String, Integer> uniqueWords = new HashMap<>();
        for (String s : wordsArray) {
            Integer current = uniqueWords.get(s);
            uniqueWords.put(s, current == null ? 1 : current + 1);
        }
        System.out.println("Уникальных слов: " + uniqueWords.size());
        Set<Map.Entry<String, Integer>> uniqueWordsSet = uniqueWords.entrySet();
        for (Map.Entry<String, Integer> set : uniqueWordsSet) {
            System.out.println("Слово " + set.getKey() + " встречается " + set.getValue() + " раз(а).");
        }
    }
}

