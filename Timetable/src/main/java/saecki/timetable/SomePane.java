package saecki.timetable;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Tobias
 */
public abstract class SomePane implements Hideable {

    private GridPane pane = new GridPane();
    private Pane parent;
    private JFXButton done = new JFXButton();
    private JFXButton source;

    private double widthFactor = 1.8;
    private double heightFactor = 0.5;
    private double fontFactor = 0.2;
    private boolean hidden = true;
    private String bottomButtonStyle = "roundedBottomButton";

    public SomePane(Pane parent) {
        setParent(parent);

        getPane().getStyleClass().add("customPane");
        getPane().setPrefHeight(20);
        getPane().setPrefWidth(20);
        getPane().setVisible(false);

        parent.getChildren().add(getPane());

        getDone().setText("Done");
        getDone().getStyleClass().add(bottomButtonStyle);
        getDone().setPrefWidth(500);
        getDone().setPrefHeight(150);

        getDone().setOnAction(n -> {
            hide();
        });

        updateBaseColor();
        getPane().getChildren().add(getDone());
    }

    public void updateBaseColor() {
        getPane().setStyle("-fx-background-color:" + GUI.bg1);
        getDone().setStyle("-fx-background-color:" + GUI.bg3);
        getDone().setTextFill(Color.web(GUI.text));
    }

    public Pane getParent() {
        return parent;
    }

    public void setParent(Pane parent) {
        this.parent = parent;
    }

    public JFXButton getSource() {
        return source;
    }

    public void setSource(JFXButton source) {
        this.source = source;
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

    public void setPane(GridPane pane) {
        this.pane = pane;
    }

    public JFXButton getDone() {
        return done;
    }

    public void setDone(JFXButton done) {
        this.done = done;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getBottomButtonStyle() {
        return bottomButtonStyle;
    }

    public void setBottomButtonStyle(String bottomButtonStyle) {
        this.bottomButtonStyle = bottomButtonStyle;
        getDone().getStyleClass().add(bottomButtonStyle);
    }
}
