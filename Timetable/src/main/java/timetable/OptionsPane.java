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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class OptionsPane {

    GridPane pane = new GridPane();

    TranslateTransition SlideIn;
    FadeTransition FadeIn;
    TranslateTransition SlideOut;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;

    List<Node> controls = new ArrayList<Node>();
    Pane bounds;
    JFXButton done = new JFXButton();
    JFXButton source;

    int animationDuration = 300;
    int animationDistance = 50;
    double widthFactor = 1.2;
    double heightFactor = 0.6;
    double fontFactor = 0.2;
    double focusAnimationFactor = 0.6;
    boolean hidden = false;

    String ripplerFill = "#66DD77";
    String topButtonStyle = "roundedTopButton";
    String middleButtonStyle = "notRoundedButton";
    String bottomButtonStyle = "customButtonBottom";

    public OptionsPane(Pane bounds) {
        this.bounds = bounds;

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

        show = new ParallelTransition(pane);
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(pane);
        hide.getChildren().add(SlideOut);
        hide.getChildren().add(FadeOut);

        pane.getStyleClass().add("customPane");

        done.setText("done");
        done.getStyleClass().add(bottomButtonStyle);
        done.setRipplerFill(Color.web(ripplerFill));

        done.setOnAction(n -> {
            hide();
        });

        pane.getChildren().add(done);

        pane.setVisible(false);
    }

    public void showOnCoordinates(double x, double y, JFXButton source) {
        this.source = source;

        double w = source.getHeight() * widthFactor;
        double h = source.getHeight() * heightFactor;

        if (x + w > bounds.getWidth()) {
            x = bounds.getWidth() - w;
        }

        if (y + h > bounds.getHeight()) {
            y = bounds.getHeight() - h;
        }

        show(x, y, w, h);
    }

    public void show(JFXButton source) {
        this.source = source;

        double x = source.getLayoutX() + 1;
        double y = source.getLayoutY() + 1;
        double w = source.getHeight() * widthFactor;
        double h = source.getHeight() * heightFactor;

        if (x + w > bounds.getWidth()) {
            x = bounds.getWidth() - w;
        }
        if (y + h > bounds.getHeight()) {
            y = bounds.getHeight() - h;
        }

        show(x, y, w, h);
    }

    private void show(double x, double y, double w, double h) {
        System.out.println("bounds: x: " + x + " y: " + y + " w: " + w + " h: " + h + " ");

        pane.setPrefWidth(w);
        pane.setPrefHeight(h);
        pane.setLayoutX(x);
        pane.setLayoutY(y);

        for (Node n : pane.getChildren()) {
            JFXButton b = (JFXButton) n;
            b.setFont(new Font(h * 0.2));
        }

        Timeline focus = new Timeline(new KeyFrame(
                Duration.millis(animationDuration * focusAnimationFactor),
                e -> pane.getChildren().get(0).requestFocus()));
        focus.play();

        pane.setVisible(true);
        show.play();
    }

    public void hide() {
        if (hidden == false) {
            hidden = true;

            source.requestFocus();

            new Timeline(
                    new KeyFrame(Duration.millis(animationDuration), n -> pane.setVisible(false))
            ).play();

            hide.play();
        }
    }

    public void addButton(JFXButton button) {

        if (pane.getChildren().size() > 1) {
            button.getStyleClass().add(middleButtonStyle);
        } else {
            button.getStyleClass().add(topButtonStyle);
        }
        button.setRipplerFill(Color.web(ripplerFill));

        int gridPosition = pane.getChildren().size() - 1;

        pane.getChildren().add(gridPosition, button);
    }

    public double getWidthFactor() {
        return widthFactor;
    }

    public void setWidthFactor(double widthFactor) {
        this.widthFactor = widthFactor;
    }

    public double getHeightFactor() {
        return heightFactor;
    }

    public void setHeightFactor(double heightFactor) {
        this.heightFactor = heightFactor;
    }

    public String getRipplerFill() {
        return ripplerFill;
    }

    public void setRipplerFill(String ripplerFill) {
        this.ripplerFill = ripplerFill;
    }

    public GridPane getPane() {
        return pane;
    }

}
