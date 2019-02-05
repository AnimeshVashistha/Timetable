/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
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
public class OptionsPane extends SomePane{

    GridPane pane = new GridPane();
    Pane parent;
    JFXButton done = new JFXButton();
    JFXButton source;

    TranslateTransition SlideIn;
    FadeTransition FadeIn;
    TranslateTransition SlideOut;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;

    int animationDuration = 200;
    int animationDistance = 50;
    double widthFactor = 1.8;
    double heightFactor = 0.6;
    double fontFactor = 0.2;
    double focusAnimationFactor = 0.6;
    boolean hidden = true;

    String ripplerFill = "#66DD77";
    String topButtonStyle = "roundedTopButton";
    String middleButtonStyle = "notRoundedButton";
    String bottomButtonStyle = "customButtonBottom";

    public OptionsPane(Pane parent) {
        pane.getStyleClass().add("customPane");
        pane.setPrefHeight(20);
        pane.setPrefWidth(20);
        pane.setVisible(false);

        this.parent = parent;
        parent.getChildren().add(pane);

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

        done.setText("done");
        done.getStyleClass().add(bottomButtonStyle);
        done.setPrefWidth(500);
        done.setPrefHeight(150);

        done.setOnAction(n -> {
            hide();
        });

        pane.getChildren().add(done);
    }

    public void showOnCoordinates(double x, double y, JFXButton source) {
        this.source = source;

        int size = pane.getChildren().size();

        double w = source.getHeight();
        double h = source.getHeight();

        if (x + w > parent.getWidth()) {
            x = parent.getWidth() - w * widthFactor;
        }
        if (y + h > parent.getHeight()) {
            y = parent.getHeight() - h * size * heightFactor;
        }

        show(x, y, w, h);
    }

    public void show(JFXButton source) {
        this.source = source;

        int size = pane.getChildren().size();

        double x = source.getLayoutX() + 1;
        double y = source.getLayoutY() + 1;
        double w = source.getHeight();
        double h = source.getHeight();

        if (x + w > parent.getWidth()) {
            x = parent.getWidth() - w * widthFactor;
        }
        if (y + h > parent.getHeight()) {
            y = parent.getHeight() - h * size * heightFactor;
        }

        show(x, y, w, h);
    }

    private void show(double x, double y, double w, double h) {
        int size = pane.getChildren().size();
        
        hidden = false;

        pane.setPrefWidth(w * widthFactor);
        pane.setPrefHeight(h * size * heightFactor);
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

    @Override
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
    
    @Override
    public void cancel() {
        hidden = true;
        pane.setVisible(false);
    }

    public void addButton(JFXButton button) {

        if (pane.getChildren().size() > 1) {
            button.getStyleClass().add(middleButtonStyle);
        } else {
            button.getStyleClass().add(topButtonStyle);
        }
        button.setRipplerFill(Color.web(ripplerFill));
        button.setPrefWidth(500);
        button.setPrefHeight(150);

        button.addEventHandler(ActionEvent.ACTION, n -> {
            hide();
        });

        int size = pane.getChildren().size();

        pane.getChildren().remove(done);
        pane.add(button, 0, size - 1, 1, 1);
        pane.add(done, 0, size, 1, 1);
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

    public double getFontFactor() {
        return fontFactor;
    }

    public void setFontFactor(double fontFactor) {
        this.fontFactor = fontFactor;
    }

    public double getFocusAnimationFactor() {
        return focusAnimationFactor;
    }

    public void setFocusAnimationFactor(double focusAnimationFactor) {
        this.focusAnimationFactor = focusAnimationFactor;
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
