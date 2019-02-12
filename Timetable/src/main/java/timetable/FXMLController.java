package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class FXMLController implements Initializable {

    final static String selectedColor = "-fx-background-color: #66DD7744";
    final static String unselectedColor = "-fx-background-color: #00000000";

    TimetableManager tm;

    JFXButton[] days;
    JFXButton[] times;
    JFXButton[][] subjects;
    JFXToggleButton[] dayToggles;
    Label[] dayLabels;
    GridPane[] dayPanes;
    JFXButton selectedDay;
    JFXButton selectedTime;
    JFXButton selectedSubject;
    Label selectedAutocompleteOption;

    List<SomePane> menus = new ArrayList<SomePane>();

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
    EventHandler<Event> subjectMenuOnShow;
    EventHandler<Event> subjectMenuOnHide;

    GridPane autoCompletePane;
    Label selectedAutoCompleteOption;
    EventHandler<KeyEvent> subjectMenuKeyPressed;

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

    TranslateTransition dayOverlayComeUp;
    FadeTransition dayOverlayFadeIn;
    TranslateTransition dayOverlayGoDown;
    FadeTransition dayOverlayFadeOut;
    ParallelTransition showDayOverlay;
    ParallelTransition hideDayOverlay;

    TranslateTransition subjectOverlayComeUp;
    FadeTransition subjectOverlayFadeIn;
    TranslateTransition subjectOverlayGoDown;
    FadeTransition subjectOverlayFadeOut;
    ParallelTransition showSubjectOverlay;
    ParallelTransition hideSubjectOverlay;

    List<Timetable> timetables;
    Timetable currentTable;
    DataManager dm;

    int animationDuration = 200;
    int animationDistance = 50;
    double animationFocusOffsetMultiplier = 0.6;
    int tIndexI = 0;
    int sIndexI = 0;
    int sIndexJ = 0;
    int autocompleteIndex = 0;
    int timeTableIndex = 0;
    int tableCount = 0;

    boolean menuPaneHidden = true;
    boolean dayOverlayHidden = true;
    boolean timeOverlayHidden = true;
    boolean subjectOverlayHidden = true;
    boolean secondarySubjectOverlayHidden = true;
    boolean autocompleteFocused = false;

    @FXML
    private AnchorPane subjectOverlay;
    @FXML
    private JFXTextField sOverlaySubject;
    @FXML
    private JFXTextField sOverlayRoom;
    @FXML
    private JFXTextField sOverlayTeacher;
    @FXML
    private JFXButton sOverlayDone;
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
    private AnchorPane dayOverlay;
    @FXML
    private JFXToggleButton dOverlayMonday;
    @FXML
    private JFXButton dOverlayDone;
    @FXML
    private JFXToggleButton dOverlayTuesday;
    @FXML
    private JFXToggleButton dOverlayWednesday;
    @FXML
    private JFXToggleButton dOverlayThursday;
    @FXML
    private JFXToggleButton dOverlayFriday;
    @FXML
    private JFXToggleButton dOverlaySaturday;
    @FXML
    private JFXToggleButton dOverlaySunday;
    @FXML
    private Label dOverlayMondayLabel;
    @FXML
    private Label dOverlayTuesdayLabel;
    @FXML
    private Label dOverlayWednesdayLabel;
    @FXML
    private Label dOverlayThursdayLabel;
    @FXML
    private Label dOverlayFridayLabel;
    @FXML
    private Label dOverlaySaturdayLabel;
    @FXML
    private Label dOverlaySundayLabel;
    @FXML
    private GridPane dOverlayMondayPane;
    @FXML
    private GridPane dOverlayTuesdayPane;
    @FXML
    private GridPane dOverlayWednesdayPane;
    @FXML
    private GridPane dOverlayThursdayPane;
    @FXML
    private GridPane dOverlayFridayPane;
    @FXML
    private GridPane dOverlaySaturdayPane;
    @FXML
    private GridPane dOverlaySundayPane;
    @FXML
    private GridPane dOverlayGrid;
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
    private AnchorPane timePickerOverlay;
    @FXML
    private JFXTimePicker timepicker;
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
        subjectName = new JFXTextField();
        subjectName.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        subjectName.setPrefWidth(500);
        subjectName.setPrefHeight(100);
        subjectName.setPromptText("name");
        subjectName.getStyleClass().add("customTextfield");
        subjectRoom = new JFXTextField();
        subjectRoom.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        subjectRoom.setPrefWidth(500);
        subjectRoom.setPrefHeight(100);
        subjectRoom.getStyleClass().add("customTextfield");
        subjectRoom.setPromptText("room");
        subjectTeacher = new JFXTextField();
        subjectTeacher.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenus);
        subjectTeacher.setPrefWidth(500);
        subjectTeacher.setPrefHeight(100);
        subjectTeacher.getStyleClass().add("customTextfield");
        subjectTeacher.setPromptText("teacher");
        //EventHandler
        subjectMenuOnShow = (Event n) -> {
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
        };
        subjectMenuOnHide = (Event n) -> {
            selectedSubject.setText(subjectName.getText() + "\n" + subjectRoom.getText());
            tm.getCurrentTable().setSubjectText(subjectName.getText(), tm.getsIndexI(), tm.getsIndexJ());
            tm.getCurrentTable().setRoomText(subjectRoom.getText(), tm.getsIndexI(), tm.getsIndexJ());
            tm.getCurrentTable().setTeacherText(subjectTeacher.getText(), tm.getsIndexI(), tm.getsIndexJ());
        };
        subjectMenu.add(subjectName);
        subjectMenu.add(subjectRoom);
        subjectMenu.add(subjectTeacher);
        subjectMenu.setOnShow(subjectMenuOnShow);
        subjectMenu.setOnHide(subjectMenuOnHide);

        //autocomplete pane
        autoCompletePane = new GridPane();
        subjectMenuKeyPressed = (KeyEvent n) -> {
            autocompleteSubject(n);
        };
        subjectName.addEventHandler(KeyEvent.KEY_PRESSED, subjectMenuKeyPressed);

        currentTable = tm.getCurrentTable();
        timetables = tm.getTimetables();

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

        //day overlay transitions        
        dayOverlayComeUp = new TranslateTransition(Duration.millis(animationDuration));
        dayOverlayComeUp.setFromY(animationDistance);
        dayOverlayComeUp.setToY(0);

        dayOverlayFadeIn = new FadeTransition(Duration.millis(animationDuration));
        dayOverlayFadeIn.setFromValue(0);
        dayOverlayFadeIn.setToValue(1);

        dayOverlayGoDown = new TranslateTransition(Duration.millis(animationDuration));
        dayOverlayGoDown.setToY(animationDistance);

        dayOverlayFadeOut = new FadeTransition(Duration.millis(animationDuration));
        dayOverlayFadeOut.setToValue(0);

        showDayOverlay = new ParallelTransition(dayOverlay);
        showDayOverlay.getChildren().add(dayOverlayComeUp);
        showDayOverlay.getChildren().add(dayOverlayFadeIn);

        hideDayOverlay = new ParallelTransition(dayOverlay);
        hideDayOverlay.getChildren().add(dayOverlayGoDown);
        hideDayOverlay.getChildren().add(dayOverlayFadeOut);

        //subject overlay transitions
        subjectOverlayComeUp = new TranslateTransition(Duration.millis(animationDuration));
        subjectOverlayComeUp.setFromY(animationDistance);
        subjectOverlayComeUp.setToY(0);

        subjectOverlayFadeIn = new FadeTransition(Duration.millis(animationDuration));
        subjectOverlayFadeIn.setFromValue(0);
        subjectOverlayFadeIn.setToValue(1);

        subjectOverlayGoDown = new TranslateTransition(Duration.millis(animationDuration));
        subjectOverlayGoDown.setToY(animationDistance);

        subjectOverlayFadeOut = new FadeTransition(Duration.millis(animationDuration));
        subjectOverlayFadeOut.setToValue(0);

        showSubjectOverlay = new ParallelTransition(subjectOverlay);
        showSubjectOverlay.getChildren().add(subjectOverlayComeUp);
        showSubjectOverlay.getChildren().add(subjectOverlayFadeIn);

        hideSubjectOverlay = new ParallelTransition(subjectOverlay);
        hideSubjectOverlay.getChildren().add(subjectOverlayGoDown);
        hideSubjectOverlay.getChildren().add(subjectOverlayFadeOut);

        nameLabel.toBack();
        nameBackground.toBack();
        menuIcon.toFront();

        menuIcon.setTranslateX(-animationDistance);

        menuPaneScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        menuPaneNew.setRipplerFill(Color.web("#66DD77"));
        menuPaneDelete.setRipplerFill(Color.web("#66DD77"));

        timepicker.setIs24HourView(true);

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
        sOverlaySubject.focusedProperty().addListener(n -> {
            if (!sOverlaySubject.isFocused()) {
                autoCompletePane.setVisible(false);
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
        dayToggles = new JFXToggleButton[7];
        dayToggles[0] = dOverlayMonday;
        dayToggles[1] = dOverlayTuesday;
        dayToggles[2] = dOverlayWednesday;
        dayToggles[3] = dOverlayThursday;
        dayToggles[4] = dOverlayFriday;
        dayToggles[5] = dOverlaySaturday;
        dayToggles[6] = dOverlaySunday;
        dayLabels = new Label[10];
        dayLabels[0] = dOverlayMondayLabel;
        dayLabels[1] = dOverlayTuesdayLabel;
        dayLabels[2] = dOverlayWednesdayLabel;
        dayLabels[3] = dOverlayThursdayLabel;
        dayLabels[4] = dOverlayFridayLabel;
        dayLabels[5] = dOverlaySaturdayLabel;
        dayLabels[6] = dOverlaySundayLabel;
        dayPanes = new GridPane[7];
        dayPanes[0] = dOverlayMondayPane;
        dayPanes[1] = dOverlayTuesdayPane;
        dayPanes[2] = dOverlayWednesdayPane;
        dayPanes[3] = dOverlayThursdayPane;
        dayPanes[4] = dOverlayFridayPane;
        dayPanes[5] = dOverlaySaturdayPane;
        dayPanes[6] = dOverlaySundayPane;
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
        //Timetable currentTable = tm.getCurrentTable();

        subjectGrid.getChildren().removeIf(e -> (e.getClass() == JFXButton.class));
        subjectGrid.getColumnConstraints().clear();
        subjectGrid.getRowConstraints().clear();

        subjectGrid.add(name, 0, 0, 1, 1);
        menuPaneName.setText(currentTable.getName());

        int pos = 0;
        for (int i = 0; i < days.length; i++) {
            if (currentTable.isDayDisplayed(i)) {
                subjectGrid.add(days[i], pos + 1, 0, 1, 1);
                pos++;
            }
        }
        for (int i = 0; i < times.length; i++) {
            if (i < currentTable.getLessons()) {
                subjectGrid.add(times[i], 0, i + 1, 1, 1);
                times[i].setText(currentTable.getTimeText(i));
            }
        }
        pos = 0;
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (j < currentTable.getLessons() && currentTable.isDayDisplayed(i)) {
                    subjectGrid.add(subjects[i][j], pos + 1, j + 1, 1, 1);
                    subjects[i][j].setText(
                            currentTable.getSubjectText(i, j)
                            + "\n" + currentTable.getRoomText(i, j)
                    );
                }
            }
            if (currentTable.isDayDisplayed(i)) {
                pos++;
            }
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1), e -> resizeFonts())
        );
        timeline.play();
    }

    public void showDayOverlay(double x, double y) {
        dayOverlayHidden = false;

        hideOtherOverlays(1);

        double hf = 4.2;
        double w = selectedDay.getHeight() * 2.8;
        double h = selectedDay.getHeight() * hf;

        dayOverlay.setPrefWidth(w);
        dayOverlay.setPrefHeight(h);

        for (int i = 0; i < 7; i++) {
            dayToggles[i].setSelected(currentTable.isDayDisplayed(i));
            dayToggles[i].setScaleX(h / hf * 0.012);
            dayToggles[i].setScaleY(h / hf * 0.012);
            dayToggles[i].setMaxHeight((h) / 8);

            dayLabels[i].setFont(new Font(h / hf * 0.22));
            dayPanes[i].setPadding(new Insets(0, h / hf * 0.4, 0, h / hf * 0.4));
        }

        dOverlayGrid.setPadding(new Insets(h / hf * 0.1, 0, 0, 0));
        dOverlayDone.setFont(new Font(h / hf * 0.22));

        if (subjectGrid.getWidth() - x > w) {
            dayOverlay.setLayoutX(x);
        } else {
            dayOverlay.setLayoutX(subjectGrid.getWidth() - w);
        }

        if (subjectGrid.getHeight() - y > h) {
            dayOverlay.setLayoutY(y);
        } else {
            dayOverlay.setLayoutY(subjectGrid.getHeight() - h);
        }

        dayOverlay.setVisible(true);
        showDayOverlay.play();

        Timeline focus = new Timeline(new KeyFrame(
                Duration.millis(animationDuration * animationFocusOffsetMultiplier),
                e -> dOverlayMonday.requestFocus()));
        focus.play();
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
        dayOverlay.setVisible(false);
        subjectOverlay.setVisible(false);
    }

    public void hideOtherMenus(SomePane sp) {
        for (SomePane s : menus) {
            if (!s.equals(sp)) {
                s.hide();
            }
        }
    }

    @FXML
    public void hideAllMenus() {
        for (SomePane s : menus) {
            s.hide();
        }
    }

    public void hideOtherOverlays(int num) {
        autoCompletePane.setVisible(false);
        if (num != 0) {
            hideMenuPane();
        }
        if (num != 1) {
            hideDayOverlay(false, false);
        }
        if (num != 3) {
            hideSubjectOverlay(false, false);
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

    public void hideDayOverlay(boolean writeData, boolean refocus) {
        if (dayOverlayHidden == false) {
            dayOverlayHidden = true;

            if (selectedDay != null && refocus) {
                selectedDay.requestFocus();
            }
            if (writeData) {
                for (int i = 0; i < 7; i++) {
                    currentTable.setDayDisplayed(dayToggles[i].isSelected(), i);
                }
                initNewTimetable();
            }
            hideDayOverlay.play();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(animationDuration),
                            e -> dayOverlay.setVisible(false))
            );
            timeline.play();
        }
    }

    public void hideSubjectOverlay(boolean writeData, boolean refocus) {
        if (subjectOverlayHidden == false) {
            subjectOverlayHidden = true;

            if (selectedSubject != null && refocus) {
                selectedSubject.requestFocus();
            }
            if (writeData) {
                writeSOverlayData();
            }
            autoCompletePane.setVisible(false);
            hideSubjectOverlay.play();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(animationDuration),
                            e -> subjectOverlay.setVisible(false))
            );
            timeline.play();
        }
    }

    private void writeSOverlayData() {
        if (selectedSubject != null) {
            selectedSubject.setText(sOverlaySubject.getText() + "\n" + sOverlayRoom.getText());
            currentTable.setSubjectText(sOverlaySubject.getText(), sIndexI, sIndexJ);
            currentTable.setRoomText(sOverlayRoom.getText(), sIndexI, sIndexJ);
            currentTable.setTeacherText(sOverlayTeacher.getText(), sIndexI, sIndexJ);
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

        menuPaneName.setText(currentTable.getName());

        menuPane.setVisible(true);
        menuBackgroundPane.setVisible(true);
        showMenuPane.play();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(animationDuration * animationFocusOffsetMultiplier), e -> menuPaneName.requestFocus())
        );
        timeline.play();
    }

    @FXML
    private void showDayOverlay(MouseEvent event) {

        if (event.isSecondaryButtonDown()) {
            selectedDay = (JFXButton) event.getSource();
            double x = event.getSceneX();
            double y = event.getSceneY();
            showDayOverlay(x, y);
        } else {
            hideOtherOverlays(Integer.MAX_VALUE);
        }
    }

    private void showSubjectOverlay(ActionEvent event) {
        subjectOverlayHidden = false;

        hideOtherOverlays(3);

        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (event.getSource() == subjects[i][j]) {
                    selectedSubject = subjects[i][j];
                    sIndexI = i;
                    sIndexJ = j;
                    break;
                }
            }
        }

        currentTable.updateReferences();

        double hf = 3;
        double x = selectedSubject.getLayoutX() + 1;
        double y = selectedSubject.getLayoutY() + 1;
        double w = selectedSubject.getWidth() * 1.2;
        double h = selectedSubject.getHeight() * hf;

        if (subjectGrid.getWidth() - x < w) {
            x = subjectGrid.getWidth() - w;
        }

        if (subjectGrid.getHeight() - y < h) {
            y = subjectGrid.getHeight() - h;
        }

        subjectOverlay.setPrefWidth(w);
        subjectOverlay.setPrefHeight(h);

        subjectOverlay.setLayoutX(x);
        subjectOverlay.setLayoutY(y);

        sOverlaySubject.setPadding(new Insets(h / hf * 0.35, h / hf * 0.4, 0, h / hf * 0.4));
        sOverlayRoom.setPadding(new Insets(h / hf * 0.35, h / hf * 0.4, 0, h / hf * 0.4));
        sOverlayTeacher.setPadding(new Insets(h / hf * 0.35, h / hf * 0.4, 0, h / hf * 0.4));

        sOverlaySubject.setFont(new Font(h / hf * 0.22));
        sOverlayRoom.setFont(new Font(h / hf * 0.22));
        sOverlayTeacher.setFont(new Font(h / hf * 0.22));
        sOverlayDone.setFont(new Font(h / hf * 0.22));

        sOverlaySubject.setText(currentTable.getSubjectText(sIndexI, sIndexJ));
        sOverlayRoom.setText(currentTable.getRoomText(sIndexI, sIndexJ));
        sOverlayTeacher.setText(currentTable.getTeacherText(sIndexI, sIndexJ));
        subjectOverlay.setVisible(true);

        autoCompletePane.setLayoutX(x + h / hf * 0.4);
        autoCompletePane.setPrefWidth(w - h / hf * 0.8);
        autoCompletePane.setPrefHeight(20);

        Timeline t = new Timeline(new KeyFrame(
                Duration.millis(40),
                e -> autoCompletePane.setLayoutY(subjectOverlay.getLayoutY() + sOverlaySubject.getHeight() + 10)));
        t.play();

        autoCompletePane.toFront();

        showSubjectOverlay.play();

        selectedSubject.requestFocus();

        Timeline focus = new Timeline(new KeyFrame(
                Duration.millis(animationDuration * animationFocusOffsetMultiplier),
                e -> sOverlaySubject.requestFocus()));
        focus.play();
    }

    @FXML
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
    private void callHideDayOverlay(ActionEvent event) {
        hideDayOverlay(true, true);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1), e -> resizeFonts())
        );
        timeline.play();
    }

    @FXML
    private void callHideSubjectOverlay(ActionEvent event) {
        hideSubjectOverlay(true, true);
    }

    @FXML
    private void sOverlaySubject_kp(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (autocompleteFocused) {
                String subject = currentTable.getOption(autocompleteIndex).getSubject();
                String room = currentTable.getOption(autocompleteIndex).getRoom();
                String teacher = currentTable.getOption(autocompleteIndex).getTeacher();
                sOverlaySubject.setText(subject);
                sOverlayRoom.setText(room);
                sOverlayTeacher.setText(teacher);
                autocompleteFocused = false;
                autoCompletePane.setVisible(false);
            } else {
                hideSubjectOverlay(true, true);
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            hideSubjectOverlay(false, true);
        } else if (event.getCode() == KeyCode.UP && sOverlaySubject.isFocused() && autoCompletePane.isVisible()) {
            if (autocompleteFocused) {
                autoCompletePane.getChildren().get(autocompleteIndex).setStyle(unselectedColor);
                autocompleteIndex--;
                if (autocompleteIndex >= 0) {
                    autoCompletePane.getChildren().get(autocompleteIndex).setStyle(selectedColor);
                } else {
                    autocompleteFocused = false;
                }
            } else {
                autocompleteFocused = true;
                autocompleteIndex = autoCompletePane.getChildren().size() - 1;
                autoCompletePane.getChildren().get(autoCompletePane.getChildren().size() - 1).setStyle(selectedColor);
            }
        } else if (event.getCode() == KeyCode.DOWN && sOverlaySubject.isFocused() && autoCompletePane.isVisible()) {
            if (autocompleteFocused) {
                autoCompletePane.getChildren().get(autocompleteIndex).setStyle(unselectedColor);
                autocompleteIndex++;
                if (autocompleteIndex < autoCompletePane.getChildren().size()) {
                    autoCompletePane.getChildren().get(autocompleteIndex).setStyle(selectedColor);
                } else {
                    autocompleteFocused = false;
                }
            } else {
                autocompleteFocused = true;
                autocompleteIndex = 0;
                autoCompletePane.getChildren().get(0).setStyle(selectedColor);
            }
        } else if (sOverlaySubject.getText().length() > 0) {
            autoCompletePane.getChildren().removeIf(e -> (e.getClass() == Label.class));
            List<Subject> options = currentTable.getAutocompleteOptions(sOverlaySubject.getText());
            if (options.size() > 0) {
                autoCompletePane.setPrefHeight((selectedSubject.getHeight() * 0.4) * options.size());
                for (int i = 0; i < options.size() - 1; i++) {
                    Label button = makeAutocompleteButton(options.get(i).getSubject(), false);
                    autoCompletePane.add(button, 0, i, 1, 1);
                }
                Label button = makeAutocompleteButton(options.get(options.size() - 1).getSubject(), true);
                autoCompletePane.add(button, 0, options.size() - 1, 1, 1);
            }
            autoCompletePane.setVisible(options.size() > 0);
        }
    }

    private Label makeAutocompleteButton(String content, boolean bottom) {
        Label button = new Label(content);
        button.setPrefWidth(500);
        button.setMinHeight(selectedSubject.getHeight() * 0.4);
        button.setPrefHeight(selectedSubject.getHeight() * 0.4);
        button.setMaxHeight(selectedSubject.getHeight() * 0.4);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Label l = (Label) event.getSource();
                for (int i = 0; i < tm.currentTable.options.size(); i++) {
                    if (l == autoCompletePane.getChildren().get(i)) {
                        String subject = tm.currentTable.getOption(autocompleteIndex).getSubject();
                        String room = tm.currentTable.getOption(autocompleteIndex).getRoom();
                        String teacher = tm.currentTable.getOption(autocompleteIndex).getTeacher();
                        subjectName.setText(subject);
                        subjectRoom.setText(room);
                        subjectTeacher.setText(teacher);
                        autocompleteFocused = false;
                        autoCompletePane.setVisible(false);
                    }
                }
            }
        });
        button.setPadding(new Insets(0, 0, 0, selectedSubject.getHeight() * 0.025));
        button.setFont(new Font(selectedSubject.getHeight() * 0.22));

        if (bottom) {
            button.getStyleClass().add("roundedBottomButton");
        } else {
            button.getStyleClass().add("notRoundedButton");
        }
        return button;
    }

    @FXML
    private void sOverlayRoom_kp(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            hideSubjectOverlay(true, true);
        } else if (event.getCode() == KeyCode.ESCAPE) {
            hideSubjectOverlay(false, true);
        }
    }

    @FXML
    private void sOverlayTeacher_kp(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            hideSubjectOverlay(true, true);
        } else if (event.getCode() == KeyCode.ESCAPE) {
            hideSubjectOverlay(false, true);
        }
    }

    @FXML
    private void daySwitched(ActionEvent event) {
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
    private void updateCurrentTableName(KeyEvent event) {
        currentTable.setName(menuPaneName.getText());
    }

    @FXML
    private void menuPane_kp(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            hideMenuPane();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            hideMenuPane();
        }
        JFXButton b = (JFXButton) menuPaneTables.getChildren().get(timeTableIndex);
        b.setText(currentTable.getName());
    }

    @FXML
    private void dayButton_kp(KeyEvent event) {
        if (event.isControlDown()) {
            selectedDay = (JFXButton) event.getSource();
            double x = selectedDay.getLayoutX() + selectedDay.getWidth() / 2;
            double y = selectedDay.getLayoutY() + selectedDay.getHeight() / 2;
            showDayOverlay(x, y);
        }
    }

    private void subjectButton_kp(KeyEvent event) {

    }

    @FXML
    private void addNewTimetable(ActionEvent event) {
        addNewTimetable();
    }

    public void addNewTimetable() {
        Timetable t = new Timetable("Timetable" + tableCount);
        timetables.add(t);
        addTimetableToMenu(t);
        setCurrentTable(t);
        timeTableIndex = timetables.size() - 1;
        highlightTimetable();
        tableCount++;
    }

    public void addTimetableToMenu(Timetable timetable) {
        int size = timetables.size();

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
                    timeTableIndex = i;
                    setCurrentTable(timetables.get(i));
                }
            }
            highlightTimetable();
        });

        int vGridPos = menuPaneTables.getChildren().size();

        menuPaneTables.setPrefHeight(h * size * 0.6);

        menuPaneTables.add(tableButton, 0, vGridPos, 1, 1);

        System.out.println("timetables.size() : " + size);

        System.out.println("menuPaneNew.getHeight() : " + h);

        System.out.println("menuPaneTables.getChildren().size() : " + vGridPos);

        System.out.println("button height : " + h * 0.6);

        System.out.println("menuPaneTables.getPrefHeight() : " + menuPaneTables.getPrefHeight());

        System.out.println("--------------------------------------------------------------");
    }

    private void setCurrentTable(Timetable t) {
        currentTable = t;
        initNewTimetable();
    }

    @FXML
    private void deleteTimetable(ActionEvent event) {

        System.out.println("###########################################################");

        timetables.remove(timeTableIndex);
        menuPaneTables.getChildren().remove(timeTableIndex);
        if (timetables.size() == 0) {
            addNewTimetable();
        } else if (timeTableIndex < timetables.size()) {
            currentTable = timetables.get(timeTableIndex);
        } else {
            timeTableIndex--;
            currentTable = timetables.get(timeTableIndex);
        }

        initNewTimetable();

        menuPaneTables.getChildren().removeIf(e -> (e.getClass() == JFXButton.class));

        for (Timetable t : timetables) {
            addTimetableToMenu(t);
        }

        highlightTimetable();
    }

    public void highlightTimetable() {
        for (Node b : menuPaneTables.getChildren()) {
            JFXButton button = (JFXButton) b;
            button.setStyle(unselectedColor);
        }
        JFXButton highlightedButton = (JFXButton) menuPaneTables.getChildren().get(timeTableIndex);
        highlightedButton.setStyle(selectedColor);
    }

    @FXML
    private void showInfo(ActionEvent event) {
        //show info pane
    }

    @FXML
    private void timeContextMenu(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            for (int i = 0; i < times.length; i++) {
                if (times[i] == event.getSource()) {
                    selectedTime = times[i];
                    tm.settIndexI(i);
                    break;
                }
            }
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
            for (int i = 0; i < times.length; i++) {
                if (times[i] == event.getSource()) {
                    selectedTime = times[i];
                    tm.settIndexI(i);
                    break;
                }
            }
            timeContextMenu.show(selectedTime);
        }
    }

    @FXML
    private void subjectMenu(ActionEvent event) {
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (subjects[i][j] == event.getSource()) {
                    selectedSubject = subjects[i][j];
                    tm.setsIndexI(i);
                    tm.setsIndexJ(j);
                    break;
                }
            }
        }
        subjectMenu.show(selectedSubject);
        hideOtherMenus(subjectMenu);
    }

    public void autocompleteSubject(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            
        } else if (e.getCode() == KeyCode.UP) {

        } else if (e.getCode() == KeyCode.DOWN) {

        } else{
            
        }
    }
    
    public void enterPressed(){
        
    }

    @FXML
    private void subjectContextMenu(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            for (int i = 0; i < subjects.length; i++) {
                for (int j = 0; j < subjects[0].length; j++) {
                    if (subjects[i][j] == event.getSource()) {
                        selectedSubject = subjects[i][j];
                        tm.setsIndexI(i);
                        tm.setsIndexJ(j);
                        break;
                    }
                }
            }
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
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (event.getSource() == subjects[i][j]) {
                    selectedSubject = subjects[i][j];
                    sIndexI = i;
                    sIndexJ = j;
                    break;
                }
            }
        }
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            subjectContextMenu.show(selectedSubject);
        } else if (event.isControlDown() && event.getCode() == KeyCode.UP) {
            moveSubjectUp(sIndexI, sIndexJ);
        } else if (event.isControlDown() && event.getCode() == KeyCode.DOWN) {
            moveSubjectDown(sIndexI, sIndexJ);
        } else if (event.isControlDown() && event.getCode() == KeyCode.LEFT) {
            moveSubjectLeft(sIndexI, sIndexJ);
        } else if (event.isControlDown() && event.getCode() == KeyCode.RIGHT) {
            moveSubjectRight(sIndexI, sIndexJ);
        } else if (event.getCode() == KeyCode.DELETE) {
            currentTable.clearSubject(sIndexI, sIndexJ);
            initNewTimetable();
        }

    }

    public void moveSubjectUp(int i, int j) {
        tm.getCurrentTable().moveSubjectUp(sIndexI, sIndexJ);
        initNewTimetable();
        if (j > 0) {
            subjects[i][j - 1].requestFocus();
        }
    }

    public void moveSubjectDown(int i, int j) {
        tm.getCurrentTable().moveSubjectDown(sIndexI, sIndexJ);
        initNewTimetable();
        if (j < currentTable.getLessons()) {
            subjects[i][j + 1].requestFocus();
        }
    }

    public void moveSubjectLeft(int i, int j) {
        tm.getCurrentTable().moveSubjectLeft(sIndexI, sIndexJ);
        initNewTimetable();
        if (i > 0) {
            subjects[i - 1][j].requestFocus();
        }
    }

    public void moveSubjectRight(int i, int j) {
        tm.getCurrentTable().moveSubjectRight(sIndexI, sIndexJ);
        initNewTimetable();
        int days = 0;
        for (int d = 0; d < currentTable.days.length; d++) {
            if (currentTable.isDayDisplayed(d)) {
                days++;
            }
        }
        if (i < days) {
            subjects[i + 1][j].requestFocus();
        }
    }

    private void hideAllMenus(ActionEvent event) {
        hideAllMenus();
    }

}
