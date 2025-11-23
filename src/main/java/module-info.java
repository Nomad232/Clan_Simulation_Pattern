module com.game.task2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.game.task2 to javafx.fxml;
    exports com.game.task2;
    exports com.game.task2.controllers;
    opens com.game.task2.controllers to javafx.fxml;
}