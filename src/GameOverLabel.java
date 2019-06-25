package kittyballoon;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

class GameOverLabel extends ScoreLabel {

    public GameOverLabel(double x, double y) {
        super(x, y);
        setOpacity(0.8);
        setPrefWidth(500);
        setPrefHeight(100);
        text.setTranslateX(40);
        text.setTranslateY(-45);
        text.setTextAlignment(TextAlignment.CENTER);
        setText("Game Over!\nPress Enter to Play Again.");
        text.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 30));
        text.setFill(Color.BLACK);

    }

}