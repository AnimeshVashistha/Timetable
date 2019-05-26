package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.util.Duration;
import static timetable.GUI.ANIMATION_DISTANCE;
import static timetable.GUI.ANIMATION_DURATION;

/**
 *
 * @author Tobias
 */
public class Menu {

    GUI gui;

    ScrollPane pane;
    AnchorPane content;
    GridPane settings;

    Label settingsLabel;

    Line[] separators;

    Label colorSection;
    HBox colorBox;
    JFXButton[] colorButtons;
    Label customColorLabel;
    JFXToggleButton customColor;
    Label colorModeLabel;
    JFXToggleButton colorMode;

    Label timeSection;
    TimePickerPane timePicker;
    Label startTimeLabel;
    JFXButton startTime;
    Label smallPauseLabel;
    JFXSlider smallPause;
    Label middlePauseLabel;
    JFXSlider middlePause;
    Label bigPauseLabel;
    JFXSlider bigPause;
    Label lessonLengthLabel;
    JFXSlider lessonLength;
    HBox timeBox;
    JFXButton applyToA;
    JFXButton applyToB;
    JFXButton applyToBoth;

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

    public Menu(GUI gui) {

        this.gui = gui;

        pane = new ScrollPane();
        pane.setHbarPolicy(ScrollBarPolicy.NEVER);
        pane.setStyle("-fx-focus-color: transparent;");
        pane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaX() != 0) {
                    event.consume();
                }
            }
        });

        content = new AnchorPane();
        content.setStyle("-fx-background-color:" + gui.transparent);
        pane.setContent(content);

        settings = new GridPane();
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(30);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(30);
        settings.getColumnConstraints().addAll(cc1, cc2);
        content.getChildren().add(settings);
        content.setTopAnchor(settings, 0d);
        content.setRightAnchor(settings, 0d);
        content.setBottomAnchor(settings, 0d);
        content.setLeftAnchor(settings, 0d);

        settingsLabel = new Label("Settings");
        settings.add(settingsLabel, 0, 0, 2, 1);

        separators = new Line[1];
        for (int i = 0; i < separators.length; i++) {
            separators[i] = new Line();
            separators[i].setStrokeWidth(2);
            separators[i].setStrokeLineCap(StrokeLineCap.ROUND);
        }

        //accent color
        colorSection = new Label("Color");
        settings.add(colorSection, 0, 1);

        colorBox = new HBox();
        settings.add(colorBox, 0, 2, 3, 1);

        colorButtons = new JFXButton[GUI.ac1s.length];
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new JFXButton();
            colorButtons[i].getStyleClass().add("roundedButton");
            colorButtons[i].setStyle("-fx-background-color:" + GUI.ac1s[i]);
            colorButtons[i].setOnAction(event -> {
                setAccentColor(event);
            });
            colorBox.getChildren().add(colorButtons[i]);
        }
        //custom accent color
        customColorLabel = new Label("Custom Color");
        settings.add(customColorLabel, 0, 3);
        customColor = new JFXToggleButton();
        customColor.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        settings.add(customColor, 1, 3);

        //color mode
        colorModeLabel = new Label("Darkmode");
        settings.add(colorModeLabel, 0, 4);
        colorMode = new JFXToggleButton();
        colorMode.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        settings.add(colorMode, 1, 4);

        //times
        settings.add(separators[0], 0, 5, 2, 1);

        timeSection = new Label("Time");
        settings.add(timeSection, 0, 6);

        timePicker = new TimePickerPane(content);
        timePicker.setOnHide(event -> {
            startTime.setText(timePicker.getTime().format());
            gui.tm.getCurrentTablePair().setStartTime(timePicker.getTime());
        });

        startTimeLabel = new Label("Start Time");
        settings.add(startTimeLabel, 0, 7);
        startTime = new JFXButton();
        startTime.getStyleClass().add("roundedButton");
        startTime.setOnAction(event -> {
            timePicker.showOnCoordinates(
                    startTime.getLayoutX(),
                    startTime.getLayoutY(),
                    startTime,
                    gui.tm.getCurrentTablePair().getStartTime()
            );
        });
        settings.add(startTime, 1, 7);

        smallPauseLabel = new Label("Small Pause");
        settings.add(smallPauseLabel, 0, 8);
        smallPause = new JFXSlider();
        smallPause.setMax(15);
        smallPause.valueProperty().addListener(event -> {
            gui.tm.getCurrentTablePair().setSmallPause((int) smallPause.getValue());
        });
        settings.add(smallPause, 1, 8);

        middlePauseLabel = new Label("Middle Pause");
        settings.add(middlePauseLabel, 0, 9);
        middlePause = new JFXSlider();
        middlePause.setMax(30);
        middlePause.valueProperty().addListener(event -> {
            gui.tm.getCurrentTablePair().setMiddlePause((int) middlePause.getValue());
        });
        settings.add(middlePause, 1, 9);

        bigPauseLabel = new Label("Big Pause");
        settings.add(bigPauseLabel, 0, 10);
        bigPause = new JFXSlider();
        bigPause.setMax(90);
        bigPause.valueProperty().addListener(event -> {
            gui.tm.getCurrentTablePair().setBigPause((int) bigPause.getValue());
        });
        settings.add(bigPause, 1, 10);

        lessonLengthLabel = new Label("lesson Lenght");
        settings.add(lessonLengthLabel, 0, 11);
        lessonLength = new JFXSlider();
        lessonLength.setMax(90);
        lessonLength.valueProperty().addListener(event -> {
            gui.tm.getCurrentTablePair().setLessonlength((int) lessonLength.getValue());
        });
        settings.add(lessonLength, 1, 11);

        timeBox = new HBox();
        settings.add(timeBox, 0, 12, 2, 1);

        applyToA = new JFXButton("Apply to A");
        applyToA.getStyleClass().add("roundedButton");
        applyToA.setOnAction(event -> {
            gui.tm.getCurrentTablePair().getA().initTimes();
            gui.initNewTimetable();
        });
        timeBox.getChildren().add(applyToA);

        applyToB = new JFXButton("Apply to B");
        applyToB.getStyleClass().add("roundedButton");
        applyToB.setOnAction(event -> {
            gui.tm.getCurrentTablePair().getB().initTimes();
            gui.initNewTimetable();
        });
        timeBox.getChildren().add(applyToB);

        applyToBoth = new JFXButton("apply to Both");
        applyToBoth.getStyleClass().add("roundedButton");
        applyToBoth.setOnAction(event -> {
            gui.tm.getCurrentTablePair().getA().initTimes();
            gui.tm.getCurrentTablePair().getB().initTimes();
            gui.initNewTimetable();
        });
        timeBox.getChildren().add(applyToBoth);

        SlideIn = new TranslateTransition(Duration.millis(ANIMATION_DURATION));
        SlideIn.setFromY(-ANIMATION_DISTANCE);
        SlideIn.setToY(0);

        FadeIn = new FadeTransition(Duration.millis(ANIMATION_DURATION));
        FadeIn.setFromValue(0);
        FadeIn.setToValue(1);

        SlideOut = new TranslateTransition(Duration.millis(ANIMATION_DURATION));
        SlideOut.setToY(-ANIMATION_DISTANCE);

        FadeOut = new FadeTransition(Duration.millis(ANIMATION_DURATION));
        FadeOut.setToValue(0);

        show = new ParallelTransition(settings);
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(settings);
        hide.getChildren().add(SlideOut);
        hide.getChildren().add(FadeOut);
        hide.setOnFinished(event -> {
            pane.setVisible(false);
        });
    }

    public void show(double w, double h) {
        colorMode.setSelected(!gui.lightMode);
        if (!gui.customColor) {
            highlightColorButton(gui.colorIndex);
        }
        pane.setVisible(true);
        updateTimeComponentsText();
        resize(w, h);
        show.play();
    }

    public void hide() {
        hide.play();
    }

    public void updateColors() {
        gui.updateColors();
        settings.setStyle("-fx-background-color:" + gui.bg1);
        settingsLabel.setTextFill(Color.web(gui.text));

        for (Line l : separators) {
            l.setStroke(Color.web(gui.text));
        }

        colorSection.setTextFill(Color.web(gui.text));
        highlightColorButton(gui.colorIndex);
        for (JFXButton b : colorButtons) {
            b.setRipplerFill(Color.web(gui.text));
        }
        customColorLabel.setTextFill(Color.web(gui.text));
        customColor.setToggleColor(Color.web(gui.ac1));
        customColor.setToggleLineColor(Color.web(gui.ac2));
        customColor.setUnToggleColor(Color.web(gui.fg1));
        customColor.setUnToggleLineColor(Color.web(gui.fg2));
        colorModeLabel.setTextFill(Color.web(gui.text));
        colorMode.setToggleColor(Color.web(gui.ac1));
        colorMode.setToggleLineColor(Color.web(gui.ac2));
        colorMode.setUnToggleColor(Color.web(gui.fg1));
        colorMode.setUnToggleLineColor(Color.web(gui.fg2));

        timeSection.setTextFill(Color.web(gui.text));
        timePicker.updateColor();
        startTimeLabel.setTextFill(Color.web(gui.text));
        startTime.setStyle("-fx-background-color:" + GUI.ac1);
        startTime.setTextFill(Color.web(gui.text));
        smallPauseLabel.setTextFill(Color.web(gui.text));
        middlePauseLabel.setTextFill(Color.web(gui.text));
        bigPauseLabel.setTextFill(Color.web(gui.text));
        lessonLengthLabel.setTextFill(Color.web(gui.text));
        applyToA.setStyle("-fx-background-color:" + gui.bg4);
        applyToA.setTextFill(Color.web(gui.text));
        applyToB.setStyle("-fx-background-color:" + gui.bg4);
        applyToB.setTextFill(Color.web(gui.text));
        applyToBoth.setStyle("-fx-background-color:" + gui.bg4);
        applyToBoth.setTextFill(Color.web(gui.text));
    }

    public void setAccentColor(ActionEvent event) {
        for (int i = 0; i < colorButtons.length; i++) {
            if (event.getSource() == colorButtons[i]) {
                highlightColorButton(i);
                gui.setAccentColor(i);
                updateColors();
            }
        }
    }

    public void highlightColorButton(int index) {
        colorButtons[gui.colorIndex].getStyleClass().removeIf(s -> (s == "customPane"));
        colorButtons[gui.colorIndex].setBorder(Border.EMPTY);
        colorButtons[index].getStyleClass().add("customPane");
        colorButtons[index].setBorder(
                new Border(
                        new BorderStroke(
                                Color.web(gui.text),
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(3),
                                new BorderWidths(4)
                        )
                )
        );
    }

    public void resize(double w, double h) {

        double padding = 0.1;
        double spacing = 0.2;
        double font1 = 0.5;
        double font2 = 0.35;
        double font3 = 0.2;

        h = h / 8;
        if (h < 80) {
            h = 80;
        }
        content.setPrefWidth(w);

        settings.setPadding(new Insets(h * 0.3));

        settingsLabel.setFont(new Font(h * font1));
        settingsLabel.setPadding(new Insets(h * padding));

        for (Line l : separators) {
            l.setStartX(h * spacing);
            l.setStartY(0);
            l.setEndX(w / 2 - h * spacing);
            l.setEndY(0);
            settings.setMargin(l, new Insets(h * padding));
        }

        colorSection.setFont(new Font(h * font2));
        colorSection.setPadding(new Insets(h * padding));
        for (JFXButton b : colorButtons) {
            b.setPrefSize(h, h);
        }
        colorBox.setSpacing(h * spacing);
        colorBox.setPadding(new Insets(h * padding));
        customColorLabel.setFont(new Font(h * font3));
        customColorLabel.setPadding(new Insets(h * padding));
        customColor.setScaleX(h * 0.012);
        customColor.setScaleY(h * 0.012);
        colorModeLabel.setFont(new Font(h * font3));
        colorModeLabel.setPadding(new Insets(h * padding));
        colorMode.setScaleX(h * 0.012);
        colorMode.setScaleY(h * 0.012);

        timeSection.setFont(new Font(h * font2));
        timeSection.setPadding(new Insets(h * padding));
        startTimeLabel.setFont(new Font(h * font3));
        startTimeLabel.setPadding(new Insets(h * padding));
        startTime.setPrefSize(h * 1.6, h * 0.8);
        startTime.setFont(new Font(h * font3));
        smallPauseLabel.setFont(new Font(h * font3));
        smallPauseLabel.setPadding(new Insets(h * padding));
        smallPause.getStyleClass().add("customSlider");
        middlePauseLabel.setFont(new Font(h * font3));
        middlePauseLabel.setPadding(new Insets(h * padding));
        bigPauseLabel.setFont(new Font(h * font3));
        bigPauseLabel.setPadding(new Insets(h * padding));
        lessonLengthLabel.setFont(new Font(h * font3));
        lessonLengthLabel.setPadding(new Insets(h * padding));
        timeBox.setSpacing(h * spacing);
        timeBox.setPadding(new Insets(h * padding));
        applyToA.setPrefSize(w / 6, h * 0.6);
        applyToA.setFont(new Font(h * font3));
        applyToB.setPrefSize(w / 6, h * 0.6);
        applyToB.setFont(new Font(h * font3));
        applyToBoth.setPrefSize(w / 6, h * 0.6);
        applyToBoth.setFont(new Font(h * font3));
    }

    public void updateTimeComponentsText() {
        startTime.setText(gui.tm.getCurrentTablePair().getStartTime().format());
        smallPause.setValue(gui.tm.getCurrentTablePair().getSmallPause());
        middlePause.setValue(gui.tm.getCurrentTablePair().getMiddlePause());
        bigPause.setValue(gui.tm.getCurrentTablePair().getBigPause());
        lessonLength.setValue(gui.tm.getCurrentTablePair().getLessonlength());
    }

    public ScrollPane getPane() {
        return pane;
    }

    public JFXToggleButton getColorMode() {
        return colorMode;
    }

}
