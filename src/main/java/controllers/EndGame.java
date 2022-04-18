package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import main.java.User;

import java.io.*;
import java.util.ArrayList;

public class EndGame {
    @FXML
    StackPane stackPane;
    @FXML
    Pane pane;
    @FXML
    VBox vBox;
    @FXML
    Label label;
    @FXML
    Button ok;
    @FXML
    TextField textField;
    @FXML
    Label instructions;

    User user = new User();
    ArrayList<User> users;

    private static String USERNAME;
    private static String SCORE;

    public void initialize() {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("users.obj")
            );
            users = (ArrayList<User>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
        }

        textField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    USERNAME = newValue;
                    user.setUserName(newValue);
                });
    }


    public void endGame(MouseEvent mouseEvent) {
        if (USERNAME != null) {
            user.setUserScore(Double.parseDouble(SCORE));
            users.add(user);

            try {
                ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream("users.obj")
                );
                oos.writeObject(users);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/menu.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root, 1280, 720);

            Stage window = (Stage) stackPane.getScene().getWindow();
            window.setScene(scene);
        } else {
            showInstructions();
        }
    }

    public void showInstructions() {
        instructions.setText("ENTER YOUR NAME TO SAVE SCORE");
    }

    public void setScore(double score) {
        SCORE = String.valueOf(score);
        user.setUserScore(score);
    }
}
