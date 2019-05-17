package timetable;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import static timetable.GUI.ANIMATION_DISTANCE;
import static timetable.GUI.ANIMATION_DURATION;
import static timetable.GUI.FOCUS_ANIMATION_OFFSET_FACTOR;

/**
 *
 * @author Tobias
 */
public class TimePickerPane extends SomePane {

    AnchorPane backgroundPane;
    GridPane hourPane;
    JFXButton hourButton;
    Pane hourPaneOverlay;
    Circle hourPreviewCircle;
    GridPane minutePane;
    JFXButton minuteButton;
    Pane minutePaneOverlay;
    Circle minutePreviewCircle;

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

    public TimePickerPane(Pane parent) {
        super(parent);

        setWidthFactor(3);
        setHeightFactor(1);

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

        backgroundPane = new AnchorPane();
        backgroundPane.setPrefSize(500, 500);
        backgroundPane.getStyleClass().add("topRoundedPane");
        hourPane = new GridPane();
        hourButton = new JFXButton("hours");
        hourButton.getStyleClass().add("roundedTopLeftButton");
        hourButton.setPrefSize(500, 150);
        hourPaneOverlay = new Pane();
        hourPane.getChildren().add(hourPaneOverlay);
        minutePane = new GridPane();
        minuteButton = new JFXButton("minutes");
        minuteButton.getStyleClass().add("roundedTopRightButton");
        minuteButton.setPrefSize(500, 150);
        minutePaneOverlay = new Pane();
        minutePane.getChildren().add(minutePaneOverlay);
        backgroundPane.getChildren().add(hourPane);
        backgroundPane.getChildren().add(minutePane);

        RowConstraints rc1 = new RowConstraints();
        rc1.setPercentHeight(15);
        RowConstraints rc2 = new RowConstraints();
        rc2.setPercentHeight(70);
        RowConstraints rc3 = new RowConstraints();
        rc3.setPercentHeight(15);

        getPane().getRowConstraints().addAll(rc1, rc2, rc3);
        getPane().getChildren().remove(getDone());
        getPane().add(hourButton, 0, 0, 1, 1);
        getPane().add(minuteButton, 1, 0, 1, 1);
        getPane().add(backgroundPane, 0, 1, 2, 1);
        getPane().add(getDone(), 0, 2, 2, 1);
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

        Timeline focus = new Timeline(new KeyFrame(
                Duration.millis(ANIMATION_DURATION * FOCUS_ANIMATION_OFFSET_FACTOR),
                n -> getPane().getChildren().get(0).requestFocus()));
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

            new Timeline(
                    new KeyFrame(Duration.millis(ANIMATION_DURATION), n -> getPane().setVisible(false))
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

}
