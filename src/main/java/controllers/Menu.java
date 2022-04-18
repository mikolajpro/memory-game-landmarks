package main.java.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class Menu {

    @FXML
    VBox vBox;

    @FXML
    public void initialize() {

    }

    public void switchToBeforeGame(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/beforeGame.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) vBox.getScene().getWindow();

        Scene scene = new Scene(root,1280,720);
        window.setScene(scene);
    }

    public void switchToHighScores(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/highscores.fxml"));
        Stage window = (Stage) vBox.getScene().getWindow();

        Scene game = new Scene(root, 1280, 720);
        window.setScene(game);
    }

    public void exit(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
