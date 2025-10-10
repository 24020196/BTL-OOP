package org.example.btl;

import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class HelloController {

    public void initialize() {
        // HBox rộng bằng VBox cha
        hbox1.prefWidthProperty().bind(vboxRoot.widthProperty());
    }
    public static void main(String[] args) {
        launch(args)
    }
}
