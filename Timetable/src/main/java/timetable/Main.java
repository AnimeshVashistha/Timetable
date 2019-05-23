package timetable;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 *
 * @author Tobias
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GUI gui = new GUI();
        Scene scene = new Scene(gui.getParent());
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
                }
            }
        });

        stage.setTitle("TTable");
        stage.getIcons().add(new Image("/images/Timetable.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
