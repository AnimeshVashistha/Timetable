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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class GUI implements Initializable {

    final static int animationDuration = 200;
    final static int animationDistance = 50;
    final static double focusAnimationOffsetFactor = 0.6;
    final static double fontFactor = 0.22;
    final static String[] englishDayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    static String primaryColor = "-fx-color: #66DD7744";
    static String selectedColor = "-fx-background-color: #66DD7744";
    static String unselectedColor = "-fx-background-color: #00000000";
    static String ripplerFill = "#66DD77";
    static String[] dayNames = englishDayNames;

    TimetableManager tm;

    JFXButton[] days;
    JFXButton[] times;
    JFXButton[][] subjects;
    JFXButton selectedDay;
    JFXButton selectedTime;
    JFXButton selectedSubject;

    EventHandler<KeyEvent> hideAllMenus;
    List<SomePane> menus;

    //menu
    EventHandler<Event> menuOnShow;
    EventHandler<Event> menuOnHide;
    EventHandler<KeyEvent> writeMenuData;
    EventHandler<ActionEvent> timetableButtonPressed;
    SidebarPane menu;
    JFXTextField menuName;
    JFXButton addTimetable;
    JFXButton deleteTimetable;
    ScrollPane menuScrollPane;
    TimetablePane timetablePane;

    //day context menu
    EventHandler<Event> dayContextMenuOnShow;
    EventHandler<Event> dayContextMenuOnHide;
    EventHandler<Event> dayToggled;
    AdvancedOptionsPane dayContextMenu;
    GridPane[] dayPanes;
    JFXToggleButton[] dayToggles;
    Label[] dayLabels;

    //time context menu
    OptionsPane timeContextMenu;
    JFXButton clearRow;
    JFXButton deleteRow;
    JFXButton addRowAbove;
    JFXButton addRowBelow;

    //subject menu
    EventHandler<Event> subjectMenuOnShow;
    EventHandler<Event> subjectMenuOnHide;
    EventHandler<KeyEvent> writeSubjectData;
    AdvancedOptionsPane subjectMenu;
    JFXTextField subjectName;
    JFXTextField subjectRoom;
    JFXTextField subjectTeacher;

    //autocomplete pane
    EventHandler<KeyEvent> subjectMenuKeyPressed;
    EventHandler<MouseEvent> autocompleteOnClick;
    AutocompletePane autoCompletePane;

    FadeTransition menuIconFadeIn;
    TranslateTransition menuIconSlideIn;
    FadeTransition menuIconFadeOut;
    TranslateTransition menuIconSlideOut;
    FadeTransition nameLabelFadeIn;
    FadeTransition nameLabelFadeOut;
    ParallelTransition showMenuIcon;
    ParallelTransition hideMenuIcon;

    //subject context menu
    OptionsPane subjectContextMenu;
    JFXButton clear;
    JFXButton delete;
    JFXButton addAbove;
    JFXButton addBelow;

    boolean menuPaneHidden = true;
    boolean dayOverlayHidden = true;

    @FXML
    private AnchorPane bg;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initControlArrays();

        tm = new TimetableManager();

        initNewTimetable();

        hideAllMenus = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                hideAllMenus();
            }
        };
        menus = new ArrayList<SomePane>();

        //menu
        menuOnShow = (Event event) -> {
            hideOtherMenus(menu);
            updateMenuData();
            updateMenuTimetables();
            scaleMenu();
        };
        menuOnHide = (Event event) -> {
            writeMenuData();
        };
        writeMenuData = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                menu.hide();
            }
        };
        timetableButtonPressed = (ActionEvent event) -> {
            timetableButtonPressed(event);
        };
        menu = new SidebarPane(bg);
        menus.add(menu);
        menu.setOnShow(menuOnShow);
        menu.setOnHide(menuOnHide);
        menuName = new JFXTextField();
        menuName.setPrefHeight(100);
        menuName.setPrefWidth(500);
        menuName.setPromptText("name");
        menuName.getStyleClass().add("customTextfield");
        menuName.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        menuName.addEventHandler(KeyEvent.KEY_RELEASED, writeMenuData);
        addTimetable = new JFXButton("add timetable");
        addTimetable.setPrefWidth(500);
        addTimetable.setPrefHeight(150);
        addTimetable.setRipplerFill(Color.web(ripplerFill));
        addTimetable.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        addTimetable.addEventHandler(ActionEvent.ACTION, event -> {
            addTimetable();
        });
        deleteTimetable = new JFXButton("delete timetable");
        deleteTimetable.setPrefWidth(500);
        deleteTimetable.setPrefHeight(150);
        deleteTimetable.setRipplerFill(Color.web(ripplerFill));
        deleteTimetable.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        deleteTimetable.addEventHandler(ActionEvent.ACTION, event -> {
            deleteTimetable();
        });
        menuScrollPane = new ScrollPane();
        menuScrollPane.setPrefHeight(500);
        menuScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        menuScrollPane.setStyle("-fx-focus-color: transparent;");
        menuScrollPane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaX() != 0) {
                    event.consume();
                }
            }
        });
        timetablePane = new TimetablePane();
        timetablePane.update(tm.getTimetables(), timetableButtonPressed, tm.getTimeTableIndex());
        menuScrollPane.setContent(timetablePane.getPane());
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        menu.getPane().getRowConstraints().addAll(new RowConstraints(), new RowConstraints(), new RowConstraints(), rc);
        menu.add(menuName);
        menu.add(addTimetable);
        menu.add(deleteTimetable);
        menu.add(menuScrollPane);

        //day context menu
        dayContextMenuOnShow = (Event event) -> {
            scaleDayContextMenu();
            updateDayContextMenuData();
        };
        dayContextMenuOnHide = (Event event) -> {
            writeDayData();
        };
        dayToggled = (Event event) -> {
            dayToggled();
        };
        dayContextMenu = new AdvancedOptionsPane(bg);
        menus.add(dayContextMenu);
        dayContextMenu.setWidthFactor(2.7);
        dayContextMenu.setOnShow(dayContextMenuOnShow);
        dayContextMenu.setOnHide(dayContextMenuOnHide);
        dayPanes = new GridPane[7];
        dayToggles = new JFXToggleButton[dayPanes.length];
        dayLabels = new Label[dayPanes.length];
        for (int i = 0; i < dayPanes.length; i++) {
            dayPanes[i] = new GridPane();
            dayToggles[i] = new JFXToggleButton();
            dayToggles[i].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            dayToggles[i].getStyleClass().add("customToggleButton");
            dayToggles[i].setAlignment(Pos.CENTER);
            dayToggles[i].addEventHandler(EventType.ROOT, dayToggled);
            dayToggles[i].addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
            dayLabels[i] = new Label(dayNames[i]);

            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            dayPanes[i].addColumn(0, dayLabels[i]);
            dayPanes[i].addColumn(2, dayToggles[i]);
            dayPanes[i].getColumnConstraints().add(cc);
            dayContextMenu.add(dayPanes[i]);
        }
        dayContextMenu.setSpecificFocus(dayToggles[0]);
        dayContextMenu.setRequestSpecificFocus(true);

        //time context menu
        timeContextMenu = new OptionsPane(bg);
        menus.add(timeContextMenu);
        clearRow = new JFXButton("clear row");
        clearRow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        clearRow.addEventHandler(ActionEvent.ACTION, event -> {
            clearRow();
        });
        deleteRow = new JFXButton("delete row");
        deleteRow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        deleteRow.addEventHandler(ActionEvent.ACTION, event -> {
            deleteRow();
        });
        addRowAbove = new JFXButton("add row above");
        addRowAbove.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        addRowAbove.addEventHandler(ActionEvent.ACTION, event -> {
            addRowAbove();
        });
        addRowBelow = new JFXButton("add row below");
        addRowBelow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        addRowBelow.addEventHandler(ActionEvent.ACTION, event -> {
            addRowBelow();
        });
        timeContextMenu.addButton(clearRow);
        timeContextMenu.addButton(deleteRow);
        timeContextMenu.addButton(addRowAbove);
        timeContextMenu.addButton(addRowBelow);

        //subject menu
        subjectMenuOnShow = (Event event) -> {
            scaleSubjectMenu();
            updateSubjectMenuData();
        };
        subjectMenuOnHide = (Event event) -> {
            writeSubjectData();
            autoCompletePane.hide();
        };
        writeSubjectData = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                subjectMenu.hide();
            }
        };
        subjectMenu = new AdvancedOptionsPane(bg);
        menus.add(subjectMenu);
        subjectMenu.setOnShow(subjectMenuOnShow);
        subjectMenu.setOnHide(subjectMenuOnHide);
        subjectName = new JFXTextField();
        subjectName.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        subjectName.setPrefWidth(500);
        subjectName.setPrefHeight(100);
        subjectName.setPromptText("name");
        subjectName.getStyleClass().add("customTextfield");
        subjectRoom = new JFXTextField();
        subjectRoom.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        subjectRoom.addEventHandler(KeyEvent.KEY_RELEASED, writeSubjectData);
        subjectRoom.setPrefWidth(500);
        subjectRoom.setPrefHeight(100);
        subjectRoom.getStyleClass().add("customTextfield");
        subjectRoom.setPromptText("room");
        subjectTeacher = new JFXTextField();
        subjectTeacher.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        subjectTeacher.addEventHandler(KeyEvent.KEY_RELEASED, writeSubjectData);
        subjectTeacher.setPrefWidth(500);
        subjectTeacher.setPrefHeight(100);
        subjectTeacher.getStyleClass().add("customTextfield");
        subjectTeacher.setPromptText("teacher");
        subjectMenu.add(subjectName);
        subjectMenu.add(subjectRoom);
        subjectMenu.add(subjectTeacher);

        //autocomplete pane
        subjectMenuKeyPressed = (KeyEvent event) -> {
            autocompleteSubject(event);
        };
        autocompleteOnClick = (MouseEvent event) -> {
            autocompleteLabelClicked(event);
        };
        autoCompletePane = new AutocompletePane(bg);
        subjectName.addEventHandler(KeyEvent.KEY_RELEASED, subjectMenuKeyPressed);
        autoCompletePane.setOnClick(autocompleteOnClick);

        //subject context menu
        subjectContextMenu = new OptionsPane(bg);
        menus.add(subjectContextMenu);
        clear = new JFXButton("clear");
        clear.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        clear.addEventHandler(ActionEvent.ACTION, event -> {
            clear();
        });
        delete = new JFXButton("delete");
        delete.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        delete.addEventHandler(ActionEvent.ACTION, event -> {
            delete();
        });
        addAbove = new JFXButton("add above");
        addAbove.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        addAbove.addEventHandler(ActionEvent.ACTION, event -> {
            addAbove();
        });
        addBelow = new JFXButton("add below");
        addBelow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        addBelow.addEventHandler(ActionEvent.ACTION, event -> {
            addBelow();
        });
        subjectContextMenu.addButton(clear);
        subjectContextMenu.addButton(delete);
        subjectContextMenu.addButton(addAbove);
        subjectContextMenu.addButton(addBelow);

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

        nameLabel.setText(tm.getCurrentTable().getName());
        nameLabel.toBack();
        nameBackground.toBack();
        menuIcon.toFront();

        menuIcon.setTranslateX(-animationDistance);

        name.setRipplerFill(Color.web("#888888"));
        for (int i = 0; i < days.length; i++) {
            days[i].setRipplerFill(Color.web("000000"));
        }
        for (int i = 0; i < times.length; i++) {
            times[i].setRipplerFill(Color.web("#000000"));
        }
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                subjects[i][j].setRipplerFill(Color.web(ripplerFill));
            }
        }

        bg.widthProperty().addListener(event -> {
            cancelMenus();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(1), n -> resizeFonts())
            );
            timeline.play();
        });
        bg.heightProperty().addListener(event -> {
            cancelMenus();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(1), n -> resizeFonts())
            );
            timeline.play();
        });
        name.focusedProperty().addListener(event -> {
            if (name.isFocused()) {
                showMenuIcon.play();
            } else {
                hideMenuIcon.play();
            }
        });
        subjectName.focusedProperty().addListener(event -> {
            if (!subjectName.isFocused()) {
                autoCompletePane.hide();
            }
        });

        menuIcon.fitHeightProperty().bind(nameBackground.heightProperty());
        menuIcon.fitWidthProperty().bind(nameBackground.heightProperty());
        name.prefHeightProperty().bind(nameBackground.heightProperty());
        name.prefWidthProperty().bind(nameBackground.widthProperty());

        Timeline unfocus = new Timeline(
                new KeyFrame(Duration.millis(1), event -> bg.requestFocus())
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
        subjectGrid.getChildren().removeIf(node -> (node.getClass() == JFXButton.class));
        subjectGrid.getColumnConstraints().clear();
        subjectGrid.getRowConstraints().clear();

        subjectGrid.add(name, 0, 0, 1, 1);

        //display days
        int pos = 0;
        for (int i = 0; i < days.length; i++) {
            if (tm.getCurrentTable().isDayDisplayed(i)) {
                subjectGrid.add(days[i], pos + 1, 0, 1, 1);
                pos++;
            }
        }

        //display times
        for (int i = 0; i < times.length; i++) {
            if (i < tm.getCurrentTable().getLessons()) {
                subjectGrid.add(times[i], 0, i + 1, 1, 1);
                times[i].setText(tm.getCurrentTable().getTimeText(i));
            }
        }

        //display subjects
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
                new KeyFrame(Duration.millis(1), event -> resizeFonts())
        );
        timeline.play();
    }

    public void cancelMenus() {
        for (SomePane s : menus) {
            s.cancel();
        }
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

    @FXML
    private void showMenuIcon(MouseEvent event) {
        showMenuIcon.play();
    }

    @FXML
    private void hideMenuIcon(MouseEvent event) {
        hideMenuIcon.play();
    }

    //
    //################################menu################################
    //
    @FXML
    private void menu(ActionEvent event) {
        hideOtherMenus(menu);
        menu.show(name);
    }

    public void scaleMenu() {
        double h = name.getPrefHeight();
        menu.getPane().setMargin(menuName, new Insets(0, 0, h * 0.1, 0));
        menuName.setPadding(new Insets(h * 0.4, h * 0.4, 0, h * 0.4));
        menuName.setFont(new Font(h * fontFactor));
        addTimetable.setFont(new Font(h * fontFactor));
        deleteTimetable.setFont(new Font(h * fontFactor));
        menu.getDone().setFont(new Font(h * fontFactor));
        timetablePane.scale(h, menu.getPane().getPrefWidth());
    }

    public void updateMenuData() {
        menuName.setText(tm.getCurrentTable().getName());
    }

    public void writeMenuData() {
        tm.getCurrentTable().setName(menuName.getText());
        nameLabel.setText(menuName.getText());
    }

    public void updateMenuTimetables() {
        timetablePane.update(tm.getTimetables(), timetableButtonPressed, tm.getTimeTableIndex());
    }

    public void timetableButtonPressed(ActionEvent event) {
        int size = timetablePane.getPane().getChildren().size();
        for (int i = 0; i < size; i++) {
            if (event.getSource().equals(timetablePane.getPane().getChildren().get(i))) {
                writeMenuData();
                tm.setCurrentTable(i);
                initNewTimetable();
                updateMenuData();
                updateMenuTimetables();
                scaleMenu();
            }
        }
    }

    public void addTimetable() {
        tm.addTimetable();
        updateMenuTimetables();
        scaleMenu();
        initNewTimetable();
        updateMenuData();
    }

    public void deleteTimetable() {
        tm.deleteCurrentTimetable();
        updateMenuTimetables();
        scaleMenu();
        initNewTimetable();
        updateMenuData();
    }

    //
    //################################dayContextMenu################################
    //
    @FXML
    private void dayContextMenu(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedDay(event);
            dayContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedDay);
            hideOtherMenus(dayContextMenu);
        }
    }

    @FXML
    private void dayKeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            getSelectedDay(event);
            dayContextMenu.show(selectedDay);
        }
    }

    public void scaleDayContextMenu() {
        double h = selectedDay.getHeight();
        for (int i = 0; i < dayToggles.length; i++) {
            dayToggles[i].setSelected(tm.getCurrentTable().isDayDisplayed(i));
            dayToggles[i].setScaleX(h * 0.012);
            dayToggles[i].setScaleY(h * 0.012);
            dayToggles[i].setMinHeight(h * dayContextMenu.getHeightFactor());
            dayToggles[i].setPrefHeight(h * dayContextMenu.getHeightFactor());
            dayToggles[i].setMaxHeight(h * dayContextMenu.getHeightFactor());
            dayLabels[i].setFont(new Font(h * fontFactor));
            dayPanes[i].setPadding(new Insets(0, h * 0.1, 0, h * 0.4));
        }
        dayContextMenu.getPane().setPadding(new Insets(h * 0.2, 0, 0, 0));
        dayContextMenu.getDone().setPadding(new Insets(h * 0.15, 0, h * 0.15, 0));
        dayContextMenu.getDone().setFont(new Font(h * fontFactor));
    }

    public void updateDayContextMenuData() {
        for (int i = 0; i < dayToggles.length; i++) {
            dayToggles[i].setSelected(tm.getCurrentTable().isDayDisplayed(i));
        }
    }

    public void writeDayData() {
        for (int i = 0; i < dayToggles.length; i++) {
            tm.getCurrentTable().setDayDisplayed(dayToggles[i].isSelected(), i);
        }
        initNewTimetable();
        Timeline t = new Timeline(
                new KeyFrame(Duration.millis(1), n -> resizeFonts())
        );
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

    private void getSelectedDay(Event event) {
        for (int i = 0; i < days.length; i++) {
            if (days[i] == event.getSource()) {
                selectedDay = days[i];
                break;
            }
        }
    }

    //
    //################################timeContextMenu################################
    //
    @FXML
    private void timeContextMenu(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedTime(event);
            timeContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedTime);
            hideOtherMenus(timeContextMenu);
        }
    }

    @FXML
    private void timeKeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            getSelectedTime(event);
            timeContextMenu.show(selectedTime);
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

    private void getSelectedTime(Event event) {
        for (int i = 0; i < times.length; i++) {
            if (times[i] == event.getSource()) {
                selectedTime = times[i];
                tm.settIndexI(i);
                break;
            }
        }
    }

    //
    //################################subjectMenu################################
    //
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
        subjectName.setFont(new Font(h * fontFactor));
        subjectRoom.setFont(new Font(h * fontFactor));
        subjectTeacher.setFont(new Font(h * fontFactor));
        subjectMenu.getDone().setFont(new Font(h * fontFactor));
    }

    public void updateSubjectMenuData() {
        subjectName.setText(tm.getCurrentTable().getSubjectText(tm.getsIndexI(), tm.getsIndexJ()));
        subjectRoom.setText(tm.getCurrentTable().getRoomText(tm.getsIndexI(), tm.getsIndexJ()));
        subjectTeacher.setText(tm.getCurrentTable().getTeacherText(tm.getsIndexI(), tm.getsIndexJ()));
    }

    private void setSubjectMenuFields(Subject s) {
        subjectName.setText(s.getSubject());
        subjectRoom.setText(s.getRoom());
        subjectTeacher.setText(s.getTeacher());
    }

    public void writeSubjectData() {
        selectedSubject.setText(subjectName.getText() + "\n" + subjectRoom.getText());
        tm.getCurrentTable().setSubjectText(subjectName.getText(), tm.getsIndexI(), tm.getsIndexJ());
        tm.getCurrentTable().setRoomText(subjectRoom.getText(), tm.getsIndexI(), tm.getsIndexJ());
        tm.getCurrentTable().setTeacherText(subjectTeacher.getText(), tm.getsIndexI(), tm.getsIndexJ());
    }

    //
    //################################autoCompletePane################################
    //
    public void autocompleteLabelClicked(MouseEvent event) {
        Label l = (Label) event.getSource();
        for (int i = 0; i < tm.getCurrentTable().getOptions().size(); i++) {
            if (l == autoCompletePane.getPane().getChildren().get(i)) {
                writeAutocomplete(i);
            }
        }
    }

    public void autocompleteSubject(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            enterPressed();
        } else if (event.getCode() == KeyCode.UP) {
            autoCompletePane.upPressed();
        } else if (event.getCode() == KeyCode.DOWN) {
            autoCompletePane.downPressed();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            //just here so handleInput() doesn't get called ._.
        } else {
            handleInput();
        }
    }

    private void enterPressed() {
        if (autoCompletePane.isAutocompleteFucused()) {
            writeAutocomplete();
        } else {
            subjectMenu.hide();
        }
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

    private void writeAutocomplete() {
        setSubjectMenuFields(tm.getCurrentTable().getOption(autoCompletePane.getAutocompleteIndex()));
        autoCompletePane.hide();
    }

    private void writeAutocomplete(int index) {
        setSubjectMenuFields(tm.getCurrentTable().getOption(index));
        autoCompletePane.hide();
    }

    //
    //################################subjectContextMenu################################
    //
    @FXML
    private void subjectContextMenu(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedSubject(event);
            subjectContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedSubject);
            hideOtherMenus(subjectContextMenu);
        }
    }

    public void clear() {
        tm.clearSubject();
        initNewTimetable();
    }

    public void delete() {
        tm.deleteSubject();
        initNewTimetable();
    }

    public void addAbove() {
        tm.addSubjectAbove();
        initNewTimetable();
    }

    public void addBelow() {
        tm.addSubjectBelow();
        initNewTimetable();
    }

    private void getSelectedSubject(Event event) {
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (event.getSource() == subjects[i][j]) {
                    selectedSubject = subjects[i][j];
                    tm.setsIndexI(i);
                    tm.setsIndexJ(j);
                    break;
                }
            }
        }
    }

    //
    //################################subjects################################
    //
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

}
