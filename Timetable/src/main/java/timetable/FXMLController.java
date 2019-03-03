package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class FXMLController implements Initializable {

    final static int animationDuration = 200;
    final static int animationDistance = 50;
    final static double animationFocusOffsetMultiplier = 0.6;
    final static String[] englishDayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    static String primaryColor = "-fx-color: #66DD7744";
    static String selectedColor = "-fx-background-color: #66DD7744";
    static String unselectedColor = "-fx-background-color: #00000000";
    static String[] dayNames = englishDayNames;

    TimetableManager tm;

    JFXButton[] days;
    JFXButton[] times;
    JFXButton[][] subjects;
    JFXButton selectedDay;
    JFXButton selectedTime;
    JFXButton selectedSubject;
    Label selectedAutocompleteOption;

    List<SomePane> menus = new ArrayList<SomePane>();

    AdvancedOptionsPane dayContextMenu;
    GridPane[] dayPanes;
    JFXToggleButton[] dayToggles;
    Label[] dayLabels;
    EventHandler<Event> dayToggled;
    EventHandler<Event> dayContextMenuOnShow;
    EventHandler<Event> dayContextMenuOnHide;

    OptionsPane timeContextMenu;
    JFXButton clearRow;
    JFXButton deleteRow;
    JFXButton addRowAbove;
    JFXButton addRowBelow;

    OptionsPane subjectContextMenu;
    JFXButton clear;
    JFXButton delete;
    JFXButton addAbove;
    JFXButton addBelow;

    AdvancedOptionsPane subjectMenu;
    JFXTextField subjectName;
    JFXTextField subjectRoom;
    JFXTextField subjectTeacher;
    EventHandler<KeyEvent> writeData;
    EventHandler<Event> subjectMenuOnShow;
    EventHandler<Event> subjectMenuOnHide;

    AutocompletePane autoCompletePane;
    Label selectedAutoCompleteOption;
    EventHandler<KeyEvent> subjectMenuKeyPressed;
    EventHandler<MouseEvent> autocompleteOnClick;
    EventHandler<KeyEvent> confirmInput;

    EventHandler<KeyEvent> hideAllMenus;

    TranslateTransition menuPaneSlideIn;
    FadeTransition menuBackgroundPaneFadeIn;
    TranslateTransition menuPaneSlideOut;
    FadeTransition menuBackgroundPaneFadeOut;
    ParallelTransition showMenuPane;
    ParallelTransition hideMenuPane;

    FadeTransition menuIconFadeIn;
    TranslateTransition menuIconSlideIn;
    FadeTransition menuIconFadeOut;
    TranslateTransition menuIconSlideOut;
    FadeTransition nameLabelFadeIn;
    FadeTransition nameLabelFadeOut;
    ParallelTransition showMenuIcon;
    ParallelTransition hideMenuIcon;

    boolean menuPaneHidden = true;
    boolean dayOverlayHidden = true;

    @FXML
    private GridPane subjectGrid;
    @FXML
    private JFXButton name;
    @FXML
    private JFXButton subject0000;
    @FXML
    private JFXButton subject0100;
    @FXML
    private JFXButton subject0200;
    @FXML
    private JFXButton subject0300;
    @FXML
    private JFXButton subject0400;
    @FXML
    private JFXButton subject0500;
    @FXML
    private JFXButton subject0600;
    @FXML
    private JFXButton subject0001;
    @FXML
    private JFXButton subject0101;
    @FXML
    private JFXButton subject0201;
    @FXML
    private JFXButton subject0301;
    @FXML
    private JFXButton subject0401;
    @FXML
    private JFXButton subject0501;
    @FXML
    private JFXButton subject0601;
    @FXML
    private JFXButton subject0002;
    @FXML
    private JFXButton subject0102;
    @FXML
    private JFXButton subject0202;
    @FXML
    private JFXButton subject0302;
    @FXML
    private JFXButton subject0402;
    @FXML
    private JFXButton subject0502;
    @FXML
    private JFXButton subject0602;
    @FXML
    private JFXButton subject0003;
    @FXML
    private JFXButton subject0103;
    @FXML
    private JFXButton subject0203;
    @FXML
    private JFXButton subject0303;
    @FXML
    private JFXButton subject0403;
    @FXML
    private JFXButton subject0503;
    @FXML
    private JFXButton subject0603;
    @FXML
    private JFXButton subject0004;
    @FXML
    private JFXButton subject0104;
    @FXML
    private JFXButton subject0204;
    @FXML
    private JFXButton subject0304;
    @FXML
    private JFXButton subject0404;
    @FXML
    private JFXButton subject0504;
    @FXML
    private JFXButton subject0604;
    @FXML
    private JFXButton subject0005;
    @FXML
    private JFXButton subject0105;
    @FXML
    private JFXButton subject0205;
    @FXML
    private JFXButton subject0305;
    @FXML
    private JFXButton subject0405;
    @FXML
    private JFXButton subject0505;
    @FXML
    private JFXButton subject0605;
    @FXML
    private JFXButton subject0006;
    @FXML
    private JFXButton subject0106;
    @FXML
    private JFXButton subject0206;
    @FXML
    private JFXButton subject0306;
    @FXML
    private JFXButton subject0406;
    @FXML
    private JFXButton subject0506;
    @FXML
    private JFXButton subject0606;
    @FXML
    private JFXButton subject0007;
    @FXML
    private JFXButton subject0107;
    @FXML
    private JFXButton subject0207;
    @FXML
    private JFXButton subject0307;
    @FXML
    private JFXButton subject0407;
    @FXML
    private JFXButton subject0507;
    @FXML
    private JFXButton subject0607;
    @FXML
    private JFXButton subject0008;
    @FXML
    private JFXButton subject0108;
    @FXML
    private JFXButton subject0208;
    @FXML
    private JFXButton subject0308;
    @FXML
    private JFXButton subject0408;
    @FXML
    private JFXButton subject0508;
    @FXML
    private JFXButton subject0608;
    @FXML
    private JFXButton subject0009;
    @FXML
    private JFXButton subject0109;
    @FXML
    private JFXButton subject0209;
    @FXML
    private JFXButton subject0309;
    @FXML
    private JFXButton subject0409;
    @FXML
    private JFXButton subject0509;
    @FXML
    private JFXButton subject0609;
    @FXML
    private AnchorPane bg;
    @FXML
    private JFXButton day00;
    @FXML
    private JFXButton day01;
    @FXML
    private JFXButton day02;
    @FXML
    private JFXButton day03;
    @FXML
    private JFXButton day04;
    @FXML
    private JFXButton day05;
    @FXML
    private JFXButton day06;
    @FXML
    private JFXButton time00;
    @FXML
    private JFXButton time01;
    @FXML
    private JFXButton time02;
    @FXML
    private JFXButton time03;
    @FXML
    private JFXButton time04;
    @FXML
    private JFXButton time05;
    @FXML
    private JFXButton time06;
    @FXML
    private JFXButton time07;
    @FXML
    private JFXButton time08;
    @FXML
    private JFXButton time09;
    @FXML
    private ImageView menuIcon;
    @FXML
    private Label nameLabel;
    @FXML
    private Label nameBackground;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private AnchorPane menuBackgroundPane;
    @FXML
    private JFXTextField menuPaneName;
    @FXML
    private GridPane menuPaneGrid;
    @FXML
    private JFXButton menuPaneNew;
    @FXML
    private GridPane menuPaneTables;
    @FXML
    private JFXButton menuPaneDelete;
    @FXML
    private ScrollPane menuPaneScrollPane;
    @FXML
    private JFXButton menuPaneInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initControlArrays();

        tm = new TimetableManager();

        hideAllMenus = (KeyEvent n) -> {
            if (n.getCode() == KeyCode.ESCAPE) {
                hideAllMenus();
            }
        };

        //day context menu
        dayContextMenu = new AdvancedOptionsPane(bg);
        menus.add(dayContextMenu);
        dayPanes = new GridPane[7];
        dayToggles = new JFXToggleButton[dayPanes.length];
        dayLabels = new Label[dayPanes.length];
        dayToggled = (Event e) -> {
            dayToggled();
        };
        for (int i = 0; i < dayPanes.length; i++) {
            dayPanes[i] = new GridPane();
            dayToggles[i] = new JFXToggleButton();
            dayToggles[i].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            dayToggles[i].getStyleClass().add("customToggleButton");
            dayToggles[i].setAlignment(Pos.CENTER);
            dayToggles[i].addEventHandler(EventType.ROOT, dayToggled);
            dayToggles[i].addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
            dayLabels[i] = new Label(dayNames[i]);

            Pane spacer = new Pane();
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            dayPanes[i].addColumn(0, dayLabels[i]);
            dayPanes[i].addColumn(1, spacer);
            dayPanes[i].addColumn(2, dayToggles[i]);
            dayPanes[i].getColumnConstraints().addAll(new ColumnConstraints(), cc);
            dayContextMenu.add(dayPanes[i]);
        }
        dayContextMenuOnShow = (Event e) -> {
            scaleDayContextMenu();
        };
        dayContextMenuOnHide = (Event e) -> {
            writeDayData();
        };
        dayContextMenu.setSpecificFocus(dayToggles[0]);
        dayContextMenu.setRequestSpecificFocus(true);
        dayContextMenu.setOnShow(dayContextMenuOnShow);
        dayContextMenu.setOnHide(dayContextMenuOnHide);

        //time context menu
        timeContextMenu = new OptionsPane(bg);
        menus.add(timeContextMenu);
        clearRow = new JFXButton("clear row");
        clearRow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        clearRow.addEventHandler(ActionEvent.ACTION, n -> {
            clearRow();
        });
        deleteRow = new JFXButton("delete row");
        deleteRow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        deleteRow.addEventHandler(ActionEvent.ACTION, n -> {
            deleteRow();
        });
        addRowAbove = new JFXButton("add row above");
        addRowAbove.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        addRowAbove.addEventHandler(ActionEvent.ACTION, n -> {
            addRowAbove();
        });
        addRowBelow = new JFXButton("add row below");
        addRowBelow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        addRowBelow.addEventHandler(ActionEvent.ACTION, n -> {
            addRowBelow();
        });
        timeContextMenu.addButton(clearRow);
        timeContextMenu.addButton(deleteRow);
        timeContextMenu.addButton(addRowAbove);
        timeContextMenu.addButton(addRowBelow);

        //subject context menu
        subjectContextMenu = new OptionsPane(bg);
        menus.add(subjectContextMenu);
        clear = new JFXButton("clear");
        clear.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        clear.addEventHandler(ActionEvent.ACTION, n -> {
            clear();
        });
        delete = new JFXButton("delete");
        delete.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        delete.addEventHandler(ActionEvent.ACTION, n -> {
            delete();
        });
        addAbove = new JFXButton("add above");
        addAbove.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        addAbove.addEventHandler(ActionEvent.ACTION, n -> {
            addAbove();
        });
        addBelow = new JFXButton("add below");
        addBelow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        addBelow.addEventHandler(ActionEvent.ACTION, n -> {
            addBelow();
        });
        subjectContextMenu.addButton(clear);
        subjectContextMenu.addButton(delete);
        subjectContextMenu.addButton(addAbove);
        subjectContextMenu.addButton(addBelow);

        //subject menu
        subjectMenu = new AdvancedOptionsPane(bg);
        menus.add(subjectMenu);
        writeData = (KeyEvent n) -> {
            if (n.getCode() == KeyCode.ENTER) {
                hideAllMenus();
            }
        };
        subjectName = new JFXTextField();
        subjectName.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        subjectName.setPrefWidth(500);
        subjectName.setPrefHeight(100);
        subjectName.setPromptText("name");
        subjectName.getStyleClass().add("customTextfield");
        subjectRoom = new JFXTextField();
        subjectRoom.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        subjectRoom.addEventHandler(KeyEvent.KEY_RELEASED, writeData);
        subjectRoom.setPrefWidth(500);
        subjectRoom.setPrefHeight(100);
        subjectRoom.getStyleClass().add("customTextfield");
        subjectRoom.setPromptText("room");
        subjectTeacher = new JFXTextField();
        subjectTeacher.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        subjectTeacher.addEventHandler(KeyEvent.KEY_RELEASED, writeData);
        subjectTeacher.setPrefWidth(500);
        subjectTeacher.setPrefHeight(100);
        subjectTeacher.getStyleClass().add("customTextfield");
        subjectTeacher.setPromptText("teacher");
        //EventHandler
        subjectMenuOnShow = (Event n) -> {
            scaleSubjectMenu();
        };
        subjectMenuOnHide = (Event n) -> {
            writeSubjectData();
            autoCompletePane.hide();
        };
        subjectMenu.add(subjectName);
        subjectMenu.add(subjectRoom);
        subjectMenu.add(subjectTeacher);
        subjectMenu.setOnShow(subjectMenuOnShow);
        subjectMenu.setOnHide(subjectMenuOnHide);

        //autocomplete pane
        autoCompletePane = new AutocompletePane(bg);
        menus.add(autoCompletePane);
        subjectMenuKeyPressed = (KeyEvent n) -> {
            autocompleteSubject(n);
        };
        autocompleteOnClick = (MouseEvent n) -> {
            autocompleteLabelClicked(n);
        };
        confirmInput = (KeyEvent n) -> {
            if (n.getCode() == KeyCode.ENTER) {
                subjectMenu.hide();
            }
        };
        subjectName.addEventHandler(KeyEvent.KEY_RELEASED, subjectMenuKeyPressed);
        autoCompletePane.setOnClick(autocompleteOnClick);

        initNewTimetable();

        //menu pane transitions
        menuPaneSlideIn = new TranslateTransition(Duration.millis(animationDuration), menuPane);
        menuPaneSlideIn.setToX(0);

        menuBackgroundPaneFadeIn = new FadeTransition(Duration.millis(animationDuration), menuBackgroundPane);
        menuBackgroundPaneFadeIn.setToValue(1);

        menuPaneSlideOut = new TranslateTransition(Duration.millis(animationDuration), menuPane);
        menuPaneSlideOut.setToX(-200);

        menuBackgroundPaneFadeOut = new FadeTransition(Duration.millis(animationDuration), menuBackgroundPane);
        menuBackgroundPaneFadeOut.setToValue(0);

        showMenuPane = new ParallelTransition();
        showMenuPane.getChildren().add(menuPaneSlideIn);
        showMenuPane.getChildren().add(menuBackgroundPaneFadeIn);

        hideMenuPane = new ParallelTransition();
        hideMenuPane.getChildren().add(menuPaneSlideOut);
        hideMenuPane.getChildren().add(menuBackgroundPaneFadeOut);

        //menu icon transitions
        menuIconSlideIn = new TranslateTransition(Duration.millis(animationDuration), menuIcon);
        menuIconSlideIn.setToX(0);

        menuIconFadeIn = new FadeTransition(Duration.millis(animationDuration), menuIcon);
        menuIconFadeIn.setToValue(1);

        menuIconSlideOut = new TranslateTransition(Duration.millis(animationDuration), menuIcon);
        menuIconSlideOut.setToX(-animationDistance);

        menuIconFadeOut = new FadeTransition(Duration.millis(animationDuration), menuIcon);
        menuIconFadeOut.setToValue(0);

        nameLabelFadeIn = new FadeTransition(Duration.millis(animationDuration), nameLabel);
        nameLabelFadeIn.setToValue(1);

        nameLabelFadeOut = new FadeTransition(Duration.millis(animationDuration), nameLabel);
        nameLabelFadeOut.setToValue(0);

        showMenuIcon = new ParallelTransition();
        showMenuIcon.getChildren().add(menuIconFadeIn);
        showMenuIcon.getChildren().add(menuIconSlideIn);
        showMenuIcon.getChildren().add(nameLabelFadeOut);

        hideMenuIcon = new ParallelTransition();
        hideMenuIcon.getChildren().add(menuIconFadeOut);
        hideMenuIcon.getChildren().add(menuIconSlideOut);
        hideMenuIcon.getChildren().add(nameLabelFadeIn);

        nameLabel.toBack();
        nameBackground.toBack();
        menuIcon.toFront();

        menuIcon.setTranslateX(-animationDistance);

        menuPaneScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        menuPaneNew.setRipplerFill(Color.web("#66DD77"));
        menuPaneDelete.setRipplerFill(Color.web("#66DD77"));

        name.setRipplerFill(Color.web("#888888"));
        for (int i = 0; i < days.length; i++) {
            days[i].setRipplerFill(Color.web("000000"));
        }
        for (int i = 0; i < times.length; i++) {
            times[i].setRipplerFill(Color.web("#000000"));
        }
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                subjects[i][j].setRipplerFill(Color.web("#66DD77"));
            }
        }

        bg.widthProperty().addListener(n -> {
            cancelMenus();
            cancelOverlays();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(1), e -> resizeFonts()),
                    new KeyFrame(Duration.millis(50), e -> menuPane.setTranslateX(-menuPane.getWidth()))
            );
            timeline.play();
        });
        bg.heightProperty().addListener(n -> {
            cancelOverlays();
            cancelMenus();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(1), e -> resizeFonts()),
                    new KeyFrame(Duration.millis(50), e -> menuPane.setTranslateX(-menuPane.getWidth()))
            );
            timeline.play();
        });
        name.focusedProperty().addListener(n -> {
            if (name.isFocused()) {
                showMenuIcon.play();
            } else {
                hideMenuIcon.play();
            }
        });
        subjectName.focusedProperty().addListener(n -> {
            if (!subjectName.isFocused()) {
                autoCompletePane.hide();
            }
        });

        nameLabel.textProperty().bind(menuPaneName.textProperty());

        menuIcon.fitHeightProperty().bind(nameBackground.heightProperty());
        menuIcon.fitWidthProperty().bind(nameBackground.heightProperty());
        name.prefHeightProperty().bind(nameBackground.heightProperty());
        name.prefWidthProperty().bind(nameBackground.widthProperty());

        menuPane.prefWidthProperty().bind(nameBackground.widthProperty().multiply(1.2));

        Timeline unfocus = new Timeline(
                new KeyFrame(Duration.millis(1), e -> bg.requestFocus())
        );

        unfocus.play();
    }

    public void initControlArrays() {
        days = new JFXButton[7];
        days[0] = day00;
        days[1] = day01;
        days[2] = day02;
        days[3] = day03;
        days[4] = day04;
        days[5] = day05;
        days[6] = day06;
        times = new JFXButton[10];
        times[0] = time00;
        times[1] = time01;
        times[2] = time02;
        times[3] = time03;
        times[4] = time04;
        times[5] = time05;
        times[6] = time06;
        times[7] = time07;
        times[8] = time08;
        times[9] = time09;
        subjects = new JFXButton[7][10];
        subjects[0][0] = subject0000;
        subjects[0][1] = subject0001;
        subjects[0][2] = subject0002;
        subjects[0][3] = subject0003;
        subjects[0][4] = subject0004;
        subjects[0][5] = subject0005;
        subjects[0][6] = subject0006;
        subjects[0][7] = subject0007;
        subjects[0][8] = subject0008;
        subjects[0][9] = subject0009;
        subjects[1][0] = subject0100;
        subjects[1][1] = subject0101;
        subjects[1][2] = subject0102;
        subjects[1][3] = subject0103;
        subjects[1][4] = subject0104;
        subjects[1][5] = subject0105;
        subjects[1][6] = subject0106;
        subjects[1][7] = subject0107;
        subjects[1][8] = subject0108;
        subjects[1][9] = subject0109;
        subjects[2][0] = subject0200;
        subjects[2][1] = subject0201;
        subjects[2][2] = subject0202;
        subjects[2][3] = subject0203;
        subjects[2][4] = subject0204;
        subjects[2][5] = subject0205;
        subjects[2][6] = subject0206;
        subjects[2][7] = subject0207;
        subjects[2][8] = subject0208;
        subjects[2][9] = subject0209;
        subjects[3][0] = subject0300;
        subjects[3][1] = subject0301;
        subjects[3][2] = subject0302;
        subjects[3][3] = subject0303;
        subjects[3][4] = subject0304;
        subjects[3][5] = subject0305;
        subjects[3][6] = subject0306;
        subjects[3][7] = subject0307;
        subjects[3][8] = subject0308;
        subjects[3][9] = subject0309;
        subjects[4][0] = subject0400;
        subjects[4][1] = subject0401;
        subjects[4][2] = subject0402;
        subjects[4][3] = subject0403;
        subjects[4][4] = subject0404;
        subjects[4][5] = subject0405;
        subjects[4][6] = subject0406;
        subjects[4][7] = subject0407;
        subjects[4][8] = subject0408;
        subjects[4][9] = subject0409;
        subjects[5][0] = subject0500;
        subjects[5][1] = subject0501;
        subjects[5][2] = subject0502;
        subjects[5][3] = subject0503;
        subjects[5][4] = subject0504;
        subjects[5][5] = subject0505;
        subjects[5][6] = subject0506;
        subjects[5][7] = subject0507;
        subjects[5][8] = subject0508;
        subjects[5][9] = subject0509;
        subjects[6][0] = subject0600;
        subjects[6][1] = subject0601;
        subjects[6][2] = subject0602;
        subjects[6][3] = subject0603;
        subjects[6][4] = subject0604;
        subjects[6][5] = subject0605;
        subjects[6][6] = subject0606;
        subjects[6][7] = subject0607;
        subjects[6][8] = subject0608;
        subjects[6][9] = subject0609;
    }

    public void resizeFonts() {
        double scaleFactor1 = 0.09;
        double scaleFactorHeightDependent1 = 0.3;
        double scaleFactorHeightDependent2 = 0.2;

        nameLabel.setFont(new Font((name.getHeight() + name.getWidth()) * scaleFactor1));

        for (JFXButton b : days) {
            b.setFont(new Font((name.getHeight() + name.getWidth()) * scaleFactor1));
        }
        for (JFXButton b : times) {
            b.setFont(new Font((name.getHeight()) * scaleFactorHeightDependent1));
        }
        for (JFXButton[] ba : subjects) {
            for (JFXButton b : ba) {
                b.setFont(new Font((name.getHeight()) * scaleFactorHeightDependent2));
            }
        }
    }

    public void initNewTimetable() {
        //Timetable tm.getCurrentTable() = tm.getCurrentTable();

        subjectGrid.getChildren().removeIf(e -> (e.getClass() == JFXButton.class));
        subjectGrid.getColumnConstraints().clear();
        subjectGrid.getRowConstraints().clear();

        subjectGrid.add(name, 0, 0, 1, 1);
        menuPaneName.setText(tm.getCurrentTable().getName());

        int pos = 0;
        for (int i = 0; i < days.length; i++) {
            if (tm.getCurrentTable().isDayDisplayed(i)) {
                subjectGrid.add(days[i], pos + 1, 0, 1, 1);
                pos++;
            }
        }
        for (int i = 0; i < times.length; i++) {
            if (i < tm.getCurrentTable().getLessons()) {
                subjectGrid.add(times[i], 0, i + 1, 1, 1);
                times[i].setText(tm.getCurrentTable().getTimeText(i));
            }
        }
        pos = 0;
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (j < tm.getCurrentTable().getLessons() && tm.getCurrentTable().isDayDisplayed(i)) {
                    subjectGrid.add(subjects[i][j], pos + 1, j + 1, 1, 1);
                    subjects[i][j].setText(
                            tm.getCurrentTable().getSubjectText(i, j)
                            + "\n" + tm.getCurrentTable().getRoomText(i, j)
                    );
                }
            }
            if (tm.getCurrentTable().isDayDisplayed(i)) {
                pos++;
            }
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1), e -> resizeFonts())
        );
        timeline.play();
    }

    public void cancelMenus() {
        for (SomePane s : menus) {
            s.cancel();
        }
    }

    public void cancelOverlays() {
        menuPane.setTranslateX(-menuPane.getWidth());
        menuPane.setVisible(false);
        menuBackgroundPane.setVisible(false);
    }

    public void hideOtherMenus(SomePane sp) {
        for (SomePane s : menus) {
            if (!s.equals(sp)) {
                s.hide();
            }
        }
    }

    @FXML
    private void hideAllMenus(ActionEvent event) {
        hideAllMenus();
    }

    public void hideAllMenus() {
        for (SomePane s : menus) {
            s.hide();
        }
    }

    public void hideOtherOverlays(int num) {
        if (num != 0) {
            hideMenuPane();
        }
    }

    public void hideMenuPane() {
        if (menuPaneHidden == false) {
            menuPaneHidden = true;

            bg.requestFocus();

            menuPaneSlideOut.setToX(-menuPane.getWidth());

            hideMenuPane.play();

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(animationDuration),
                            e -> menuPane.setVisible(false)),
                    new KeyFrame(Duration.millis(animationDuration),
                            e -> menuBackgroundPane.setVisible(false))
            );
            timeline.play();
        }
    }

    @FXML
    private void showMenuIcon(MouseEvent event) {
        showMenuIcon.play();
    }

    @FXML
    private void menu(ActionEvent event) {
        menuPaneHidden = false;

        hideOtherOverlays(0);

        double h = menuPaneNew.getHeight();
        double w = menuPaneNew.getWidth();
        int tablecount = menuPaneTables.getChildren().size();

        menuPaneGrid.setMargin(menuPaneName, new Insets(h * 0.65, h * 0.4, h * 0.1, h * 0.4));
        menuPaneTables.setPrefHeight(h * tablecount * 0.6);
        menuPaneTables.setPrefWidth(w);
        for (Node b : menuPaneTables.getChildren()) {
            JFXButton button = (JFXButton) b;
            button.setPrefHeight(h * 0.6);
        }

        menuPaneName.setFont(new Font((name.getHeight() + name.getWidth()) * 0.09));
        menuPaneNew.setFont(new Font((name.getHeight() + name.getWidth()) * 0.09));
        menuPaneDelete.setFont(new Font((name.getHeight() + name.getWidth()) * 0.09));
        for (Node b : menuPaneTables.getChildren()) {
            JFXButton button = (JFXButton) b;
            button.setFont(new Font((name.getHeight() + name.getWidth()) * 0.08));
        }

        menuPaneName.setText(tm.getCurrentTable().getName());

        menuPane.setVisible(true);
        menuBackgroundPane.setVisible(true);
        showMenuPane.play();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(animationDuration * animationFocusOffsetMultiplier), e -> menuPaneName.requestFocus())
        );
        timeline.play();
    }

    private void callCancelOverlaysA(ActionEvent event) {
        hideOtherOverlays(-1);
    }

    @FXML
    private void hideMenuIcon(MouseEvent event) {
        hideMenuIcon.play();
    }

    @FXML
    private void callHideMenuPane(MouseEvent event) {
        hideMenuPane();
    }

    @FXML
    private void updateCurrentTableName(KeyEvent event) {
        tm.getCurrentTable().setName(menuPaneName.getText());
    }

    @FXML
    private void menuPane_kp(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            hideMenuPane();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            hideMenuPane();
        }
        JFXButton b = (JFXButton) menuPaneTables.getChildren().get(tm.getTimeTableIndex());
        b.setText(tm.getCurrentTable().getName());
    }

    @FXML
    private void addNewTimetable(ActionEvent event) {
        addNewTimetable();
    }

    public void addNewTimetable() {
        Timetable t = new Timetable("Timetable" + tm.tableCount);
        tm.getTimetables().add(t);
        addTimetableToMenu(t);
        setCurrentTable(t);
        tm.setTimeTableIndex(tm.getTimetables().size() - 1);
        highlightTimetable();
        tm.tableCount++;
    }

    public void addTimetableToMenu(Timetable timetable) {
        int size = tm.getTimetables().size();

        double h = menuPaneNew.getHeight();

        JFXButton tableButton = new JFXButton(timetable.getName());
        //tableButton.getStylesheets().add("subjectButton");
        tableButton.setRipplerFill(Color.web(("#66DD77")));
        tableButton.setPrefHeight(h * 0.6);
        tableButton.setPrefWidth(600);
        tableButton.setFont(new Font((name.getHeight() + name.getWidth()) * 0.08));
        //Actionhandler for changing the currently displayed Timetable
        tableButton.setOnAction(n -> {
            for (int i = 0; i < menuPaneTables.getChildren().size(); i++) {
                if (menuPaneTables.getChildren().get(i) == n.getSource()) {
                    tm.setTimeTableIndex(i);
                    setCurrentTable(tm.getTimetables().get(i));
                }
            }
            highlightTimetable();
        });

        int vGridPos = menuPaneTables.getChildren().size();

        menuPaneTables.setPrefHeight(h * size * 0.6);

        menuPaneTables.add(tableButton, 0, vGridPos, 1, 1);

        System.out.println("tm.getTimetables().size() : " + size);

        System.out.println("menuPaneNew.getHeight() : " + h);

        System.out.println("menuPaneTables.getChildren().size() : " + vGridPos);

        System.out.println("button height : " + h * 0.6);

        System.out.println("menuPaneTables.getPrefHeight() : " + menuPaneTables.getPrefHeight());

        System.out.println("--------------------------------------------------------------");
    }

    private void setCurrentTable(Timetable t) {
        tm.currentTable = t;
        initNewTimetable();
    }

    @FXML
    private void deleteTimetable(ActionEvent event) {

        System.out.println("###########################################################");

        tm.getTimetables().remove(tm.getTimeTableIndex());
        menuPaneTables.getChildren().remove(tm.getTimeTableIndex());
        if (tm.getTimetables().size() == 0) {
            addNewTimetable();
        } else if (tm.getTimeTableIndex() < tm.getTimetables().size()) {
            tm.setCurrentTable(tm.getTimeTableIndex());
        } else {
            tm.setTimeTableIndex(tm.getTimeTableIndex() - 1);
            tm.setCurrentTable(tm.getTimeTableIndex());
        }

        initNewTimetable();

        menuPaneTables.getChildren().removeIf(e -> (e.getClass() == JFXButton.class));

        for (Timetable t : tm.getTimetables()) {
            addTimetableToMenu(t);
        }

        highlightTimetable();
    }

    public void highlightTimetable() {
        for (Node b : menuPaneTables.getChildren()) {
            JFXButton button = (JFXButton) b;
            button.setStyle(unselectedColor);
        }
        JFXButton highlightedButton = (JFXButton) menuPaneTables.getChildren().get(tm.getTimeTableIndex());
        highlightedButton.setStyle(selectedColor);
    }

    @FXML
    private void showInfo(ActionEvent event) {
        //show info pane
    }

    @FXML
    private void dayContextMenu(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedDay(event);
            dayContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedDay);
            hideOtherMenus(dayContextMenu);
        }
    }

    public void scaleDayContextMenu() {
        double h = selectedDay.getHeight();
        for (int i = 0; i < 7; i++) {
            dayToggles[i].setSelected(tm.getCurrentTable().isDayDisplayed(i));
            dayToggles[i].setScaleX(h * 0.012);
            dayToggles[i].setScaleY(h * 0.012);
            dayToggles[i].setMinHeight(h * dayContextMenu.getHeightFactor());
            dayToggles[i].setPrefHeight(h * dayContextMenu.getHeightFactor());
            dayToggles[i].setMaxHeight(h * dayContextMenu.getHeightFactor());
            dayLabels[i].setFont(new Font(h * 0.22));
            dayPanes[i].setPadding(new Insets(0, h * 0.4, 0, h * 0.4));
        }
        dayContextMenu.getPane().setPadding(new Insets(h * 0.1, 0, 0, 0));
        dayContextMenu.getDone().setFont(new Font(h * 0.22));

    }

    public void writeDayData() {
        for (int i = 0; i < 7; i++) {
            tm.getCurrentTable().setDayDisplayed(dayToggles[i].isSelected(), i);
        }
        initNewTimetable();
    }

    public void dayToggled() {
        int activatedButtons = 0;
        int index = 0;
        for (int i = 0; i < dayToggles.length; i++) {
            if (dayToggles[i].isSelected()) {
                activatedButtons++;
                index = i;
            }
        }
        if (activatedButtons > 1) {
            for (JFXToggleButton tb : dayToggles) {
                tb.setDisable(false);
            }
        } else {
            dayToggles[index].setDisable(true);
        }
    }

    @FXML
    private void dayKeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            getSelectedDay(event);
            dayContextMenu.show(selectedDay);
            hideOtherMenus(dayContextMenu);
        }
    }

    private void getSelectedDay(Event e) {
        for (int i = 0; i < days.length; i++) {
            if (days[i] == e.getSource()) {
                selectedDay = days[i];
                break;
            }
        }
    }

    @FXML
    private void timeContextMenu(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedTime(event);
            timeContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedTime);
            hideOtherMenus(timeContextMenu);
        }
    }

    public void clearRow() {
        tm.clearRow();
        initNewTimetable();
    }

    public void deleteRow() {
        tm.deleteRow();
        initNewTimetable();
    }

    public void addRowAbove() {
        tm.addRowAbove();
        initNewTimetable();
    }

    public void addRowBelow() {
        tm.addRowBelow();
        initNewTimetable();
    }

    @FXML
    private void timeKeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            getSelectedTime(event);
            timeContextMenu.show(selectedTime);
            hideOtherMenus(timeContextMenu);

        }
    }

    private void getSelectedTime(Event e) {
        for (int i = 0; i < times.length; i++) {
            if (times[i] == e.getSource()) {
                selectedTime = times[i];
                tm.settIndexI(i);
                break;
            }
        }
    }

    @FXML
    private void subjectMenu(ActionEvent event) {
        getSelectedSubject(event);
        subjectMenu.show(selectedSubject);
        hideOtherMenus(subjectMenu);
    }

    public void scaleSubjectMenu() {
        double h = selectedSubject.getHeight();
        subjectMenu.getPane().setMargin(subjectName, new Insets(0, 0, h * 0.1, 0));
        subjectMenu.getPane().setMargin(subjectRoom, new Insets(0, 0, h * 0.1, 0));
        subjectMenu.getPane().setMargin(subjectTeacher, new Insets(0, 0, h * 0.1, 0));
        subjectName.setPadding(new Insets(h * 0.4, h * 0.4, 0, h * 0.4));
        subjectRoom.setPadding(new Insets(h * 0.4, h * 0.4, 0, h * 0.4));
        subjectTeacher.setPadding(new Insets(h * 0.4, h * 0.4, 0, h * 0.4));
        subjectMenu.getDone().setPadding(new Insets(h * 0.1));
        subjectName.setFont(new Font(h * 0.22));
        subjectRoom.setFont(new Font(h * 0.22));
        subjectTeacher.setFont(new Font(h * 0.22));
        subjectMenu.getDone().setFont(new Font(h * 0.22));
        subjectName.setText(tm.getCurrentTable().getSubjectText(tm.getsIndexI(), tm.getsIndexJ()));
        subjectRoom.setText(tm.getCurrentTable().getRoomText(tm.getsIndexI(), tm.getsIndexJ()));
        subjectTeacher.setText(tm.getCurrentTable().getTeacherText(tm.getsIndexI(), tm.getsIndexJ()));
    }

    public void writeSubjectData() {
        selectedSubject.setText(subjectName.getText() + "\n" + subjectRoom.getText());
        tm.getCurrentTable().setSubjectText(subjectName.getText(), tm.getsIndexI(), tm.getsIndexJ());
        tm.getCurrentTable().setRoomText(subjectRoom.getText(), tm.getsIndexI(), tm.getsIndexJ());
        tm.getCurrentTable().setTeacherText(subjectTeacher.getText(), tm.getsIndexI(), tm.getsIndexJ());
    }

    public void autocompleteSubject(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            enterPressed();
        } else if (e.getCode() == KeyCode.UP) {
            autoCompletePane.upPressed();
        } else if (e.getCode() == KeyCode.DOWN) {
            autoCompletePane.downPressed();
        } else if (e.getCode() == KeyCode.ESCAPE) {
            //just here so handleInput() doesn't get called ._.
        } else {
            handleInput();
        }
    }

    public void autocompleteLabelClicked(MouseEvent e) {
        Label l = (Label) e.getSource();
        for (int i = 0; i < tm.getCurrentTable().getOptions().size(); i++) {
            if (l == autoCompletePane.getPane().getChildren().get(i)) {
                writeAutocomplete(i);
            }
        }
    }

    private void enterPressed() {
        if (autoCompletePane.isAutocompleteFucused()) {
            writeAutocomplete();
        } else {
            subjectMenu.hide();
        }
    }

    private void writeAutocomplete() {
        setSubjectMenuFields(tm.getCurrentTable().getOption(autoCompletePane.getAutocompleteIndex()));
        autoCompletePane.hide();
    }

    private void writeAutocomplete(int index) {
        setSubjectMenuFields(tm.getCurrentTable().getOption(index));
        autoCompletePane.hide();
    }

    private void setSubjectMenuFields(Subject s) {
        subjectName.setText(s.getSubject());
        subjectRoom.setText(s.getRoom());
        subjectTeacher.setText(s.getTeacher());
    }

    private void handleInput() {
        if (subjectName.getText().length() > 0) {
            tm.getCurrentTable().updateReferences();
            List<Subject> options = tm.getCurrentTable().getAutocompleteOptions(subjectName.getText());
            if (options.size() > 0) {
                autoCompletePane.setFields(subjectName, options);
                autoCompletePane.show(subjectName);
            } else {
                autoCompletePane.hide();
            }
        }
    }

    @FXML
    private void subjectContextMenu(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedSubject(event);
            subjectContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedSubject);
            hideOtherMenus(subjectContextMenu);
        }
    }

    public void clear() {
        tm.clear();
        initNewTimetable();
    }

    public void delete() {
        tm.delete();
        initNewTimetable();
    }

    public void addAbove() {
        tm.delete();
        initNewTimetable();
    }

    public void addBelow() {
        tm.delete();
        initNewTimetable();
    }

    @FXML
    private void subjectKeyReleased(KeyEvent event) {
        getSelectedSubject(event);
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            subjectContextMenu.show(selectedSubject);
        } else if (event.isControlDown() && event.getCode() == KeyCode.UP) {
            moveSubjectUp(tm.getsIndexI(), tm.getsIndexJ());
        } else if (event.isControlDown() && event.getCode() == KeyCode.DOWN) {
            moveSubjectDown(tm.getsIndexI(), tm.getsIndexJ());
        } else if (event.isControlDown() && event.getCode() == KeyCode.LEFT) {
            moveSubjectLeft(tm.getsIndexI(), tm.getsIndexJ());
        } else if (event.isControlDown() && event.getCode() == KeyCode.RIGHT) {
            moveSubjectRight(tm.getsIndexI(), tm.getsIndexJ());
        } else if (event.getCode() == KeyCode.DELETE) {
            tm.getCurrentTable().clearSubject(tm.getsIndexI(), tm.getsIndexJ());
            initNewTimetable();
        }

    }

    public void moveSubjectUp(int i, int j) {
        tm.getCurrentTable().moveSubjectUp(tm.getsIndexI(), tm.getsIndexJ());
        initNewTimetable();
        if (j > 0) {
            subjects[i][j - 1].requestFocus();
        }
    }

    public void moveSubjectDown(int i, int j) {
        tm.getCurrentTable().moveSubjectDown(tm.getsIndexI(), tm.getsIndexJ());
        initNewTimetable();
        if (j < tm.getCurrentTable().getLessons()) {
            subjects[i][j + 1].requestFocus();
        }
    }

    public void moveSubjectLeft(int i, int j) {
        tm.getCurrentTable().moveSubjectLeft(tm.getsIndexI(), tm.getsIndexJ());
        initNewTimetable();
        if (i > 0) {
            subjects[i - 1][j].requestFocus();
        }
    }

    public void moveSubjectRight(int i, int j) {
        tm.getCurrentTable().moveSubjectRight(tm.getsIndexI(), tm.getsIndexJ());
        initNewTimetable();
        int days = 0;
        for (int d = 0; d < tm.getCurrentTable().days.length; d++) {
            if (tm.getCurrentTable().isDayDisplayed(d)) {
                days++;
            }
        }
        if (i < days) {
            subjects[i + 1][j].requestFocus();
        }
    }

    private void getSelectedSubject(Event e) {
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (e.getSource() == subjects[i][j]) {
                    selectedSubject = subjects[i][j];
                    tm.setsIndexI(i);
                    tm.setsIndexJ(j);
                    break;
                }
            }
        }
    }

}
