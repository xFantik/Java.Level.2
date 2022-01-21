package home_works.lesson_4_graphics;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class ChatApplication extends Application {
    @FXML
    private TextField inputText;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("my-chat-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/my-chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 500);
        stage.setTitle("MyChat");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ChatController.chatController.setProp();
    }

    public static void main(String[] args) {
        launch();
    }
}