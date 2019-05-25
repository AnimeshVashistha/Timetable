package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import static timetable.GUI.ANIMATION_DISTANCE;
import static timetable.GUI.ANIMATION_DURATION;
import static timetable.GUI.FOCUS_ANIMATION_OFFSET_FACTOR;

/**
 *
 * @author Tobias
 */
public class Menu implements Hideable {

    GUI gui;
    
    GridPane pane;
    JFXButton done;
    JFXButton[] colorButtons;
    Label darkModeLabel;
    JFXToggleButton darkMode;

    Node specificFocus;
    boolean requestSpecificFocus = false;
    boolean hidden = true;

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

    public Menu(GUI gui) {

        this.gui = gui;
        
        pane = new GridPane();

        done = new JFXButton("done");

        colorButtons = new JFXButton[GUI.ac1s.length];
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new JFXButton();
            colorButtons[i].setStyle("-fx-background-color:" + GUI.ac1s[i]);
            colorButtons[i].setPrefSize(150, 150);
            pane.setMargin(colorButtons[i], new Insets(50));
            pane.add(colorButtons[i], i%4, i/4);
        }

        SlideIn = new TranslateTransition(Duration.millis(ANIMATION_DURATION));
        SlideIn.setFromY(-ANIMATION_DISTANCE);
        SlideIn.setToY(0);

        FadeIn = new FadeTransition(Duration.millis(ANIMATION_DURATION));
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);

        SlideOut = new TranslateTransition(Duration.millis(ANIMATION_DURATION));
        SlideOut.setToY(-ANIMATION_DISTANCE);

        FadeOut = new FadeTransition(Duration.millis(ANIMATION_DURATION));
        FadeOut.setToValue(0);

        show = new ParallelTransition(pane);
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(pane);
        hide.getChildren().add(SlideOut);
        hide.getChildren().add(FadeOut);
    }

    private void show(JFXButton source) {
        int size = pane.getChildren().size();

        hidden = false;

        if (onShow != null) {
            showEvent.fire();
        }

        Node toFocus;
        if (requestSpecificFocus) {
            toFocus = specificFocus;
        } else {
            toFocus = pane.getChildren().get(0);
        }

        Timeline focus = new Timeline(new KeyFrame(
                Duration.millis(ANIMATION_DURATION * FOCUS_ANIMATION_OFFSET_FACTOR),
                n -> toFocus.requestFocus()));
        focus.play();

        pane.setVisible(true);
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
        if (hidden == false) {
            hidden = true;

            if (onHide != null) {
                hideEvent.fire();
            }

            new Timeline(
                    new KeyFrame(Duration.millis(ANIMATION_DURATION), n -> pane.setVisible(false))
            ).play();

            hide.play();
        }
    }

    @Override
    public void cancel() {
        hidden = true;
        pane.setVisible(false);
    }

    public void setOnHide(EventHandler<Event> onHide) {
        if (this.onHide != null) {
            hideEvent.removeEventHandler(EventType.ROOT, this.onHide);
        }
        this.onHide = onHide;
        hideEvent.addEventHandler(EventType.ROOT, onHide);
    }

    public void add(Node n) {
        int size = pane.getChildren().size();
        pane.getChildren().remove(done);
        pane.add(n, 0, size - 1, 1, 1);
        pane.add(done, 0, size, 1, 1);
    }

    public void updateColor() {
        pane.setStyle("-fx-background-color:" + GUI.bg1);
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

    public GridPane getPane() {
        return pane;
    }

}
