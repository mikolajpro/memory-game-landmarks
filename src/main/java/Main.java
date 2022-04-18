package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/menu.fxml"));

        primaryStage.setTitle("Memory Game - Landmarks");
        primaryStage.getIcons().add(new Image("main/resources/images/icon.png"));

        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
