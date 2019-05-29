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
import javafx.scene.layout.StackPane;
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

    static final int[] sliderMax = {15, 30, 90, 90};
    static String[] sliderText = {"Small Pause", "Middle Pause", "Big Pause", "Lesson Length"};

    static String smallPauseText = "Small Pause";
    static String middlePauseText = "Middle Pause";
    static String bigPauseText = "Big Pause";
    static String lessonLengthText = "Lesson Length";

    GUI gui;

    AnchorPane pane;
    ScrollPane scrollPane;
    AnchorPane content;
    GridPane settings;

    Label settingsLabel;

    Label[] separators;

    Label colorSection;
    ColorPickerPane colorPicker;

    Label defaultColorLabel;
    HBox defaultColorBox;
    JFXButton[] defaultColorButtons;
    Label customColorLabel;
    HBox customColorBox;
    JFXButton[] customColorButtons;
    Label colorModeLabel;
    JFXToggleButton colorMode;

    Label timeSection;
    TimePickerPane timePicker;

    Label startTimeLabel;
    JFXButton startTime;

    AnchorPane[] sliderPanes;
    Label[] sliderLabels;
    Label[] sliderValues;
    JFXSlider[] sliders;

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

        pane = new AnchorPane();
        pane.getStyleClass().add("customPane");
        pane.setVisible(false);

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-focus-color: transparent;");
        scrollPane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaX() != 0) {
                    event.consume();
                }
            }
        });
        pane.getChildren().add(scrollPane);
        pane.setTopAnchor(scrollPane, 0d);
        pane.setRightAnchor(scrollPane, 0d);
        pane.setBottomAnchor(scrollPane, 0d);
        pane.setLeftAnchor(scrollPane, 0d);

        content = new AnchorPane();
        content.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 6;
            double width = scrollPane.getContent().getBoundsInLocal().getWidth();
            double vvalue = scrollPane.getVvalue();
            scrollPane.setVvalue(vvalue + -deltaY / width);
        });
        content.setStyle("-fx-background-color:" + gui.transparent);
        scrollPane.setContent(content);

        settings = new GridPane();
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

        defaultColorLabel = new Label("Default Colors");
        settings.add(defaultColorLabel, 0, 2);

        defaultColorBox = new HBox();
        settings.add(defaultColorBox, 0, 3, 3, 1);

        defaultColorButtons = new JFXButton[GUI.ac1s.length];
        for (int i = 0; i < defaultColorButtons.length; i++) {
            JFXButton b = new JFXButton();
            b.getStyleClass().add("roundedButton");
            b.setStyle("-fx-background-color:" + GUI.ac1s[i]);
            b.setOnAction(event -> {
                setAccentColor(event);
            });
            b.focusedProperty().addListener(event -> {
                focusColorButton(b);
            });
            defaultColorButtons[i] = b;
            defaultColorBox.getChildren().add(defaultColorButtons[i]);
        }
        //custom colors
        customColorLabel = new Label("Custom Colors");
        settings.add(customColorLabel, 0, 4);

        customColorBox = new HBox();
        settings.add(customColorBox, 0, 5, 3, 1);

        customColorButtons = new JFXButton[GUI.ac1s.length];
        for (int i = 0; i < defaultColorButtons.length; i++) {
            JFXButton b = new JFXButton();
            b.getStyleClass().add("roundedButton");
            b.setStyle("-fx-background-color:" + GUI.customAcs[i]);
            b.setOnAction(event -> {
                setCustomAccentColor(event);
            });
            b.focusedProperty().addListener(event -> {
                focusColorButton(b);
            });
            customColorButtons[i] = b;
            customColorBox.getChildren().add(customColorButtons[i]);
        }

        //color mode
        colorModeLabel = new Label("Darkmode");
        settings.add(colorModeLabel, 0, 6);
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
        settings.add(colorMode, 1, 6);

        //times
        settings.add(separators[0], 0, 7, 2, 1);

        timeSection = new Label("Time");
        settings.add(timeSection, 0, 8);

        timePicker = new TimePickerPane(content);
        timePicker.setOnHide(event -> {
            startTime.setText(timePicker.getTime().format());
            gui.tm.getCurrentTablePair().setStartTime(timePicker.getTime());
        });

        startTimeLabel = new Label("Start Time");
        settings.add(startTimeLabel, 0, 9);
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
        settings.add(startTime, 1, 9);

        sliderPanes = new AnchorPane[4];
        sliderLabels = new Label[sliderPanes.length];
        sliderValues = new Label[sliderPanes.length];
        sliders = new JFXSlider[sliderPanes.length];
        for (int i = 0; i < sliderPanes.length; i++) {
            AnchorPane sp = new AnchorPane();
            sliderPanes[i] = sp;
            settings.add(sp, 0, 10 + i);
            Label sl = new Label(sliderText[i]);
            sliderLabels[i] = sl;
            Label sv = new Label();
            sliderValues[i] = sv;
            sv.getStyleClass().add("roundedButton");
            sp.getChildren().addAll(sl, sv);
            sp.setRightAnchor(sv, 0d);
            JFXSlider s = new JFXSlider();
            s.setMax(sliderMax[i]);
            s.valueProperty().addListener(event -> {
                int val = (int) Math.round(s.getValue());
                if (s.equals(sliders[0])) {
                    gui.tm.getCurrentTablePair().setSmallPause(val);
                } else if (s == sliders[1]) {
                    gui.tm.getCurrentTablePair().setMiddlePause(val);
                } else if (s == sliders[2]) {
                    gui.tm.getCurrentTablePair().setBigPause(val);
                } else if (s == sliders[3]) {
                    gui.tm.getCurrentTablePair().setLessonLength(val);
                }
                sv.setText(String.format("%02d", val));
            });
            s.focusedProperty().addListener(event -> {
                focusSlider(s, sv);
            });
            sliders[i] = s;
            settings.add(s, 1, 10 + i);
        }

        timeBox = new HBox();
        settings.add(timeBox, 0, 14, 2, 1);

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

        show = new ParallelTransition(pane);
        show.getChildren().add(SlideIn);
        show.getChildren().add(FadeIn);

        hide = new ParallelTransition(pane);
        hide.getChildren().add(SlideOut);
        hide.getChildren().add(FadeOut);
        hide.setOnFinished(event -> pane.setVisible(false));
    }

    public void show(double x, double y, double w, double h) {
        if (hidden) {
            hidden = false;
            colorMode.setSelected(gui.darkMode);
            if (!gui.customColor) {
                highlightColorButton(gui.colorIndex, gui.customColor);
            }
            updateTimeComponentsText();
            pane.setLayoutX(x);
            pane.setLayoutY(y);
            pane.setPrefWidth(w);
            pane.setMinHeight(h);
            pane.setPrefHeight(h);
            pane.setMaxHeight(h);
            resize(w, h);
            updateColors();
            pane.setVisible(true);
            new Timeline(
                    new KeyFrame(
                            Duration.millis(ANIMATION_DURATION * gui.FOCUS_ANIMATION_OFFSET_FACTOR),
                            n -> defaultColorButtons[0].requestFocus()
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
        defaultColorLabel.setFont(font3);
        defaultColorLabel.setPadding(padding);
        defaultColorBox.setSpacing(spacing);
        defaultColorBox.setPadding(padding);
        for (JFXButton b : defaultColorButtons) {
            b.setPrefSize(h, h);
        }
        customColorLabel.setFont(font3);
        customColorLabel.setPadding(padding);
        customColorBox.setSpacing(spacing);
        customColorBox.setPadding(padding);
        for (JFXButton b : customColorButtons) {
            b.setPrefSize(h, h);
        }
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
        for (int i = 0; i < sliderPanes.length; i++) {
            sliderLabels[i].setFont(font3);
            sliderLabels[i].setPadding(padding);
            sliderValues[i].setFont(font3);
            sliderValues[i].setPadding(padding);
            sliders[i].setMaxWidth((3) / (0.012));
            sliders[i].setTranslateX((h * 0.012 - 1) * h);
            sliders[i].setScaleX(h * 0.012);
            sliders[i].setScaleY(h * 0.012);
        }
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

        pane.setStyle("-fx-background-color:" + gui.bg1);
        settingsLabel.setTextFill(Color.web(gui.text));

        for (Label s : separators) {
            s.setStyle("-fx-background-color:" + gui.text);
        }

        colorSection.setTextFill(Color.web(gui.text));
        defaultColorLabel.setTextFill(Color.web(gui.text));
        highlightColorButton(gui.colorIndex, gui.customColor);
        for (JFXButton b : defaultColorButtons) {
            b.setRipplerFill(Color.web(gui.text));
        }
        customColorLabel.setTextFill(Color.web(gui.text));
        for (JFXButton b : customColorButtons) {
            b.setRipplerFill(Color.web(gui.text));
        }
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
        for (int i = 0; i < sliderPanes.length; i++) {
            sliderLabels[i].setTextFill(Color.web(gui.text));
            sliderValues[i].setTextFill(Color.web(gui.text));
            applyColorsToSlider(sliders[i]);
        }
        applyToA.setStyle("-fx-background-color:" + gui.bg4);
        applyToA.setTextFill(Color.web(gui.text));
        applyToB.setStyle("-fx-background-color:" + gui.bg4);
        applyToB.setTextFill(Color.web(gui.text));
        applyToBoth.setStyle("-fx-background-color:" + gui.bg4);
        applyToBoth.setTextFill(Color.web(gui.text));
    }

    public void applyColorsToSlider(JFXSlider slider) {
        StackPane sliderTrack = (StackPane) slider.lookup(".track");
        if (sliderTrack != null) {
            sliderTrack.setStyle("-fx-background-color:" + gui.bg4);
        }
        StackPane sliderColorTrack = (StackPane) slider.lookup(".colored-track");
        if (sliderColorTrack != null) {
            sliderColorTrack.setStyle("-fx-background-color:" + gui.ac2);
        }
        StackPane sliderThumb = (StackPane) slider.lookup(".thumb");
        if (sliderThumb != null) {
            sliderThumb.setStyle("-fx-background-color:" + gui.ac1);
        }
        StackPane sliderAnimatedThumb = (StackPane) slider.lookup(".animated-thumb");
        if (sliderAnimatedThumb != null) {
            sliderAnimatedThumb.setStyle("-fx-background-color:" + gui.ac2);
        }
    }

    public void setAccentColor(ActionEvent event) {
        for (int i = 0; i < defaultColorButtons.length; i++) {
            if (event.getSource() == defaultColorButtons[i]) {
                highlightColorButton(i, false);
                gui.setAccentColor(i);
                gui.updateColors();
            }
        }
    }

    public void setCustomAccentColor(ActionEvent event) {
        for (int i = 0; i < customColorButtons.length; i++) {
            if (event.getSource() == customColorButtons[i]) {
                highlightColorButton(i, true);
                gui.setCustomAccentColor(i);
                gui.updateColors();
            }
        }
    }

    public void highlightColorButton(int index, boolean custom) {
        if (gui.customColor) {
            customColorButtons[gui.colorIndex].getStyleClass().removeIf(s -> (s == "customPane"));
            customColorButtons[gui.colorIndex].setBorder(Border.EMPTY);
        } else {
            defaultColorButtons[gui.colorIndex].getStyleClass().removeIf(s -> (s == "customPane"));
            defaultColorButtons[gui.colorIndex].setBorder(Border.EMPTY);
        }
        if (custom) {
            customColorButtons[index].getStyleClass().add("customPane");
            customColorButtons[index].setBorder(
                    new Border(
                            new BorderStroke(
                                    Color.web(gui.bg4),
                                    BorderStrokeStyle.SOLID,
                                    new CornerRadii(3),
                                    new BorderWidths(4)
                            )
                    )
            );
        } else {
            defaultColorButtons[index].getStyleClass().add("customPane");
            defaultColorButtons[index].setBorder(
                    new Border(
                            new BorderStroke(
                                    Color.web(gui.bg4),
                                    BorderStrokeStyle.SOLID,
                                    new CornerRadii(3),
                                    new BorderWidths(4)
                            )
                    )
            );
        }
    }

    public void focusColorButton(JFXButton b) {
        if (b.isFocused()) {
            b.setBorder(
                    new Border(
                            new BorderStroke(
                                    Color.web(gui.text),
                                    BorderStrokeStyle.SOLID,
                                    new CornerRadii(3),
                                    new BorderWidths(4)
                            )
                    )
            );
        } else {
            b.setBorder(Border.EMPTY);
            highlightColorButton(gui.colorIndex, gui.customColor);
        }
    }

    public void focusSlider(JFXSlider s, Label l) {
        if (s.isFocused()) {
            l.setStyle("-fx-background-color:" + GUI.ac2);
        } else {
            l.setStyle("-fx-background-color:" + GUI.transparent);
        }
    }

    public void updateTimeComponentsText() {
        startTime.setText(gui.tm.getCurrentTablePair().getStartTime().format());
        int sp = gui.tm.getCurrentTablePair().getSmallPause();
        sliders[0].setValue(sp);
        sliderValues[0].setText(String.format("%02d", sp));
        int mp = gui.tm.getCurrentTablePair().getMiddlePause();
        sliders[1].setValue(mp);
        sliderValues[1].setText(String.format("%02d", mp));
        int bp = gui.tm.getCurrentTablePair().getBigPause();
        sliders[2].setValue(bp);
        sliderValues[2].setText(String.format("%02d", bp));
        int ll = gui.tm.getCurrentTablePair().getLessonLength();
        sliders[3].setValue(ll);
        sliderValues[3].setText(String.format("%02d", ll));
    }

    public AnchorPane getPane() {
        return pane;
    }

    public JFXToggleButton getColorMode() {
        return colorMode;
    }

    public boolean isHidden() {
        return hidden;
    }

}
