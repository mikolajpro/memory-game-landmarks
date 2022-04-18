package main.java.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Win {
    @FXML
    VBox vBox;
    @FXML
    Text text;

    public void initialize(double s) {

        vBox.setStyle("-fx-background: #564A4A");

//        text.setText("ENTER YOUR NAME TO SAVE YOUR SCORE");
//        text.setText("Enter your name to save score");
//        text.setFont(Font.font("Roboto", 50));

        TextField textField = new TextField();
        textField.setMaxWidth(text.getLayoutBounds().getWidth());
        textField.setMinHeight(50);

        Text score = new Text();
        score.setText(String.valueOf(s));

        vBox.getChildren().addAll(textField);
    }
}
