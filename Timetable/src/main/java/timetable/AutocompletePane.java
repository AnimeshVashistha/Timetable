package timetable;

import com.jfoenix.controls.JFXTextField;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class AutocompletePane extends SomePane {

    GridPane pane = new GridPane();
    Pane parent;
    JFXTextField source;

    FadeTransition FadeIn;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;

    int animationDuration = 200;
    int animationDistance = 50;
    double widthFactor = 3;
    double heightFactor = 0.6;
    double fontFactor = 0.3;
    double paddingFactor = 0.1;
    boolean hidden = true;

    String defaultButtonStyle = "notRoundedButton";
    String bottomButtonStyle = "roundedBottomButton";

    public AutocompletePane(Pane parent) {
        this.parent = parent;
        pane.getStyleClass().add("autocompletePane");
        pane.setPrefHeight(20);
        pane.setPrefWidth(20);
        pane.setVisible(false);

        this.parent = parent;
        parent.getChildren().add(pane);

        FadeIn = new FadeTransition(Duration.millis(animationDuration));
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);

        FadeOut = new FadeTransition(Duration.millis(animationDuration));
        FadeOut.setToValue(0);

        show = new ParallelTransition(pane);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(pane);
        hide.getChildren().add(FadeOut);
    }

    public void showOnCoordinates(double x, double y, JFXTextField source) {
        this.source = source;

        int size = pane.getChildren().size();

        double w = source.getHeight();
        double h = source.getHeight();

        show(x, y, w, h);
    }

    public void show(JFXTextField source) {
        this.source = source;

        int size = pane.getChildren().size();

        double x = source.getParent().getLayoutX();
        double y = source.getParent().getLayoutY();
        double w = source.getHeight();
        double h = source.getHeight();

        show(x, y, w, h);
    }

    private void show(double x, double y, double w, double h) {
        int size = pane.getChildren().size();

        hidden = false;

        pane.setPrefWidth(w * widthFactor);
        pane.setPrefHeight(h * size * heightFactor);
        pane.setLayoutX(x + source.getPadding().getLeft());
        GridPane parent = (GridPane) source.getParent();
        pane.setLayoutY(y + source.getHeight() + 2);
        pane.setVisible(true);
        show.play();
    }

    public void show() {
        pane.setVisible(true);
    }

    @Override
    public void hide() {
        if (hidden == false) {
            hidden = true;
            autocompleteFucused = false;

            new Timeline(
                    new KeyFrame(Duration.millis(animationDuration), n -> pane.setVisible(false))
            ).play();

            hide.play();
        }
    }

    @Override
    public void cancel() {
        pane.setVisible(false);
    }

    EventHandler<MouseEvent> onClick;

    public void setOnClick(EventHandler<MouseEvent> onClick) {
        this.onClick = onClick;
    }

    public void setFields(JFXTextField source, List<Subject> options) {
        this.source = source;

        pane.getChildren().removeIf(n -> (n.getClass() == Label.class));

        for (int i = 0; i < options.size(); i++) {
            Label l = new Label(options.get(i).getSubject());
            l.setPrefSize(150, 500);
            l.setPadding(new Insets(0, 0, 0, source.getHeight() * paddingFactor));
            l.setFont(new Font(source.getHeight() * fontFactor));
            if (onClick != null) {
                l.addEventHandler(MouseEvent.MOUSE_CLICKED, onClick);
            }
            if (i == options.size() - 1) {
                l.getStyleClass().add(bottomButtonStyle);
            } else {
                l.getStyleClass().add(defaultButtonStyle);
            }
            pane.add(l, 0, i, 1, 1);
        }
    }

    public void setLocation(double x, double y) {
        pane.setLayoutX(x);
        pane.setLayoutY(y);
    }

    boolean autocompleteFucused = false;
    int autocompleteIndex = 0;

    public void upPressed() {
        if (pane.isVisible()) {
            if (autocompleteFucused) {
                unfocus(autocompleteIndex);
                if (autocompleteIndex <= 0) {
                    autocompleteFucused = false;
                } else {
                    autocompleteIndex--;
                    focus(autocompleteIndex);
                }
            } else {
                autocompleteFucused = true;
                autocompleteIndex = pane.getChildren().size() - 1;
                focus(autocompleteIndex);
            }
        }
    }

    public void downPressed() {
        if (pane.isVisible()) {
            if (autocompleteFucused) {
                unfocus(autocompleteIndex);
                if (autocompleteIndex >= pane.getChildren().size() - 1) {
                    autocompleteFucused = false;
                } else {
                    autocompleteIndex++;
                    focus(autocompleteIndex);
                }
            } else {
                autocompleteFucused = true;
                autocompleteIndex = 0;
                focus(autocompleteIndex);
            }
        }
    }

    public void focus(int index) {
        pane.getChildren().get(index).setStyle(FXMLController.selectedColor);
    }

    public void unfocus(int index) {
        pane.getChildren().get(index).setStyle(FXMLController.unselectedColor);
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

    public GridPane getPane() {
        return pane;
    }

    public boolean isAutocompleteFucused() {
        return autocompleteFucused;
    }

    public int getAutocompleteIndex() {
        return autocompleteIndex;
    }
}
