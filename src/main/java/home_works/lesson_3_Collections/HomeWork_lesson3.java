package home_works.lesson_3_Collections;

import java.util.HashMap;

public class HomeWork_lesson3 {
    public static void main(String[] args) {
        task1();

        System.out.println();

        task2();
    }

    private static void task1() {
        String[] fruits = {"банан", "яблоко", "банан", "груша", "арбуз", "абрикос", "дыня", "гранат", "банан", "груша", "фейхоа", "банан", "яблоко", "фейхоа", "дыня"};
        HashMap<String, Integer> fruitsMap = new HashMap<>();
        for (String fruit : fruits) {
            if (fruitsMap.putIfAbsent(fruit, 1) != null) {
                fruitsMap.compute(fruit, (s, integer) -> integer + 1);
            }
        }
        fruitsMap.forEach((s, integer) -> System.out.println(s + " - " + integer));
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

