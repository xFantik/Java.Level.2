module home_works.lesson_4_graphics {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;


    exports home_works.lesson_4_graphics;
    opens home_works.lesson_4_graphics to javafx.fxml;
}