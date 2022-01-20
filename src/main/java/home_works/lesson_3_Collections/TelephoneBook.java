package home_works.lesson_3_Collections;

import java.util.ArrayList;

public class TelephoneBook {
    ArrayList<Entry> book = new ArrayList<>();

    public void add(String surname, String phone) {
        book.add(new Entry(surname, phone));
    }

    public void get(String surname) {
        for (Entry entry : book) {
            if (entry.name.equals(surname)) {
                System.out.println(entry);
            }
        }
    }

    private static class Entry {
        String name;
        String phone;
        public Entry(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        @Override
        public String toString() {
            return name + ", phone='" + phone + '\'';
        }
    }
}
