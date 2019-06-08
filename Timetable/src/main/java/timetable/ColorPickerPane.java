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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import static timetable.GUI.ANIMATION_DISTANCE;
import static timetable.GUI.ANIMATION_DURATION;

/**
 *
 * @author Tobias
 */
public class ColorPickerPane extends SomePane {

    Label preview;
    JFXSlider[] sliders;

    TranslateTransition SlideIn;
    FadeTransition FadeIn;
    TranslateTransition SlideOut;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;

    EventHandler<Event> onHide;
    Button hideEvent = new Button();

    Color color;

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

        preview = new Label();
        preview.setPrefSize(500, 500);
        preview.getStyleClass().add("roundedLabel");
        getPane().add(preview, 0, 0);

        sliders = new JFXSlider[3];
        for (int i = 0; i < sliders.length; i++) {
            JFXSlider s = new JFXSlider();
            s.setMax(255);
            s.valueProperty().addListener(event -> {
                displayColor();
            });
            sliders[i] = s;
            getPane().add(s, 0, i + 1);
        }

        getPane().getChildren().remove(getDone());
        getPane().add(getDone(), 0, 4);

    }

    public void showOnCoordinates(double x, double y, JFXButton source, Color color) {
        setSource(source);

        int size = getPane().getChildren().size();

        double w = source.getHeight();
        double h = source.getHeight();

        show(x, y, w, h, color);
    }

    public void show(JFXButton source, Color color) {
        setSource(source);

        int size = getPane().getChildren().size();

        double x = source.getLayoutX() + 1;
        double y = source.getLayoutY() + 1;
        double w = source.getHeight();
        double h = source.getHeight();

        show(x, y, w, h, color);
    }

    private void show(double x, double y, double w, double h, Color color) {
        int size = getPane().getChildren().size();

        setHidden(false);

        getPane().setPrefWidth(w * getWidthFactor());
        getPane().setPrefHeight(h * size * getHeightFactor());
        getPane().setLayoutX(x);
        getPane().setLayoutY(y);

        this.color = color;

        updateSliderValues();
        displayColor();

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

    public void updateColor() {
        updateBaseColor();
        displayColor();
        getPane().setStyle("-fx-background-color:" + GUI.bg3);
        for (JFXSlider s : sliders) {
            GUI.applyColorsToSlider(s);
        }
    }

    public void displayColor() {
        color = Color.hsb(sliders[0].getValue() / 255, sliders[1].getValue() / 255, sliders[2].getValue() / 255);
        System.out.print(GUI.toRGBCode(color));
        preview.setStyle("-fx-background-color:" + GUI.toRGBCode(color));
    }

    public void updateSliderValues() {
        sliders[0].setValue(color.getHue() * 255);
        sliders[1].setValue(color.getSaturation() * 255);
        sliders[2].setValue(color.getBrightness() * 255);
    }

}
