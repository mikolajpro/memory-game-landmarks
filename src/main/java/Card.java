package main.java;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Card {
    private ImagePattern front;
    private ImagePattern back;
    private Rectangle rectangle;
    public int showCounter;

    public Card(Rectangle rectangle) {
        this.rectangle = rectangle;
        this.back = new ImagePattern(new Image("/main/resources/images/back.jpg"));
    }

    public void setFront(ImagePattern front) {
        this.front = front;
    }

    public ImagePattern getFront() {
        return front;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public ImagePattern getBack() {
        return back;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean wasNeverShownBefore() {
        return showCounter < 1;
    }
}
