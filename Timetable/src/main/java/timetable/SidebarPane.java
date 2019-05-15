package timetable;

import com.jfoenix.controls.JFXButton;
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
public class SidebarPane extends SomePane {

    Node specificFocus;

    TranslateTransition SlideIn;
    TranslateTransition SlideOut;
    ParallelTransition show;
    ParallelTransition hide;

    EventHandler<Event> onShow;
    Button showEvent = new Button();

    EventHandler<Event> onHide;
    Button hideEvent = new Button();

    boolean requestSpecificFocus = false;

    public SidebarPane(Pane parent) {
        super(parent);

        getPane().getStyleClass().add("menuPane");
        setBottomButtonStyle("roundedBottomRightButton");
        setWidthFactor(1.2);

        SlideIn = new TranslateTransition(Duration.millis(SomePane.ANIMATION_DURATION));
        SlideIn.setToX(0);

        SlideOut = new TranslateTransition(Duration.millis(SomePane.ANIMATION_DURATION));

        show = new ParallelTransition(getPane());
        show.getChildren().add(SlideIn);

        hide = new ParallelTransition(getPane());
        hide.getChildren().add(SlideOut);
    }

    public void show(JFXButton source) {
        setSource(source);
        setHidden(false);

        double w = source.getWidth() * getWidthFactor();
        double h = getParent().getHeight();

        getPane().setPrefWidth(w);
        getPane().setPrefHeight(h);

        SlideIn.setFromX(-w);
        SlideOut.setToX(-w);

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
                Duration.millis(SomePane.ANIMATION_DURATION * SomePane.FOCUS_ANIMATION_OFFSET_FACTOR),
                n -> toFocus.requestFocus()));
        focus.play();

        getPane().setVisible(true);
        show.play();
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
            SlideOut.setToX(-getPane().getWidth());

            new Timeline(
                    new KeyFrame(Duration.millis(SomePane.ANIMATION_DURATION), n -> getPane().setVisible(false))
            ).play();

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
