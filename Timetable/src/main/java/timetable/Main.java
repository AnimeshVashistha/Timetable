package timetable;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONObject;
import static timetable.GUI.ANIMATION_DURATION;

/**
 *
 * @author Tobias
 */
public class Main extends Application {

    static final String TIMETABLE_MANAGER = "timetableManager";
    static final String GUI = "gui";

    DataManager dm;
    GUI gui;
    Scene scene;
    Timeline saveData;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        dm = new DataManager(getAppData() + "Timetable" + File.separator + "timetables.json");

        JSONObject data = dm.readData();

        gui = new GUI(data);
        gui.getPane().setCache(true);
        gui.getPane().setCacheHint(CacheHint.SPEED);
        gui.getSettings().setHostServices(getHostServices());

        scene = new Scene(gui.getPane());
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown()) {
                gui.controlDown = true;
                if (event.getCode() == KeyCode.TAB) {
                    gui.switchTab();
                } else if (event.getCode() == KeyCode.D) {
                    gui.toggleColorMode();
                    gui.updateColors();
                    if (!gui.settingsMenu.isHidden()) {
                        gui.settingsMenu.colorMode.setSelected(!gui.settingsMenu.colorMode.isSelected());
                    }
                } else if (event.getCode() == KeyCode.H) {
                    gui.hideAllMenus();
                } else if (event.getCode() == KeyCode.M) {
                    if (gui.menu.isHidden()) {
                        gui.menu();
                    }
                } else if (event.getCode() == KeyCode.N) {
                    gui.addTimetable();
                } else if (event.getCode() == KeyCode.S) {
                    if (gui.menu.isHidden()) {
                        gui.menu();
                        new Timeline(
                                new KeyFrame(
                                        Duration.millis(ANIMATION_DURATION * gui.FOCUS_ANIMATION_OFFSET_FACTOR),
                                        n -> gui.settingsMenu()
                                )
                        ).play();
                    } else {
                        gui.settingsMenu();
                    }
                }
            } else {
                if (event.getCode() == KeyCode.ESCAPE) {
                    gui.hideAllMenus();
                }
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.isControlDown()) {
            } else {
                gui.controlDown = false;
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

        stage.setTitle("Timetable");
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
        gui.resize();
    }

    public void saveData() {
        JSONObject data = new JSONObject();
        data.put(TIMETABLE_MANAGER, gui.tm.getDataToSave());
        data.put(GUI, gui.getDataToSave());
        dm.writeData(data);
    }

    public static String getAppData() {
        String path = "";
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            path = System.getenv("APPDATA");
        } else if (OS.contains("MAC")) {
            path = System.getProperty("user.home") + "/Library/";
        } else if (OS.contains("NUX")) {
            path = System.getProperty("user.home");
        } else {
            path = System.getProperty("user.dir");
        }

        path = path + File.separator;

        return path;
    }

}
