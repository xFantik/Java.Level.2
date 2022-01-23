package home_works.lesson_4_graphics;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.*;

public class ChatController implements Initializable {
    private static final String mainChatName = "Общий чат";


    private final ArrayList<Dialog> dialogsList = new ArrayList<>();
    static ChatController chatController;
    static int lettersCount = 33;
    private String lastSender="";


    //-----------------тестовые переменные
    public static String myName = "Паша";
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

        Message message = new Message(myName, text);


        if (currentDialog == null) {
            addToChatList(new Message("System", "Диалог не выбран"));
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
        dialogsList.add(new Dialog(mainChatName));
        currentDialog = getDialog(mainChatName);
        titleText.setText(mainChatName);
        dialogsList.add(new Dialog("Вася"));
        dialogsList.add(new Dialog("Дима"));
        dialogsList.add(new Dialog("Петя"));
        dialogsList.add(new Dialog("Семён"));


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
        for (Dialog d : dialogsList) {
            if (d.getOpponent().equals(name))
                return d;
        }
        Dialog new_d = (new Dialog(name));
        dialogsList.add(new_d);
        return new_d;

    }

    private void loadChatList(String contactName) {         //Обновление диалогового окна. вызывается выбором контакта в списке
        currentDialog = getDialog(contactName);                    //Подгружаем сохраненный диалог

        if (currentDialog.getOpponent().equals(mainChatName)) {
            titleText.setText("Общий чат");                //меняем заголовок окна

        } else
            titleText.setText(contactName + " на связи");                //меняем заголовок окна

        chatList.getItems().clear();                             //очищаем окно чата
        for (int i = 0; i < currentDialog.size(); i++) {        //          и загружаем сообщения из текущего диалога.
            addToChatList(currentDialog.get(i));
        }
        ((Pane) contactList.getSelectionModel().getSelectedItem()).getChildren().get(0).setStyle("-fx-background-color: null");


    }

    private void addToChatList(Message message) {
        Pane paneInChatList = new Pane();
        Label messageItem = new Label();
        messageItem.setPadding(new Insets(6));
        messageItem.setStyle("-fx-background-radius: 6;");

        if (message.getSender().equals(myName)) {
            messageItem.setStyle(messageItem.getStyle() + "-fx-background-color: #3E7D0850");
            paneInChatList.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            paneInChatList.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            if (currentDialog.getOpponent().equals(mainChatName)) {
                if (!message.getSender().equals(lastSender)){
                    lastSender=message.getSender();
                    Label messageName = new Label();
                    messageName.setText(message.getSender() + ":");
                    messageName.setFont(new Font(10));
                    messageItem.setMinWidth(8 * message.getSender().length() + 5);
                    messageName.setPadding(new Insets(0, 3, 0, 3));
                    paneInChatList.getChildren().add(messageName);
                    messageItem.setPadding(new Insets(15, 6, 6, 6));


                }
                messageItem.setOnMouseClicked((MouseEvent mouseEvent) -> {
//                System.out.println(mouseEvent);
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        inputText.setText(message.getSender() + ", " + inputText.getText());
                        inputText.requestFocus();
                        inputText.selectEnd();
                    }
                });
            }
            messageItem.setStyle(messageItem.getStyle() + MessageColorUtil.getColor(message.getSender()));

        }
        messageItem.setWrapText(true);
        messageItem.setText(StringUtils.wrapText(message.getText(), lettersCount));

        paneInChatList.getChildren().add(messageItem);
        chatList.getItems().add(paneInChatList);
        chatList.scrollTo(chatList.getItems().size() - 1);
    }


    public void receiveMessage(ActionEvent actionEvent) {

        int el = (int) (Math.random() * (dialogsList.size() + 5));
        String name;
        if (el >= dialogsList.size()) {
            name = mainChatName;
        } else
            name = dialogsList.get(el).getOpponent();




        //System.out.println(name + " прислал новое сообщение");

        Dialog d = getDialog(name);
        String[] texts = {"Привет!", "Как дела?", "Что-то тут скучно!", "А пойдём, погуляем?", "Кто на гору катать?",
                "Даже не знаю ,что тут написать! Просто очень хочется, чтобы моё сообщение все заметили, поэтому делаю его очень длинным!!"};
        int rndText = (int) (Math.random() * texts.length);
        if (name.equals(mainChatName)) {
            int nameNum = (int) (Math.random() * (dialogsList.size() - 1)) + 1;
            d.add(new Message(dialogsList.get(nameNum).getOpponent(), texts[rndText]));
        } else {
            d.add(new Message(name, texts[rndText]));
        }

        if (getDialog(name) == currentDialog) {
            addToChatList(currentDialog.get(currentDialog.size() - 1));
        }

        updateContactList();

    }


    private void updateContactList() {
        if (observableListContacts == null) {
            observableListContacts = FXCollections.observableArrayList();
            contactList.setItems(observableListContacts);
        }

        contactList.getItems().clear();

        for (Dialog entry : dialogsList) {

            Pane paneInContactList = new Pane();
            Label name = new Label();

            name.setText(entry.getOpponent());


            if (entry.hasNewMessages() && !currentDialog.getOpponent().equals(entry.getOpponent())) {
                name.setStyle("-fx-background-color: #3E7D0850");

            } else {
                name.setStyle("-fx-background-color: none");
            }

            paneInContactList.getChildren().add(name);
            contactList.getItems().add(paneInContactList);
        }
    }
}