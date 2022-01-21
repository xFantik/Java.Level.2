package home_works.lesson_4_graphics;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class ChatController implements Initializable {


    private TreeMap<String, Dialog> dialogMap = new TreeMap<>();
    static ChatController chatController;
    static int lettersCount = 33;


    //-----------------тестовые переменные
    private String myName = "Паша";
    //  ArrayList<String> testContacts = new ArrayList<>();
    private Dialog currentDialog;


    @FXML
    public VBox leftPane;
    @FXML
    public ListView contactList;

    ObservableList<Pane> observableListContacts;

    @FXML
    private Label titleText;

    @FXML
    private TextField inputText;

    @FXML
    private ListView<Pane> chatList;

    public ChatController() {
        chatController = this;
    }

    @FXML
    protected void sendMessage() {
        String text = inputText.getText();
        if (text.isBlank()) {
            inputText.clear();
            inputText.requestFocus();
            return;
        }

        Dialog.Message message = new Dialog.Message(myName, text);


        if (currentDialog == null) {
            addToChatList(new Dialog.Message("System", "Диалог не выбран"));
        } else {
            currentDialog.add(message);
            addToChatList(message);
        }
        inputText.clear();
        inputText.requestFocus();
    }

    public void setProp() {
        inputText.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dialogMap.put("Вася", new Dialog());
        dialogMap.put("Дима", new Dialog());
        dialogMap.put("Петя", new Dialog());
        dialogMap.put("Семён", new Dialog());


        updateContactList();





        MultipleSelectionModel<Pane> langsSelectionModel = contactList.getSelectionModel();

        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<Pane>() {
            public void changed(ObservableValue<? extends Pane> changed, Pane oldValue, Pane newValue) {
//                System.out.println(changed);
                if (newValue != null)
                    loadChatList(((Label) newValue.getChildren().get(0)).getText());
            }
        });
    }

    private Dialog getDialog(String name) {
        if (dialogMap.get(name) == null) {
            dialogMap.put(name, new Dialog());
        }
        return dialogMap.get(name);
    }

    private void loadChatList(String contactName) {                   //Обновление диалогового окна
        currentDialog = getDialog(contactName);                    //Подгружаем сохраненный диалог

        titleText.setText(contactName + " на связи");                //меняем заголовок

        chatList.getItems().clear();                             //очищаем
        for (int i = 0; i < currentDialog.size(); i++) {        //          и заполняем сообщениями чат
            addToChatList(currentDialog.get(i));
        }
    }

    private void addToChatList(Dialog.Message message) {
        Pane paneInChatList = new Pane();
        Label messageItem = new Label();

        if (message.getSender().equals(myName)) {
            messageItem.setStyle("-fx-background-radius: 6; -fx-background-color: #3E7D0850");
            paneInChatList.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            paneInChatList.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            messageItem.setStyle("-fx-background-radius: 6; -fx-background-color: #00FFC450");
        }
        messageItem.setPadding(new Insets(6));
        messageItem.setWrapText(true);
        messageItem.setText(StringUtils.wrapText(message.getText(), lettersCount));
        paneInChatList.getChildren().add(messageItem);
        chatList.getItems().add(paneInChatList);
        chatList.scrollTo(chatList.getItems().size() - 1);
    }


    public void receiveMessage(ActionEvent actionEvent) {
        Set keys = dialogMap.keySet();
        dialogMap.keySet();

        int el = (int) (Math.random() * keys.size());
        String name = "";
        for (Object key : keys) {
            if (el == 0) {
                name = (String) key;
            }
            el--;
        }


        System.out.println(name + " прислал новое сообщение");

        Dialog d = getDialog(name);
        d.add(new Dialog.Message(name, "тест"), true);

        if (dialogMap.get(name) == currentDialog) {
            addToChatList(new Dialog.Message(name, "тест"));
        }


        updateContactList();

    }


    private void updateContactList() {
        if (observableListContacts == null) {
            observableListContacts = FXCollections.observableArrayList();
            contactList.setItems(observableListContacts);
        }

        contactList.getItems().clear();
        for (Map.Entry<String, Dialog> entry : dialogMap.entrySet()) {

            Pane paneInContactList = new Pane();
            Label name = new Label();

            name.setText(entry.getKey());

            if (entry.getValue().hasNewMessages()) {                                                   //Если исходящее сообщение
                name.setStyle("-fx-background-color: #3E7D0850");
            }

            paneInContactList.getChildren().add(name);
            contactList.getItems().add(paneInContactList);
        }
    }
}