package home_works.lesson_5_Threads;

import java.util.Arrays;

public class HomeWork_lesson5 {
    public static int size = 200_000_000;

    public static void main(String[] args) {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);

        calculateArrThread(arr, 1);                      //выполняем вычисление в 1 потоке для всего массива
        float[] referenceArr = Arrays.copyOf(arr, arr.length);        //сохраняем эталонный массив для проверки последующих вычислений
        checkAndRefresh(arr, referenceArr);                         //проверка массивов и заполнения исходного единицами
        testWithThreads(arr, referenceArr, 2, 4, 8, 16);   // выполянем вычисления с разделением на потоки


    }

    private static void testWithThreads(float[] arr, float[] referenceArr, Integer... threadCounts) {
        for (int i = 0; i < threadCounts.length; i++) {
            calculateArrThread(arr, threadCounts[i]);                           //вычисления в одном массиве
            checkAndRefresh(arr, referenceArr);                  //проверка корректности вычислений (сравнение с эталоном)
            calculateArrThreadSplitArray(arr, threadCounts[i]);                 // вычисления с разбивкой на подмассивы
            checkAndRefresh(arr, referenceArr);
        }
    }

    private static void calculateArrThreadSplitArray(float[] arr, int threadCount) {
        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[threadCount];                             //Массив потоков
        float[][] arrayOfArrays = new float[threadCount][];                     //Массив частей исходного масива
        int partSize = arr.length / threadCount;                                //размер части массива

        for (int i = 0; i < threads.length; i++) {                              //Деление массива на чати
            int startIndex = i * partSize;
            arrayOfArrays[i] = new float[partSize];
            System.arraycopy(arr, startIndex, arrayOfArrays[i], 0, partSize);
        }

        for (int i = 0; i < threads.length; i++) {                              //создание и запуск потоков
            int arrayIndex = i;                                                 // каждому потоку достается свой подмассив
            threads[i] = new Thread(() ->
                calculatePart(arrayOfArrays[arrayIndex], 0,
                        arrayOfArrays[arrayIndex].length - 1, arrayIndex * partSize)
            );
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {                              //ожидание завершения работы всех потоков
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < arrayOfArrays.length; i++) {                        //Обратная "склейка" массивов в один
            System.arraycopy(arrayOfArrays[i], 0, arr, i * partSize, partSize);
        }

        long delta = System.currentTimeMillis() - startTime;
        System.out.printf("Количество потоков: %s Время обработки: %sс %sмс (с разбиением на массивы)\n", threadCount, (int) delta / 1000, (int) delta % 1000);
    }


    private static void calculateArrThread(float arr[], int threadCount) {
        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[threadCount];
        int partSize = arr.length / threadCount;

        for (int i = 0; i < threads.length; i++) {                          //создание и запуск потоков
            int startIndex = i * partSize;
            threads[i] = new Thread(() -> {                                 // каждому потоку достается свой подмассив
                calculatePart(arr, startIndex, startIndex + partSize - 1);
            });
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {                           //ожидание завершения работы всех потоков
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long delta = System.currentTimeMillis() - startTime;
        System.out.printf("Количество потоков: %s Время обработки: %sс %sмс\n", threadCount, (int) delta / 1000, (int) delta % 1000);
    }


    private static void calculatePart(float[] arr, int startIndex, int lastIndex, int delta) {
        for (int j = startIndex; j <= lastIndex; j++) {
            int i = j + delta;
            arr[j] = (float) (arr[j] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void calculatePart(float[] arr, int startIndex, int lastIndex) {         //Отдельный метод для исключения лишней оперции с переменной delta
        for (int i = startIndex; i <= lastIndex; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void checkAndRefresh(float[] arr, float[] referenceArr) {
        if (Arrays.compare(referenceArr, arr) != 0) {
            System.out.println("Ошибка копирования!!!!");
        }
        Arrays.fill(arr, 1);
    }
}
