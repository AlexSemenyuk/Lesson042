package org.itstep;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Java. Lesson042. Task02
 * Собрать из двухмерного массива одномерный при помощи потоков
 *
 * @author Semenyuk Alexander
 * Date 14.12.2022
 * Задание 2
 * Предположим, что общий двумерный массив чисел должен быть записан в общий одномерный массив.
 * Это должно быть выполнено с использованием 4 потоков, где каждый поток записывает один столбец
 * двумерного массива за раз в одномерный массив. Определите классы Java и основной метод для выполнения поставленной задачи.
 */
public class Task02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int row = 4;
        int col = 5;
        Random random = new Random();
        int[][] arr = new int[row][col];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = random.nextInt(20);
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.printf("%2d ", arr[i][j]);
                if (j == arr[i].length - 1) {
                    System.out.print("\n");
                }
            }
        }

        int num = 4;
        int[] result = new int[arr.length * arr[0].length];
        ExecutorService executorService = Executors.newFixedThreadPool(num);

        int[] j = {0};
        while (j[0] < arr[0].length) {
            Future<int[]> future = executorService.submit(() -> {
                int[] arr1 = new int[arr.length];
                synchronized (arr) {
                    for (int i = 0; i < arr.length; i++) {
                        arr1[i] = arr[i][j[0]];
                    }
                    Thread.sleep(10);
                }
                System.out.println(Thread.currentThread().getName() + ": is running");
                return arr1;
            });
            int[] rezultCol = future.get();
            for (int m = 0; m < rezultCol.length; m++) {
                result[j[0] * rezultCol.length + m] = rezultCol[m];
            }
            j[0]++;
        }


        System.out.println("result = " + Arrays.toString(result));
        executorService.shutdown();
    }
}
