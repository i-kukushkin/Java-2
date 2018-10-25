package lesson2;

/**
 * Java 2. Homework to Lesson 2.
 * @author Ilya Kukushkin
 * @version 25 Oct 2018
 */

public class Main {

    public static void main(String[] args) {
        /*
        Для проверки исключения MyArraySizeException необходимо изменить значение константы ROWS или COLUMNS
         */
        final int ROWS = 4;
        final int COLUMNS = 4;
        String[][] strArray = new String[ROWS][COLUMNS];
        try {
            checkArraySize(strArray);
            fillArray(strArray);
            showArray(strArray);
        /*
        Для проверки исключения MyArrayDataException необходимо убрать комментарий у следующей строки
         */
//            strArray[3][2] = "abc";
            System.out.println("\nСумма всех элементов массива равна " + sumArrayElements(strArray));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    private static void checkArraySize(String[][] strArray) throws MyArraySizeException {
        final String ARRAY_SIZE_ERROR = "Размер переданного массива отличается от [4][4]!";
        boolean arrayCheck = strArray.length == 4 && strArray[0].length == 4;
        if (!arrayCheck) throw new MyArraySizeException(ARRAY_SIZE_ERROR);
    }

    private static int sumArrayElements(String[][] strArray) throws MyArrayDataException {
        final String ARRAY_SIZE_ERROR = "Преобразование в число невозможно!";
        int count = 0;
        for (int i = 0; i < strArray.length; i++) {
            for (int j = 0; j < strArray[i].length; j++) {
                if (!isParse(strArray[i][j])) throw new MyArrayDataException(ARRAY_SIZE_ERROR, i, j);
                count += Integer.parseInt(strArray[i][j]);
            }
        }
        return count;
    }

    private static void fillArray(String[][] strArray) {
        for (int i = 0; i < strArray.length; i++) {
            for (int j = 0; j < strArray[i].length; j++) {
                strArray[i][j] = Integer.toString((int) (Math.random() * 10));
            }
        }
    }

    private static void showArray(String[][] strArray) {
        for (int i = 0; i < strArray.length; i++) {
            for (int j = 0; j < strArray[i].length; j++) {
                System.out.print(strArray[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private static boolean isParse(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
