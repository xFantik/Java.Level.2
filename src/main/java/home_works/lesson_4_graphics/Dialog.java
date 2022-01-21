package home_works.lesson_4_graphics;

import java.util.ArrayList;

public class Dialog {
    private ArrayList<Message> messages = new ArrayList<>();
    private boolean hasNewMessages;

    public int size(){
        return messages.size();
    }
    public Message get(int index){
        if (index==size()-1)            //Если загрузили последнее сообщение
            hasNewMessages=false;
        return messages.get(index);
    }
    public void add(Message m){
        messages.add(m);
    }

    public void add(Message m, boolean isNew) {
        add(m);
        hasNewMessages=isNew;

    }

    public boolean hasNewMessages(){
        return hasNewMessages;
    }


    public static class Message{
        private final String text;
        private final String sender;

        public Message(String sender, String text) {
            this.sender =sender;
            this.text = text;
        }


        public String getText() {
            return text;
        }

        public String getSender() {
            return sender;
        }
    }

}
