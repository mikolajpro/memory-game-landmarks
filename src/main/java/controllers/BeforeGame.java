package main.java.controllers;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import main.java.Board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BeforeGame {

    @FXML
    StackPane stackPane;
    @FXML
    VBox vBox;
    @FXML
    TextField rows;
    @FXML
    TextField columns;
    @FXML
    Button start;
    @FXML
    Label instructions;

    private static final IntegerProperty ROWS = new SimpleIntegerProperty();
    private static final IntegerProperty COLUMNS = new SimpleIntegerProperty();

    private static int NUMBER_OF_IMAGES;
    private static int MAX_NUMBER_OF_CARDS;

    @FXML
    public void initialize() {
        setMaxNumberOfCards();

        rows.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        ROWS.set(Integer.parseInt(newValue));
                    } catch (NumberFormatException e) {

                    }
                }
        );
        columns.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        COLUMNS.set(Integer.parseInt(newValue));
                    } catch (NumberFormatException e) {

                    }

                }
        );
    }

    public void setMaxNumberOfCards() {
        File directory = new File("src/main/resources/images/front");

        //get all the files from a directory
        ArrayList<File> images = new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.listFiles())));

        NUMBER_OF_IMAGES = images.size();
        MAX_NUMBER_OF_CARDS = NUMBER_OF_IMAGES * 2;
    }

    public void startGame(MouseEvent mouseEvent) {
        int numberOfCards = (int) (ROWS.multiply(COLUMNS)).getValue();

        if (numberOfCards > 0 && numberOfCards <= MAX_NUMBER_OF_CARDS && numberOfCards % 2 == 0) {
            switchToGame();
            instructions.setVisible(false);
        } else {
            showInstructions();
        }

    }

    public void showInstructions() {
        instructions.setText("ROWS x COLUMNS has to be: even, greater that 0 and lesser than " + MAX_NUMBER_OF_CARDS);
    }

    public void switchToGame() {
        Board.rows = ROWS.get();
        Board.columns = COLUMNS.get();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/game.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1280, 720);
        Game gameController = new Game();

        gameController.provideScene(scene);

        Stage window = (Stage) stackPane.getScene().getWindow();
        window.setScene(scene);
    }
}
