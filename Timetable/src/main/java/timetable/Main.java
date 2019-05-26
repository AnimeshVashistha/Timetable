package timetable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class Main extends Application {

    GUI gui;
    Scene scene;
    DataManager dm = new DataManager("timetables.json");
    Timeline saveData;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        gui = new GUI();

        scene = new Scene(gui.getPane());
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
                    //settings
                }
            }
        });
        scene.widthProperty().addListener(event -> {
            resize();
        });
        scene.heightProperty().addListener(event -> {
            resize();
        });

        saveData = new Timeline(
                new KeyFrame(Duration.seconds(30), n -> {
                    saveData();
                })
        );
        saveData.setCycleCount(Timeline.INDEFINITE);

        saveData.play();

        stage.setTitle("TTable");
        stage.getIcons().add(new Image("/images/Timetable.png"));
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }

    @Override
    public void stop() {
        saveData();
    }

    public void resize() {
        gui.cancelMenus();
        new Timeline(
                new KeyFrame(Duration.millis(1), n -> gui.resizeFonts())
        ).play();
    }
    
    public void saveData(){
        
    }
    
    public void readData(){
        
    }

}
