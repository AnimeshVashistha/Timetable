package timetable;

import com.jfoenix.controls.JFXButton;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    
    public void update(List<Timetable> timetables, EventHandler<ActionEvent> buttonPressed) {
        pane.getChildren().removeIf(node -> (node.getClass() == JFXButton.class));
        for (Timetable t : timetables) {
            JFXButton button = new JFXButton(t.getName());
            button.getStyleClass().add(buttonStyle);
            button.setRipplerFill(Color.web(GUI.ripplerFill));
            button.setPrefWidth(500);
            button.setPrefHeight(150);
            button.addEventHandler(ActionEvent.ACTION, buttonPressed);
            pane.getChildren().add(button);
        }
    }
    
    public void scale(double h) {
        int size = pane.getChildren().size();
        pane.setPrefHeight(h * size * heightFactor);
        
        for (Node n : pane.getChildren()) {
            JFXButton button = (JFXButton) n;
            button.setFont(new Font(h * GUI.fontFactor));
        }
    }
    
    public GridPane getPane() {
        return pane;
    }
    
    public void setPane(GridPane pane) {
        this.pane = pane;
    }
    
}
