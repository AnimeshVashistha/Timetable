package timetable;

import com.jfoenix.controls.JFXButton;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import timetable.Datatypes.Timetable;

/**
 *
 * @author user
 */
public class TimetablePane {

    final static double heightFactor = 0.4;

    GridPane pane;
    String buttonStyle = "notRoundedButton";

    public TimetablePane() {
        pane = new GridPane();
    }

    public void update(List<Timetable> timetables, EventHandler<ActionEvent> buttonPressed, int index) {
        pane.getChildren().removeIf(node -> (node.getClass() == JFXButton.class));
        for (int i = 0; i < timetables.size(); i++) {
            JFXButton button = new JFXButton(timetables.get(i).getName());
            button.getStyleClass().add(buttonStyle);
            button.setRipplerFill(Color.web(GUI.primaryColor));
            button.setPrefWidth(500);
            button.setPrefHeight(150);
            if(i == index){
                button.setStyle("-fx-background-color:" + GUI.primaryColor + "44");
            }
            button.addEventHandler(ActionEvent.ACTION, buttonPressed);
            pane.add(button, 0, i, 1, 1);
        }
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

    public GridPane getPane() {
        return pane;
    }

    public void setPane(GridPane pane) {
        this.pane = pane;
    }

}
