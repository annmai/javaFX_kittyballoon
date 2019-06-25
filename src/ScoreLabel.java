package kittyballoon;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Ann Mai
 */
public class ScoreLabel extends Pane {

    protected Text text = new Text("Score: 0");

    public ScoreLabel(double x, double y) {
        setPrefHeight(50);
        setPrefWidth(300);
        setTranslateX(x - 250);
        setTranslateY(y + 10);
        text.setTranslateY(25);
        text.setTranslateX(100);
        getChildren().addAll(text);
        text.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 22));
        text.setFill(Color.HOTPINK);
    }

    public void setText(String message) {
    	text.setText(message);

    }
}
