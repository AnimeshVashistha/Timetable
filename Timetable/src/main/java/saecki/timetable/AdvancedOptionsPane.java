package saecki.timetable;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class AdvancedOptionsPane extends SomePane {

    Node specificFocus;
    boolean requestSpecificFocus = false;

    TranslateTransition SlideIn;
    FadeTransition FadeIn;
    TranslateTransition SlideOut;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;

    EventHandler<Event> onShow;
    Button showEvent = new Button();

    EventHandler<Event> onHide;
    Button hideEvent = new Button();

    public AdvancedOptionsPane(Pane parent) {
        super(parent);

        setWidthFactor(3);

        SlideIn = new TranslateTransition(Duration.millis(GUI.ANIMATION_DURATION));
        SlideIn.setFromY(GUI.ANIMATION_DISTANCE);
        SlideIn.setToY(0);

        FadeIn = new FadeTransition(Duration.millis(GUI.ANIMATION_DURATION));
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);

        SlideOut = new TranslateTransition(Duration.millis(GUI.ANIMATION_DURATION));
        SlideOut.setToY(GUI.ANIMATION_DISTANCE);

        FadeOut = new FadeTransition(Duration.millis(GUI.ANIMATION_DURATION));
        FadeOut.setToValue(0);

        show = new ParallelTransition(getPane());
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(getPane());
        hide.getChildren().add(SlideOut);
        hide.getChildren().add(FadeOut);
        hide.setOnFinished(event -> getPane().setVisible(false));
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

        if (onShow != null) {
            showEvent.fire();
        }

        Node toFocus;
        if (requestSpecificFocus) {
            toFocus = specificFocus;
        } else {
            toFocus = getPane().getChildren().get(0);
        }

        Timeline focus = new Timeline(new KeyFrame(
                Duration.millis(GUI.ANIMATION_DURATION * GUI.FOCUS_ANIMATION_OFFSET_FACTOR),
                n -> toFocus.requestFocus()));
        focus.play();

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

    public void setOnShow(EventHandler<Event> onShow) {
        if (this.onShow != null) {
            showEvent.removeEventHandler(EventType.ROOT, this.onShow);
        }
        this.onShow = onShow;
        showEvent.addEventHandler(EventType.ROOT, onShow);
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

    public void add(Node n) {
        int size = getPane().getChildren().size();
        getPane().getChildren().remove(getDone());
        getPane().add(n, 0, size - 1, 1, 1);
        getPane().add(getDone(), 0, size, 1, 1);
    }

    public Node getSpecificFocus() {
        return specificFocus;
    }

    public void setSpecificFocus(Node specificFocus) {
        this.specificFocus = specificFocus;
    }

    public boolean isRequestSpecificFocus() {
        return requestSpecificFocus;
    }

    public void setRequestSpecificFocus(boolean requestSpecificFocus) {
        this.requestSpecificFocus = requestSpecificFocus;
    }

}
