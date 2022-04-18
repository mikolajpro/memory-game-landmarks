package main.java.controllers;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

import main.java.Board;
import main.java.Card;
import main.java.Timer;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox vBox;
    @FXML
    GridPane gridPane;

    private final static IntegerProperty CARDS_LEFT = new SimpleIntegerProperty();

    int cardSize = 145;

    ArrayList<Card> cards = new ArrayList<>();


    AtomicInteger numberOfShowedCards = new AtomicInteger();

    private volatile boolean enough = false;

    Timeline gameTimeline = new Timeline(
            new KeyFrame(Duration.millis(100),
                    event -> {
                        if (cards.stream().noneMatch(x -> x.getRectangle().isVisible())) {
                            switchToEndGame(scrollPane.getScene(),"/main/resources/view/endGame.fxml");
                        }
                    }
            )
    );

    @FXML
    Timer timer = new Timer();

    double score = 0;

    @FXML
    public void initialize() {

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        vBox.setStyle("-fx-background-image: url(/main/resources/images/bg/bg_blue.png)");

        gridPane.setHgap(cardSize * 0.2);
        gridPane.setVgap(cardSize * 0.2);


        // Generating rectangles
        // Setting their fronts to random color
        Color color = null;
        for (int i = 0; i < Board.rows * Board.columns; i++) {
            Rectangle rectangle = new Rectangle(cardSize, cardSize * 1.4);
            Card card = new Card(rectangle);
            rectangle.setFill(card.getBack());
            cards.add(card);

        }


        DropShadow dropShadow = new DropShadow();
        DropShadow dropShadow2 = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow2.setRadius(7);

        // Setting fill from 'cardColors' to 'cards'
        // Setting 'cards' shadow to 'dropShadow'
        for (Card card : cards) {
//            card.getRectangle().setFill(card.getFront());
            card.getRectangle().setEffect(dropShadow);
            card.getRectangle().setStroke(Color.WHITE);
            card.getRectangle().setStrokeWidth(4);
            card.getRectangle().setStrokeType(StrokeType.INSIDE);
        }

        generateFronts("src/main/resources/images/front");

        // Sorting 'cards' randomly
        Collections.shuffle(cards);


        // Hover
        int cardIndex = 0;
        for (int row = 0; row < Board.rows; row++) {
            for (int column = 0; column < Board.columns; column++) {
                Card card = cards.get(cardIndex);
                Rectangle rectangle = card.getRectangle();

                ScaleTransition scaleDownTransition = new ScaleTransition(Duration.millis(50), rectangle);
                scaleDownTransition.setFromX(1.05);
                scaleDownTransition.setFromY(1.05);
                scaleDownTransition.setToX(1);
                scaleDownTransition.setToY(1);
                gridPane.add(rectangle, column, row);

                rectangle.setOnMouseClicked((event) -> {

                            numberOfShowedCards.set(
                                    (int) cards.stream()
                                            .filter(x -> !x.getRectangle().getFill().equals(x.getBack()))
                                            .count()
                            );
                            if (numberOfShowedCards.get() < 2) {
                                rectangle.setFill(card.getFront());
                                numberOfShowedCards.getAndIncrement();
                            }
                            if (numberOfShowedCards.get() == 2) {
                                ArrayList<Card> shownCards = new ArrayList<>();
                                cards.stream()
                                        .filter(x -> !x.getRectangle().getFill().equals(x.getBack()))
                                        .forEach(shownCards::add);

                                boolean frontsMatch = false;
                                try {
                                    if (shownCards.get(0).getFront().equals(shownCards.get(1).getFront())) {
                                        frontsMatch = true;
                                        //If both were never shown before +1 point to score
                                        if (shownCards.get(0).wasNeverShownBefore() && shownCards.get(1).wasNeverShownBefore())
                                            score += 1;
                                    }
                                    shownCards.get(0).showCounter++;
                                    shownCards.get(1).showCounter++;
                                } catch (IndexOutOfBoundsException e) {
                                    //It just prevents scoring a point by clicking one card twice
                                }

                                if (frontsMatch) {
                                    showAnswear("url(/main/resources/images/bg/bg_green.png)");
                                    shownCards.forEach(this::removeCard);
                                } else {
                                    showAnswear("url(/main/resources/images/bg/bg_red.png)");
                                    cards.stream()
                                            .filter(x -> !x.getRectangle().getFill().equals(x.getBack()))
                                            .forEach(this::hideCard);
                                }

                            }
                        }
                );
                rectangle.setOnMouseEntered((event) -> {
                            rectangle.setScaleX(1.05);
                            rectangle.setScaleY(1.05);
                            gridPane.setCursor(Cursor.HAND);
                        }
                );
                rectangle.setOnMouseExited((event) -> {
                            scaleDownTransition.play();
                            gridPane.setCursor(Cursor.DEFAULT);
                        }
                );
                cardIndex++;
            }
        }
        gameTimeline.setCycleCount(Animation.INDEFINITE);
        gameTimeline.play();
    }

    public void generateFronts(String directoryName) {
        File directory = new File(directoryName);

        //get all the files from a directory
        ArrayList<File> images = new ArrayList<>(Arrays.asList(directory.listFiles()));

        ImagePattern imagePattern = null;
        File image = null;
        for (int i = 0; i < cards.size(); i++) {
            if (i % 2 == 0) {
                image = images.get((int) (Math.random() * images.size()));
                imagePattern = new ImagePattern(new Image("/main/resources/images/front/" + image.getName()));
                cards.get(i).setFront(imagePattern);
            } else {
                cards.get(i).setFront(imagePattern);
                images.remove(image);
            }
        }
    }

    public void showAnswear(String url) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0),
                        event -> {
                            vBox.setStyle("-fx-background-image: " + url);
                        }
                ),
                new KeyFrame(Duration.millis(200),
                        event -> {
                            vBox.setStyle("-fx-background-image: url(/main/resources/images/bg/bg_blue.png)");
                        }
                )
        );
        timeline.setCycleCount(1);
        timeline.play();

    }

    public void removeCard(Card card) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200),
                        event -> {
                            card.getRectangle().setVisible(false);
                            card.getRectangle().setFill(card.getBack());
                        }
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void hideCard(Card card) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(2000),
                        event -> {
                            card.getRectangle().setFill(card.getBack());
                        }
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void provideScene(Scene scene) {
        KeyCombination kc = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_ANY, KeyCombination.SHIFT_ANY);
        Runnable rn = () -> {
            System.out.println("Accelerator key worked");
            switchToEndGame(scene,"/main/resources/view/menu.fxml");
        };
        scene.getAccelerators().put(kc, rn);
    }

    public void switchToEndGame(Scene scene, String path) {
        gameTimeline.pause();

        double time = (double) timer.getGameTime();
        score += Board.rows * Board.columns / 2;
        System.out.println(score);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage window = (Stage) scene.getWindow();
        Scene newScene = new Scene(root,1280,720);

        EndGame endGameController = new EndGame();
        endGameController.setScore(score);

        window.setScene(newScene);
    }
}
