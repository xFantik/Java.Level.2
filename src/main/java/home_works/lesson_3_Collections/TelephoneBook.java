package home_works.lesson_3_Collections;

import java.util.HashMap;
import java.util.Map;

public class TelephoneBook {
    HashMap<String, String> book = new HashMap<>();

    public void add(String surname, String phone) {
        if (book.get(phone) != null) {
            System.out.println("Этот номер занят. Владелец: "+ book.get(phone));
        } else
            book.put(phone, surname);
    }

    public void get(String surname) {
        for (Map.Entry<String, String> entry : book.entrySet()) {
            if (entry.getValue().equals(surname)) {
                System.out.println(surname + ": " + entry.getKey());
            }
        }
    }


}
