package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import static timetable.GUI.ANIMATION_DISTANCE;
import static timetable.GUI.ANIMATION_DURATION;

/**
 *
 * @author Tobias
 */
public class ColorPickerPane extends SomePane {

    JFXSlider[] sliders;

    TranslateTransition SlideIn;
    FadeTransition FadeIn;
    TranslateTransition SlideOut;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;

    EventHandler<Event> onHide;
    Button hideEvent = new Button();

    public ColorPickerPane(Pane parent) {
        super(parent);

        setWidthFactor(3);
        setHeightFactor(1.05);

        SlideIn = new TranslateTransition(Duration.millis(ANIMATION_DURATION));
        SlideIn.setFromY(ANIMATION_DISTANCE);
        SlideIn.setToY(0);

        FadeIn = new FadeTransition(Duration.millis(ANIMATION_DURATION));
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);

        SlideOut = new TranslateTransition(Duration.millis(ANIMATION_DURATION));
        SlideOut.setToY(ANIMATION_DISTANCE);

        FadeOut = new FadeTransition(Duration.millis(ANIMATION_DURATION));
        FadeOut.setToValue(0);

        show = new ParallelTransition(getPane());
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(getPane());
        hide.getChildren().add(SlideOut);
        hide.getChildren().add(FadeOut);
        hide.setOnFinished(event -> getPane().setVisible(false));

        sliders = new JFXSlider[3];
        for (int i = 0; i < sliders.length; i++) {
            JFXSlider s = new JFXSlider();
            s.setMax(255);
            s.valueProperty().addListener(event -> {
                displayColor();
            });
        }

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

        setHidden(false);

        getPane().setPrefWidth(w * getWidthFactor());
        getPane().setPrefHeight(h * size * getHeightFactor());
        getPane().setLayoutX(x);
        getPane().setLayoutY(y);

        getDone().setFont(new Font(getSource().getHeight() * getFontFactor()));

        Timeline reposition = new Timeline(new KeyFrame(Duration.millis(1), n -> {
            repositon();
        }));
        reposition.play();

        getPane().setVisible(true);
        show.play();
    }

    private void repositon() {

        double x = getPane().getLayoutX();
        double y = getPane().getLayoutY();
        double w = getPane().getWidth();
        double h = getPane().getHeight();

        if (x + w > getParent().getWidth()) {
            x = getParent().getWidth() - w;
            getPane().setLayoutX(x);
        }
        if (y + h > getParent().getHeight()) {
            y = getParent().getHeight() - h;
            getPane().setLayoutY(y);

        }
    }

    @Override
    public void hide() {
        if (isHidden() == false) {
            setHidden(true);

            if (onHide != null) {
                hideEvent.fire();
            }

            getSource().requestFocus();

            hide.play();
        }
    }

    @Override
    public void cancel() {
        setHidden(true);
        getPane().setVisible(false);
    }

    public void setOnHide(EventHandler<Event> onHide) {
        if (this.onHide != null) {
            hideEvent.removeEventHandler(EventType.ROOT, this.onHide);
        }
        this.onHide = onHide;
        hideEvent.addEventHandler(EventType.ROOT, onHide);
    }
    
    public void displayColor(){
        
    }

}
