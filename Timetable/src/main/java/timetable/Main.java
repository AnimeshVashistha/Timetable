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
    
    /**
     * launching the javafx application
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * initializes a new datamanager, gui and a scene
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        //initializing a new datamanager with the os specific application data path
        dm = new DataManager(getAppData() + "Timetable" + File.separator + "timetables.json");

        //reading application data
        JSONObject data = dm.readData();

        //initializing a new gui and passing the application data
        gui = new GUI(data);
        //setting cache hints for better performance
        gui.getPane().setCache(true);
        gui.getPane().setCacheHint(CacheHint.SPEED);
        //passing a hostservice instance to settings for opening a browser
        gui.getSettings().setHostServices(getHostServices());

        //initializing a new scene and passing the parent
        scene = new Scene(gui.getPane());
        //adding css stylesheets
        scene.getStylesheets().add("/styles/Styles.css");
        //adding keypressed event handler
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown()) {
                gui.controlDown = true;
                if (event.getCode().equals(KeyCode.TAB)) {
                    gui.switchTab();
                } else if (event.getCode().equals(KeyCode.D)) {
                    gui.toggleColorMode();
                    gui.updateColors();
                    if (!gui.settingsMenu.isHidden()) {
                        gui.settingsMenu.colorMode.setSelected(!gui.settingsMenu.colorMode.isSelected());
                    }
                } else if (event.getCode().equals(KeyCode.H)) {
                    gui.hideAllMenus();
                } else if (event.getCode().equals(KeyCode.M)) {
                    if (gui.menu.isHidden()) {
                        gui.menu();
                    }
                } else if (event.getCode().equals(KeyCode.N)) {
                    gui.addTimetable();
                } else if (event.getCode().equals(KeyCode.S)) {
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
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    gui.hideAllMenus();
                }
            }
        });
        //adding a keyreleased event handler
        scene.setOnKeyReleased(event -> {
            if (!event.isControlDown()) {
                gui.controlDown = false;
            }
        });
        //adding scene resizing event handler
        scene.widthProperty().addListener(event -> {
            resized();
        });
        scene.heightProperty().addListener(event -> {
            resized();
        });

        //initializing a timeline for periodically saving application data
        saveData = new Timeline(
                new KeyFrame(Duration.seconds(30), n -> {
                    saveData();
                })
        );
        saveData.setCycleCount(Timeline.INDEFINITE);
        //starting the timeline
        saveData.play();

        //setting the title, icon, scene and minimum size
        stage.setTitle("Timetable");
        stage.getIcons().add(new Image("/images/Timetable.png"));
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        //showing the window
        stage.show();
    }

    /**
     * saving appliation data
     */
    @Override
    public void stop() {
        saveData();
    }

    /**
     * cancels all menus and resizes the content
     */
    public void resized() {
        gui.cancelMenus();
        gui.resize();
    }

    /**
     * collects data and settings from the gui and timetablemanager puts them in
     * a jsonobject and writes that object to a file
     */
    public void saveData() {
        JSONObject data = new JSONObject();
        data.put(TIMETABLE_MANAGER, gui.tm.getDataToSave());
        data.put(GUI, gui.getDataToSave());
        dm.writeData(data);
    }

    /**
     * returns the os specific prefered path for storing application data
     *
     * @return application data path
     */
    public static String getAppData() {
        String path = "";
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            path = System.getenv("APPDATA");
        } else if (OS.contains("MAC")) {
            path = System.getProperty("user.home") + "/Library/";
        } else if (OS.contains("NUX")) {
            path = System.getProperty("user.home") + File.separator + ".config";
        } else {
            path = System.getProperty("user.dir");
        }

        path = path + File.separator;

        return path;
    }

}
