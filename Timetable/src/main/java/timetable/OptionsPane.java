/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import com.jfoenix.controls.JFXButton;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class OptionsPane extends GridPane {

    List<Node> controls = new ArrayList<Node>();

    int animationDuration = 300;
    int animationDistance = 50;
    boolean hidden = false;

    TranslateTransition SlideIn;
    FadeTransition FadeIn;
    TranslateTransition SlideOut;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;

    public OptionsPane() {
        SlideIn = new TranslateTransition(Duration.millis(animationDuration));
        SlideIn.setFromY(animationDistance);
        SlideIn.setToY(0);

        FadeIn = new FadeTransition(Duration.millis(animationDuration));
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);

        SlideOut = new TranslateTransition(Duration.millis(animationDuration));
        SlideOut.setToY(animationDistance);

        FadeOut = new FadeTransition(Duration.millis(animationDuration));
        FadeOut.setToValue(0);

        show = new ParallelTransition(this);
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(this);
        hide.getChildren().add(SlideOut);
        hide.getChildren().add(FadeOut);

        setVisible(false);
    }

    public void show() {
        setVisible(true);
        show.play();
    }

    public void hide() {
        if (hidden == false) {
            hidden = true;
            new Timeline(
                    new KeyFrame(Duration.millis(animationDuration), n -> setVisible(false))
            ).play();
            show.play();
        }
    }

    public void addButton(JFXButton b) {
        controls.add(b);

        if(controls.size() == 0){
            
        }
    }

}
