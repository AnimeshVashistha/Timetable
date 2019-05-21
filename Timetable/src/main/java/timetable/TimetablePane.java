package timetable;

import com.jfoenix.controls.JFXButton;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import timetable.Datatypes.TimetablePair;

/**
 *
 * @author user
 */
public class TimetablePane {

    final static double heightFactor = 0.4;
    int index;

    GridPane pane;
    String buttonStyle = "notRoundedButton";

    public TimetablePane() {
        pane = new GridPane();
    }

    public void update(List<TimetablePair> timetables, EventHandler<ActionEvent> buttonPressed, int index) {
        this.index = index;
        pane.getChildren().removeIf(node -> (node.getClass() == JFXButton.class));
        for (int i = 0; i < timetables.size(); i++) {
            JFXButton button = new JFXButton(timetables.get(i).getName());
            button.getStyleClass().add(buttonStyle);
            button.setPrefWidth(500);
            button.setPrefHeight(150);
            if (i == index) {
                button.setStyle("-fx-background-color:" + GUI.ac1 + "44");
            }
            button.addEventHandler(ActionEvent.ACTION, buttonPressed);
            pane.add(button, 0, i, 1, 1);
        }
        updateColor();
    }

    public void scale(double h, double w) {
        int size = pane.getChildren().size();
        pane.setPrefHeight(h * size * heightFactor);
        pane.setPrefWidth(w);

        for (Node n : pane.getChildren()) {
            JFXButton button = (JFXButton) n;
            button.setFont(new Font(h * GUI.FONT_FACTOR));
        }
    }

    public void updateColor() {
        pane.setStyle("-fx-background-color:" + GUI.bg1);
        pane.getChildren().get(index).setStyle("-fx-background-color:" + GUI.ac1 + "44");
        for (Node n : getPane().getChildren()) {
            JFXButton b = (JFXButton) n;
            b.setRipplerFill(Color.web(GUI.ac1));
            b.setTextFill(Color.web(GUI.text));
        }
    }

    public GridPane getPane() {
        return pane;
    }

    public void setPane(GridPane pane) {
        this.pane = pane;
    }

}
