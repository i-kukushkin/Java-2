package lesson5;

import java.util.Arrays;

/**
 * Java 2. Lesson 5. Homework
 *
 * @author Ilya Kukushkin
 * @version 4 Nov 2018
 */

public class MainClass {
    public static void main(String[] args) {
        final int SIZE = 10000000;
        final int NUMBER_OF_THREADS = 16;
        final float[] originalArray = new float[SIZE];
        for (int i = 0; i < originalArray.length; i++) {
            originalArray[i] = 1.0f;
        }
        /*
        Создаются копии оригинального массива для передачи в методы,
        иначе результаты вычисления записываются в оригинальный массив и результаты работы методов становятся не равны.
         */
        float[] arrayForSingleThread = Arrays.copyOf(originalArray, originalArray.length);
        float[] arrayForManyThreads = Arrays.copyOf(originalArray, originalArray.length);
        System.out.println("1 thread: " + doItSingleThread(arrayForSingleThread) + " ms");
        System.out.println(NUMBER_OF_THREADS + " threads: " + doItManyThreads(arrayForManyThreads, NUMBER_OF_THREADS) + " ms");
        System.out.println("Resulting arrays are equal? " + Arrays.equals(arrayForSingleThread, arrayForManyThreads));
    }

    public static long doItSingleThread(float[] arr) {
        long timestampStart = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long timestampStop = System.currentTimeMillis();
        return timestampStop - timestampStart;
    }

    /*
    Метод принимает на вход заданный массив и количество потоков
     */
    public static String doItManyThreads(float[] arr, int nubmerOfThreads) {
        // Проверка на степень 2
        if (nubmerOfThreads > 0 && (nubmerOfThreads & (nubmerOfThreads - 1)) == 0) {
            final int NUMBER_OF_THREADS_SIZE = arr.length / nubmerOfThreads;
            long timestampStart = System.currentTimeMillis();
            /*
            1. Создаем двумерный массив, количество строк равно количеству потоков, передаваемых в метод.
            2. В полученные массивы копируем данные из переданного в метод массива, учитывая позицию в массиве.
            */
            float[][] arrayForActions = new float[nubmerOfThreads][];
            for (int i = 0; i < arrayForActions.length; i++) {
                arrayForActions[i] = new float[NUMBER_OF_THREADS_SIZE];
                System.arraycopy(arr, (NUMBER_OF_THREADS_SIZE * i), arrayForActions[i], 0, NUMBER_OF_THREADS_SIZE);
            }
            /*
            1. Создаем массив потоков, в метод Runnable передаем условия домашнего задания.
            Обращаю Ваше внимание, что на основе оригинальной формулы результаты выполнения с помощью одного или нескольких потоков будут отличаться,
            т.к. один из множителей - позиция в массиве. В случае разбивки массива на несколько, этот множитель будет отличаться.
            В данном методе формула изменена с учетом разбивки на несколько массивов.
            2. В конце psvm добавлено сравнение массивов.
             */
            Thread[] threadsArray = new Thread[nubmerOfThreads];
            for (int i = 0; i < threadsArray.length; i++) {
                int numberOfInnerArray = i;
                threadsArray[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < arrayForActions[numberOfInnerArray].length; j++) {
                            arrayForActions[numberOfInnerArray][j] = (float) (arrayForActions[numberOfInnerArray][j] * Math.sin(0.2f + (j + NUMBER_OF_THREADS_SIZE * numberOfInnerArray) / 5)
                                    * Math.cos(0.2f + (j + NUMBER_OF_THREADS_SIZE * numberOfInnerArray) / 5) * Math.cos(0.4f + (j + NUMBER_OF_THREADS_SIZE * numberOfInnerArray) / 2));
                        }
                    }
                });
                threadsArray[i].start();
            }
            for (Thread t : threadsArray) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < arrayForActions.length; i++) {
                System.arraycopy(arrayForActions[i], 0, arr, (NUMBER_OF_THREADS_SIZE * i), NUMBER_OF_THREADS_SIZE);
            }
            long timestampStop = System.currentTimeMillis();
            return Long.toString(timestampStop - timestampStart);
        } else {
            return "Не степень двойки";
        }
    }
}
