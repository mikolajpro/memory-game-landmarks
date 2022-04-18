package main.java.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.User;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class Highscores {

    @FXML
    VBox vBox;

    @FXML
    TableView tableView;
    @FXML
    TableColumn<User, Double> score;
    @FXML
    TableColumn<User, String> name;

    ArrayList<User> users;

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

        Comparator<User> compareByScore = Comparator.comparingDouble(User::getUserScore);
        users.sort(compareByScore.reversed());

        ObservableList<User> list = FXCollections.observableArrayList(users);

        score.setCellValueFactory(new PropertyValueFactory<>("userScore"));
        name.setCellValueFactory(new PropertyValueFactory<>("userName"));

        tableView.setItems(list);
    }

    public void switchToMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/menu.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1280, 720);
        Game gameController = new Game();

        gameController.provideScene(scene);

        Stage window = (Stage) vBox.getScene().getWindow();
        window.setScene(scene);
    }
}
