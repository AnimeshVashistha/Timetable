/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class SidebarPane extends SomePane{

    GridPane pane = new GridPane();
    Pane parent;
    JFXButton done = new JFXButton();
    JFXButton source;
    Node specificFocus;

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
    
    boolean hidden = true;
    
    @Override
    public void hide() {
        if (hidden == false) {
            hidden = true;

            new Timeline(
                    new KeyFrame(Duration.millis(FXMLController.animationDuration), n -> pane.setVisible(false))
            ).play();

            hide.play();
        }
    }

    @Override
    public void cancel() {
    
    }
    
}
