package home_works.lesson_3_Collections;

import java.util.HashMap;
import java.util.Map;

public class HomeWork_lesson3 {
    public static void main(String[] args) {
        task1();

        System.out.println();

        task2();
    }

    private static void task1() {
        String[] fruits = {"банан", "яблоко", "банан", "груша", "арбуз", "абрикос", "дыня", "гранат", "банан", "груша", "киви", "банан", "яблоко", "фейхоа", "дыня"};
        HashMap<String, Integer> fruitsMap = new HashMap<String, Integer>();
        for (int i = 0; i < fruits.length; i++) {
            if (fruitsMap.get(fruits[i]) == null) {
                fruitsMap.put(fruits[i], 1);
            } else {
                int t = fruitsMap.get(fruits[i]);
                fruitsMap.put(fruits[i], t + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : fruitsMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
//        fruitsMap.forEach((s, integer) -> System.out.println(s + " - " + integer));
    }


    private static void task2() {
        TelephoneBook telephoneBook = new TelephoneBook();
        telephoneBook.add("Иванов", "8-916-123-45-67");
        telephoneBook.add("Петров", "+7(495)111-22-21");
        telephoneBook.add("Иванов", "8-800-555-35-35");
        telephoneBook.add("Сидиров", "8-999-111-22-22");
        telephoneBook.get("Иванов");
    }
}

