package main.java;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Timer extends Text {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");
    private final LocalTime localTime = LocalTime.now();
    private long gameTime;
    private Timeline timeline;

    public Timer() {
        startTimer();
    }

    public void startTimer() {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(0),
                        event -> {
                            gameTime = localTime.until(LocalTime.now(), ChronoUnit.SECONDS);
                            setText(LocalTime.ofSecondOfDay(gameTime).format(formatter));
                        }
                ),
                new KeyFrame(Duration.millis(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public long getGameTime() {
        return gameTime;
    }

    public void stopTimer() {
        timeline.pause();
    }
}
