package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Label;
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
import javafx.scene.text.Font;
import javafx.util.Duration;
import static timetable.GUI.ANIMATION_DISTANCE;
import static timetable.GUI.ANIMATION_DURATION;

/**
 *
 * @author Tobias
 */
public class Menu implements Hideable {

    static String smallPauseText = "Small Pause ";
    static String middlePauseText = "Middle Pause ";
    static String bigPauseText = "Big Pause ";
    static String lessonLengthText = "Lesson Length ";

    GUI gui;

    ScrollPane pane;
    AnchorPane content;
    GridPane settings;

    Label settingsLabel;

    Label[] separators;

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

    boolean hidden = true;

    public Menu(GUI gui) {

        this.gui = gui;

        pane = new ScrollPane();
        pane.setHbarPolicy(ScrollBarPolicy.NEVER);
        pane.setStyle("-fx-focus-color: transparent;");
        pane.setVisible(false);
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
        settings.getStyleClass().add("customPane");
        content.getChildren().add(settings);
        content.setTopAnchor(settings, 0d);
        content.setRightAnchor(settings, 0d);
        content.setBottomAnchor(settings, 0d);
        content.setLeftAnchor(settings, 0d);

        settingsLabel = new Label("Settings");
        settings.add(settingsLabel, 0, 0, 2, 1);

        separators = new Label[1];
        for (int i = 0; i < separators.length; i++) {
            separators[i] = new Label();
            separators[i].setMinHeight(1);
            separators[i].setPrefHeight(1);
            separators[i].setMaxHeight(1);
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
        colorMode.selectedProperty().addListener(event -> {
            if (colorMode.isSelected()) {
                gui.setDarkColors();
            } else {
                gui.setLightColors();
            }
            gui.updateColors();
        });
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

        smallPauseLabel = new Label(smallPauseText);
        settings.add(smallPauseLabel, 0, 8);
        smallPause = new JFXSlider();
        smallPause.setMax(15);
        smallPause.valueProperty().addListener(event -> {
            int sp = (int) Math.round(smallPause.getValue());
            gui.tm.getCurrentTablePair().setSmallPause(sp);
            smallPauseLabel.setText(smallPauseText + sp);
        });
        settings.add(smallPause, 1, 8);

        middlePauseLabel = new Label(middlePauseText);
        settings.add(middlePauseLabel, 0, 9);
        middlePause = new JFXSlider();
        middlePause.setMax(30);
        middlePause.valueProperty().addListener(event -> {
            int mp = (int) Math.round(middlePause.getValue());
            gui.tm.getCurrentTablePair().setMiddlePause(mp);
            middlePauseLabel.setText(middlePauseText + mp);
        });
        settings.add(middlePause, 1, 9);

        bigPauseLabel = new Label(bigPauseText);
        settings.add(bigPauseLabel, 0, 10);
        bigPause = new JFXSlider();
        bigPause.setMax(90);
        bigPause.valueProperty().addListener(event -> {
            int bp = (int) Math.round(bigPause.getValue());
            gui.tm.getCurrentTablePair().setBigPause(bp);
            bigPauseLabel.setText(bigPauseText + bp);
        });
        settings.add(bigPause, 1, 10);

        lessonLengthLabel = new Label(lessonLengthText);
        settings.add(lessonLengthLabel, 0, 11);
        lessonLength = new JFXSlider();
        lessonLength.setMax(90);
        lessonLength.valueProperty().addListener(event -> {
            int ll = (int) Math.round(lessonLength.getValue());
            gui.tm.getCurrentTablePair().setLessonlength(ll);
            lessonLengthLabel.setText(lessonLengthText + ll);
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

    public void show(double x, double y, double w, double h) {
        if (hidden) {
            hidden = false;
            colorMode.setSelected(gui.darkMode);
            if (!gui.customColor) {
                highlightColorButton(gui.colorIndex);
            }
            pane.setVisible(true);
            updateTimeComponentsText();
            pane.setLayoutX(x);
            pane.setLayoutY(y);
            pane.setPrefWidth(w);
            pane.setPrefHeight(h);
            resize(w, h);
            new Timeline(
                    new KeyFrame(
                            Duration.millis(gui.ANIMATION_DURATION * gui.FOCUS_ANIMATION_OFFSET_FACTOR),
                            n -> colorButtons[0].requestFocus()
                    )
            ).play();
            show.play();
        }
    }

    public void hide() {
        if (!hidden) {
            hidden = true;
            hide.play();
        }
    }

    @Override
    public void cancel() {
        if (!hidden) {
            hidden = true;
            pane.setVisible(false);
        }
    }

    public void resize(double w, double h) {

        h = h / 8;
        if (h < 80) {
            h = 80;
        }

        Insets padding = new Insets(h * 0.1);
        Font font1 = new Font(h * 0.5);
        Font font2 = new Font(h * 0.35);
        Font font3 = new Font(h * 0.2);
        double spacing = h * 0.2;

        content.setPrefWidth(w);

        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPrefWidth(h * 3);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setMaxWidth(h * 3);
        ColumnConstraints cc3 = new ColumnConstraints();
        cc3.setFillWidth(true);
        settings.getColumnConstraints().removeIf(s -> s.getClass().equals(ColumnConstraints.class));
        settings.getColumnConstraints().addAll(cc1, cc2, cc3);
        settings.setPadding(new Insets(h * 0.3));

        for (Label s : separators) {
            s.setPrefWidth(w * 0.6);
            settings.setMargin(s, padding);
        }

        settingsLabel.setFont(font1);
        settingsLabel.setPadding(padding);

        colorSection.setFont(font2);
        colorSection.setPadding(padding);
        for (JFXButton b : colorButtons) {
            b.setPrefSize(h, h);
        }
        colorBox.setSpacing(spacing);
        colorBox.setPadding(padding);
        customColorLabel.setFont(font3);
        customColorLabel.setPadding(padding);
        customColor.setScaleX(h * 0.012);
        customColor.setScaleY(h * 0.012);
        colorModeLabel.setFont(font3);
        colorModeLabel.setPadding(padding);
        colorMode.setScaleX(h * 0.012);
        colorMode.setScaleY(h * 0.012);

        timeSection.setFont(font2);
        timeSection.setPadding(padding);
        startTimeLabel.setFont(font3);
        startTimeLabel.setPadding(padding);
        startTime.setPrefSize(h * 1.6, h * 0.8);
        startTime.setFont(font3);
        smallPauseLabel.setFont(font3);
        smallPauseLabel.setPadding(padding);
        smallPause.getStyleClass().add("customSlider");
        middlePauseLabel.setFont(font3);
        middlePauseLabel.setPadding(padding);
        bigPauseLabel.setFont(font3);
        bigPauseLabel.setPadding(padding);
        lessonLengthLabel.setFont(font3);
        lessonLengthLabel.setPadding(padding);
        timeBox.setSpacing(spacing);
        timeBox.setPadding(padding);
        applyToA.setPrefSize(w / 3, h * 0.6);
        applyToA.setFont(font3);
        applyToB.setPrefSize(w / 3, h * 0.6);
        applyToB.setFont(font3);
        applyToBoth.setPrefSize(w / 3, h * 0.6);
        applyToBoth.setFont(font3);
    }

    public void updateColors() {
        settings.setStyle("-fx-background-color:" + gui.bg1);
        settingsLabel.setTextFill(Color.web(gui.text));

        for (Label s : separators) {
            s.setStyle("-fx-background-color:" + gui.text);
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
                gui.updateColors();
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

    public void updateTimeComponentsText() {
        startTime.setText(gui.tm.getCurrentTablePair().getStartTime().format());
        int sp = gui.tm.getCurrentTablePair().getSmallPause();
        smallPause.setValue(sp);
        smallPauseLabel.setText(smallPauseText + sp);
        int mp = gui.tm.getCurrentTablePair().getMiddlePause();
        middlePause.setValue(mp);
        middlePauseLabel.setText(middlePauseText + mp);
        int bp = gui.tm.getCurrentTablePair().getBigPause();
        bigPause.setValue(bp);
        bigPauseLabel.setText(bigPauseText + bp);
        int ll = gui.tm.getCurrentTablePair().getLessonlength();
        lessonLength.setValue(ll);
        lessonLengthLabel.setText(lessonLengthText + ll);
    }

    public ScrollPane getPane() {
        return pane;
    }

    public JFXToggleButton getColorMode() {
        return colorMode;
    }

    public boolean isHidden() {
        return hidden;
    }

}
