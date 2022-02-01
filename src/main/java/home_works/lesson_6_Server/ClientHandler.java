package home_works.lesson_6_Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private static final ArrayList<ClientHandler> clientsList = new ArrayList<>();
    private static final HashMap<Integer, String> names = new HashMap<>();

    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Клиент подключен. Индекс: " + clientsList.size());
            sendMessage(-1, "Добро пожаловать на сервер!", this);
            sendMessage(-1, "Для отправки сообщения в приват: /<индекс_получателя> ...", this);
            sendMessage(-1, "Ваш индекс: " + clientsList.size(), this);
            sendMessage(-1, "Установить имя: /name <yourName>", this);
            sendMessage(-1, "Список участников: /list", this);
            handleMessage(-1, "У нас новый собеседник с индексом: " + clientsList.size());
            clientsList.add(this);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("WARNING: Can't initialise client connection");
            interrupt();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readUTF();
                message = message.trim();
                int fromIndex = clientsList.indexOf(this);
                if (message.length() == 0) {
                    System.out.println("пустое сообщние");
                    continue;
                }
                if (message.charAt(0) == '/') {
                    String[] t = message.split(" ");
                    if (t[0].equals("/name") && t.length > 1) {
                        String newName = message.substring(6);
                        names.put(clientsList.indexOf(this), newName);
                        System.out.println("Клиент " + clientsList.indexOf(this) + " выбрал имя " + newName);
                        sendMessage(-1, "Выбрано имя: " + newName, this);
                    }
                    if (t[0].equals("/list")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Контакты онлайн:\n");
                        for (int i = 0; i < clientsList.size(); i++) {
                            if (!clientsList.get(i).isInterrupted()) {
                                sb.append(i);
                                sb.append(": ").append(names.getOrDefault(i, "noname")).append("\n");
                            }
                        }
                        sendMessage(-1, sb.toString(), this);
                    }
                    int toIndex = getReceiverFromString(message.substring(1));
                    if (toIndex >= 0) {
                        System.out.println("Сообщение в приват для клиента номер " + toIndex);
                        if (toIndex >= clientsList.size()) {
                            System.out.println("Попытка отправки несуществующему клиенту " + toIndex);
                            sendMessage(-1, "Клиент с индексом " + toIndex + " не найден", clientsList.get(fromIndex));
                        } else if (clientsList.get(toIndex).isInterrupted()) {
                            System.out.println("Попытка отправки отключившемуся клиенту " + toIndex);
                            sendMessage(-1, "Клиент с индексом " + toIndex + " отключился", clientsList.get(fromIndex));
                        } else {
                            sendMessage(fromIndex, message.substring(String.valueOf(toIndex).length()+1).trim(), clientsList.get(toIndex));
                        }
                    }
                    continue;
                }
                System.out.println("Клиент " + clientsList.indexOf(this) + ": " + message);
                handleMessage(fromIndex, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("INFO: Клиент " + clientsList.indexOf(this) + " отключился");
            interrupt();
        }
    }

    private static void handleMessage(int from, String message) {
        if (message.isBlank())
            return;

        int toIndex = getReceiverFromString(message);

        for (int i = 0; i < clientsList.size(); i++) {                      //рассылка всем
            if (i == from) {                                                        // (себя прпускаем)
                continue;
            }
            ClientHandler clientHandler = clientsList.get(i);
            sendMessage(from, message, clientHandler);
        }

    }

    private static void sendMessage(int from, String message, ClientHandler clientHandler) {
        if (message.isBlank()) return;
        if (!clientHandler.isInterrupted())
            try {
                if (from == -1) {
                    clientHandler.out.writeUTF("SERVER INFO: " + message);
                } else {
                    clientHandler.out.writeUTF("Сообщение от " + names.getOrDefault(from, String.valueOf(from)) + ": " + message);
                }
            } catch (IOException e) {
                clientHandler.interrupt();
                //clientsList.remove(i);                         //Если удалить из списка, сдвинется нумерация остальных клиентов
                e.printStackTrace();
            }
    }

    private static int getReceiverFromString(String s) {
        s = s.trim();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                result.append(s.charAt(i));
            } else {
                break;
            }
        }
        if (result.length() == 0) return -10;
        return Integer.parseInt(result.toString());

    }

}
