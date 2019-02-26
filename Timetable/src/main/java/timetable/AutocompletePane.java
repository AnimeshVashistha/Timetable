package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class AutocompletePane extends SomePane {
    
    GridPane pane = new GridPane();
    Pane parent;
    JFXTextField source;
    
    TranslateTransition SlideIn;
    FadeTransition FadeIn;
    TranslateTransition SlideOut;
    FadeTransition FadeOut;
    ParallelTransition show;
    ParallelTransition hide;
    
    int animationDuration = 200;
    int animationDistance = 50;
    double widthFactor = 3;
    double heightFactor = 0.6;
    double fontFactor = 0.2;
    boolean hidden = true;
    
    String defaultButtonStyle = "notRoundedButton";
    String bottomButtonStyle = "roundedBottomButton";
    
    public AutocompletePane(Pane parent) {
        this.parent = parent;
        pane.getStyleClass().add("customPane");
        pane.setPrefHeight(20);
        pane.setPrefWidth(20);
        pane.setVisible(false);
        
        this.parent = parent;
        parent.getChildren().add(pane);
        
        SlideIn = new TranslateTransition(Duration.millis(animationDuration));
        SlideIn.setFromY(animationDistance);
        SlideIn.setToY(0);
        
        FadeIn = new FadeTransition(Duration.millis(animationDuration));
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);
        
        SlideOut = new TranslateTransition(Duration.millis(animationDuration));
        SlideOut.setToY(animationDistance);
        
        FadeOut = new FadeTransition(Duration.millis(animationDuration));
        FadeOut.setToValue(0);
        
        show = new ParallelTransition(pane);
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);
        
        hide = new ParallelTransition(pane);
        hide.getChildren().add(SlideOut);
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
        
        double x = source.getLayoutX();
        double y = source.getLayoutY();
        double w = source.getHeight();
        double h = source.getHeight();
        
        show(x, y, w, h);
    }
    
    private void show(double x, double y, double w, double h) {
        int size = pane.getChildren().size();
        
        hidden = false;
        
        pane.setPrefWidth(w * widthFactor);
        pane.setPrefHeight(h * size * heightFactor);
        pane.setLayoutX(x + w);
        pane.setLayoutY(y + h);
        
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
    
    public void setFields(List<Subject> options) {
        pane.getChildren().removeIf(n -> (n.getClass() == Label.class));
        for (int i = 0; i < options.size() - 1; i++) {
            Label l = new Label(options.get(i).getSubject());
            l.getStyleClass().add(defaultButtonStyle);
            pane.getChildren().add(l);
        }
        Label l = new Label(options.get(options.size() - 1).getSubject());
        l.getStyleClass().add(bottomButtonStyle);
        pane.getChildren().add(l);
    }
    
    public void setLocation(double x, double y) {
        pane.setLayoutX(x);
        pane.setLayoutY(y);
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
    
}
