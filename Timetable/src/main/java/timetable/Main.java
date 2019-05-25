package timetable;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Tobias
 */
public class Main extends Application {

    Menu settings;
    GUI gui;

    AnchorPane parent;
    Scene scene;
    
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        gui = new GUI();
        gui.getSettingsButton().setOnAction(event -> {
            showSettings();
        });
        
        settings = new Menu(gui);
        
        parent = new AnchorPane();
        parent.getChildren().add(settings.getPane());
        parent.setTopAnchor(settings.getPane(), 0d);
        parent.setRightAnchor(settings.getPane(), 0d);
        parent.setBottomAnchor(settings.getPane(), 0d);
        parent.setLeftAnchor(settings.getPane(), 0d);
        
        parent.getChildren().add(gui.getPane());
        parent.setTopAnchor(gui.getPane(), 0d);
        parent.setRightAnchor(gui.getPane(), 0d);
        parent.setBottomAnchor(gui.getPane(), 0d);
        parent.setLeftAnchor(gui.getPane(), 0d);
        
        scene = new Scene(parent);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown()) {
                if (event.getCode() == KeyCode.TAB) {
                    gui.switchTab();
                } else if (event.getCode() == KeyCode.N) {
                    gui.addTimetable();
                }
            } else {
                if (event.getCode() == KeyCode.M) {
                    gui.menu();
                } else if (event.getCode() == KeyCode.S) {
                    showSettings();
                }
            }
        });
        
        
        stage.setTitle("TTable");
        stage.getIcons().add(new Image("/images/Timetable.png"));
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        gui.tm.writeDataToFile();
    }

    public void showSettings() {
        gui.getPane().setVisible(false);
        settings.getPane().setVisible(true);
    }

    public void showGUI() {
        settings.getPane().setVisible(false);
        gui.getPane().setVisible(true);
    }

}
