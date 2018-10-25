package lesson2;

public class MyArrayDataException extends Exception {

    public MyArrayDataException(String errorMsg, int i, int j) {
        super(errorMsg + " Преобразование невозможно в ячейке [" + i + "][" + j + "]!");
    }

}
