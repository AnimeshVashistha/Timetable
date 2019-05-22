package timetable;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import static timetable.GUI.ANIMATION_DISTANCE;
import static timetable.GUI.ANIMATION_DURATION;

/**
 *
 * @author Tobias
 */
public class MenuButton extends AnchorPane {

    Label background;
    Label label;
    JFXButton button;
    Pane menuIcon;
    Rectangle bar1;
    Rectangle bar2;
    Rectangle bar3;

    FadeTransition menuIconFadeIn;
    TranslateTransition menuIconSlideIn;
    FadeTransition menuIconFadeOut;
    TranslateTransition menuIconSlideOut;
    FadeTransition labelFadeIn;
    FadeTransition labelFadeOut;
    ParallelTransition showMenuIcon;
    ParallelTransition hideMenuIcon;

    public MenuButton() {
        this.setPrefSize(500, 150);
        background = new Label();
        background.getStyleClass().add("roundedShadowedButton");
        label = new Label();
        label.setAlignment(Pos.CENTER);
        menuIcon = new Pane();
        menuIcon.setOpacity(0);
        menuIcon.setTranslateX(-ANIMATION_DISTANCE);
        bar1 = new Rectangle(40, 5);
        bar1.xProperty().bind(this.widthProperty().subtract(this.heightProperty()).divide(2).add(this.heightProperty().multiply(0.15)));
        bar1.yProperty().bind(this.heightProperty().multiply(0.2));
        bar1.widthProperty().bind(this.heightProperty().multiply(0.7));
        bar1.heightProperty().bind(this.heightProperty().multiply(0.05));
        bar2 = new Rectangle(40, 5);
        bar2.xProperty().bind(this.widthProperty().subtract(this.heightProperty()).divide(2).add(this.heightProperty().multiply(0.15)));
        bar2.yProperty().bind(this.heightProperty().multiply(0.475));
        bar2.widthProperty().bind(this.heightProperty().multiply(0.7));
        bar2.heightProperty().bind(this.heightProperty().multiply(0.05));
        bar3 = new Rectangle(40, 5);
        bar3.xProperty().bind(this.widthProperty().subtract(this.heightProperty()).divide(2).add(this.heightProperty().multiply(0.15)));
        bar3.yProperty().bind(this.heightProperty().multiply(0.75));
        bar3.widthProperty().bind(this.heightProperty().multiply(0.7));
        bar3.heightProperty().bind(this.heightProperty().multiply(0.05));
        menuIcon.getChildren().add(0, bar1);
        menuIcon.getChildren().add(1, bar2);
        menuIcon.getChildren().add(2, bar3);
        button = new JFXButton();
        button.getStyleClass().add("roundedButton");

        this.getChildren().addAll(background, label, menuIcon, button);

        MenuButton.setTopAnchor(background, 0d);
        MenuButton.setRightAnchor(background, 0d);
        MenuButton.setBottomAnchor(background, 0d);
        MenuButton.setLeftAnchor(background, 0d);
        MenuButton.setTopAnchor(label, 0d);
        MenuButton.setRightAnchor(label, 0d);
        MenuButton.setBottomAnchor(label, 0d);
        MenuButton.setLeftAnchor(label, 0d);
        MenuButton.setTopAnchor(menuIcon, 0d);
        MenuButton.setRightAnchor(menuIcon, 0d);
        MenuButton.setBottomAnchor(menuIcon, 0d);
        MenuButton.setLeftAnchor(menuIcon, 0d);
        MenuButton.setTopAnchor(button, 0d);
        MenuButton.setRightAnchor(button, 0d);
        MenuButton.setBottomAnchor(button, 0d);
        MenuButton.setLeftAnchor(button, 0d);

        button.focusedProperty().addListener(event -> {
            if (button.isFocused()) {
                showMenuIcon.play();
            } else {
                hideMenuIcon.play();
            }
        });

        button.hoverProperty().addListener(event -> {
            if (button.isHover()) {
                showMenuIcon.play();
            } else {
                hideMenuIcon.play();
            }
        });
        menuIconSlideIn = new TranslateTransition(Duration.millis(ANIMATION_DURATION), menuIcon);
        menuIconSlideIn.setToX(0);
        menuIconFadeIn = new FadeTransition(Duration.millis(ANIMATION_DURATION), menuIcon);
        menuIconFadeIn.setToValue(1);
        menuIconSlideOut = new TranslateTransition(Duration.millis(ANIMATION_DURATION), menuIcon);
        menuIconSlideOut.setToX(-ANIMATION_DISTANCE);
        menuIconFadeOut = new FadeTransition(Duration.millis(ANIMATION_DURATION), menuIcon);
        menuIconFadeOut.setToValue(0);
        labelFadeIn = new FadeTransition(Duration.millis(ANIMATION_DURATION), label);
        labelFadeIn.setToValue(1);
        labelFadeOut = new FadeTransition(Duration.millis(ANIMATION_DURATION), label);
        labelFadeOut.setToValue(0);

        showMenuIcon = new ParallelTransition();
        showMenuIcon.getChildren().add(menuIconFadeIn);
        showMenuIcon.getChildren().add(menuIconSlideIn);
        showMenuIcon.getChildren().add(labelFadeOut);

        hideMenuIcon = new ParallelTransition();
        hideMenuIcon.getChildren().add(menuIconFadeOut);
        hideMenuIcon.getChildren().add(menuIconSlideOut);
        hideMenuIcon.getChildren().add(labelFadeIn);
    }

    public void updateColor() {
        background.setStyle("-fx-background-color:" + GUI.ac1);
        label.setTextFill(Color.web(GUI.text));
        bar1.setFill(Color.web(GUI.text));
        bar2.setFill(Color.web(GUI.text));
        bar3.setFill(Color.web(GUI.text));
    }

    public void setFont(Font font) {
        label.setFont(font);
    }

    public void setOnAction(EventHandler<ActionEvent> eventHandler) {
        button.setOnAction(eventHandler);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public void setRipplerFill(Paint ripplerFill) {
        button.setRipplerFill(ripplerFill);
    }

    public JFXButton getButton() {
        return button;
    }

}
