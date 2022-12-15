package org.itstep;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Java. Lesson042. Task01
 * Синхронизация потоков (один увеличивает x, другой его печатает)
 *
 * @author Semenyuk Alexander
 * Date 14.12.2022
 * Задание 1
 * Предположим, что два потока «t1» и «t2» обращаются к общему целому числу «x».
 * Поток «t1» бесконечно увеличивает «x», а поток «t2» бесконечно печатает значение «x».
 * То есть потоки работают в бесконечном цикле. Однако «t1» не должно увеличивать «x» до тех пор,
 * пока это значение «x» не будет напечатано «t2», а «t2» не должно печатать одно и то же значение «x» более одного раза.
 * Определите классы для реализации «t1», «t2» и «x». Напишите соответствующие методы для этих классов.
 */
public class Task01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] count = {2};
        int num = 2;

        ExecutorService executorService = Executors.newFixedThreadPool(num);
        while (true) {
            Future<Integer> future = executorService.submit(() -> {
//                synchronized (count) {
                int number = count[0];
                number++;
                Thread.sleep(10);
                System.out.println(Thread.currentThread().getName() + ": is running");
//                }
                return number;
            });
            count[0] = future.get();
            Future<Integer> future1 = executorService.submit(() -> {
//                synchronized (count) {
                try {
                    System.out.println(Thread.currentThread().getName() + ": is running - count = " + count[0]);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                }
                return 1;
            });
            future1.get();
        }

    }
}
