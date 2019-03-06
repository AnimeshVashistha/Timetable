package timetable;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class OptionsPane extends SomePane {

    TranslateTransition SlideIn;
    FadeTransition FadeIn;
    TranslateTransition SlideOut;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;

    String topButtonStyle = "roundedTopButton";
    String middleButtonStyle = "notRoundedButton";

    public OptionsPane(Pane parent) {
        super(parent);

        SlideIn = new TranslateTransition(Duration.millis(SomePane.animationDuration));
        SlideIn.setFromY(SomePane.animationDistance);
        SlideIn.setToY(0);

        FadeIn = new FadeTransition(Duration.millis(SomePane.animationDuration));
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);

        SlideOut = new TranslateTransition(Duration.millis(SomePane.animationDuration));
        SlideOut.setToY(SomePane.animationDistance);

        FadeOut = new FadeTransition(Duration.millis(SomePane.animationDuration));
        FadeOut.setToValue(0);

        show = new ParallelTransition(getPane());
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(getPane());
        hide.getChildren().add(SlideOut);
        hide.getChildren().add(FadeOut);
    }

    public void showOnCoordinates(double x, double y, JFXButton source) {
        setSource(source);

        int size = getPane().getChildren().size();

        double w = source.getHeight();
        double h = source.getHeight();

        show(x, y, w, h);
    }

    public void show(JFXButton source) {
        setSource(source);

        int size = getPane().getChildren().size();

        double x = source.getLayoutX() + 1;
        double y = source.getLayoutY() + 1;
        double w = source.getHeight();
        double h = source.getHeight();

        show(x, y, w, h);
    }

    private void show(double x, double y, double w, double h) {
        int size = getPane().getChildren().size();

        if (x + w * getWidthFactor() > getParent().getWidth()) {
            x = getParent().getWidth() - w * getWidthFactor();
        }
        if (y + h * size * getHeightFactor() > getParent().getHeight()) {
            y = getParent().getHeight() - h * size * getHeightFactor();
        }

        setHidden(false);

        getPane().setPrefWidth(w * getWidthFactor());
        getPane().setPrefHeight(h * size * getHeightFactor());
        getPane().setLayoutX(x);
        getPane().setLayoutY(y);

        for (Node n : getPane().getChildren()) {
            JFXButton b = (JFXButton) n;
            b.setFont(new Font(h * 0.2));
        }

        Timeline focus = new Timeline(new KeyFrame(
                Duration.millis(SomePane.animationDuration * SomePane.focusAnimationOffsetFactor),
                n -> getPane().getChildren().get(0).requestFocus()));
        focus.play();

        getPane().setVisible(true);
        show.play();
    }

    @Override
    public void hide() {
        if (isHidden() == false) {
            setHidden(true);

            getSource().requestFocus();

            new Timeline(
                    new KeyFrame(Duration.millis(SomePane.animationDuration), n -> getPane().setVisible(false))
            ).play();

            hide.play();
        }
    }

    @Override
    public void cancel() {
        setHidden(true);
        getPane().setVisible(false);
    }

    public void addButton(JFXButton button) {

        if (getPane().getChildren().size() > 1) {
            button.getStyleClass().add(middleButtonStyle);
        } else {
            button.getStyleClass().add(topButtonStyle);
        }
        button.setRipplerFill(Color.web(GUI.ripplerFill));
        button.setPrefWidth(500);
        button.setPrefHeight(150);

        button.addEventHandler(ActionEvent.ACTION, event -> {
            hide();
        });

        int size = getPane().getChildren().size();

        getPane().getChildren().remove(getDone());
        getPane().add(button, 0, size - 1, 1, 1);
        getPane().add(getDone(), 0, size, 1, 1);
    }

}
