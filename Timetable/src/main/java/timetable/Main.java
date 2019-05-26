package timetable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class Main extends Application {

    Menu settings;
    GUI gui;

    AnchorPane parent;
    Scene scene;

    boolean inSettings = false;

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
        settings.getPane().setVisible(false);
        settings.getColorMode().selectedProperty().addListener(event -> {
            if (gui.lightMode) {
                gui.setDarkColors();
            } else {
                gui.setLightColors();
            }
            parent.setStyle("-fx-background-color:" + gui.bg1);
            settings.updateColors();
        });

        parent = new AnchorPane();

        parent.getChildren().add(gui.getPane());
        parent.setTopAnchor(gui.getPane(), 0d);
        parent.setRightAnchor(gui.getPane(), 0d);
        parent.setBottomAnchor(gui.getPane(), 0d);
        parent.setLeftAnchor(gui.getPane(), 0d);

        parent.getChildren().add(settings.getPane());
        parent.setTopAnchor(settings.getPane(), 0d);
        parent.setRightAnchor(settings.getPane(), 0d);
        parent.setBottomAnchor(settings.getPane(), 0d);
        parent.setLeftAnchor(settings.getPane(), 0d);

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
                } else if (event.getCode() == KeyCode.T) {
                    showGUI();
                }
            }
        });
        scene.widthProperty().addListener(event -> {
            resizeCurrentScene();
        });
        scene.heightProperty().addListener(event -> {
            resizeCurrentScene();
        });

        settings.updateColors();
        stage.setTitle("TTable");
        stage.getIcons().add(new Image("/images/Timetable.png"));
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }

    @Override
    public void stop() {
        gui.tm.writeDataToFile();
    }

    public void showSettings() {
        if (!inSettings) {
            inSettings = true;
            new Timeline(
                    new KeyFrame(Duration.millis(GUI.ANIMATION_DURATION), n -> gui.getPane().setVisible(false))
            ).play();
            settings.show(scene.getWidth(), scene.getHeight());
        }
    }

    public void showGUI() {
        if (inSettings) {
            inSettings = false;
            settings.hide();
            gui.getPane().setVisible(true);
        }
    }

    public void resizeCurrentScene() {
        if (inSettings) {
            new Timeline(
                    new KeyFrame(Duration.millis(1), n -> settings.resize(scene.getWidth(), scene.getHeight()))
            ).play();
        } else {
            gui.cancelMenus();
            new Timeline(
                    new KeyFrame(Duration.millis(1), n -> gui.resizeFonts())
            ).play();
        }
    }

}
