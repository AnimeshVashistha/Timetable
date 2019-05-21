package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import timetable.Datatypes.Subject;

/**
 *
 * @author Tobias
 */
public class GUI {

    final static int ANIMATION_DURATION = 200;
    final static int ANIMATION_DISTANCE = 50;
    final static double GAP_SIZE = 2;
    final static double FOCUS_ANIMATION_OFFSET_FACTOR = 0.6;
    final static double FONT_FACTOR = 0.22;
    final static String[] ENGLISH_DAY_NAMES = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    final static String[] GERMAN_DAY_NAMES = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};

    static String[] dayNames = ENGLISH_DAY_NAMES;

    static String ac1 = "#66DD77";
    static String ac2 = "#44CC55";
    static String fg2 = "#888888";
    static String fg1 = "#FFFFFF";
    static String bg1 = "#EEEEEE";
    static String bg2 = "#E8E8E8";
    static String bg3 = "#DDDDDD";
    static String bg4 = "#CCCCCC";
    static String rpf = "#000000";
    static String text = "#292929";
    static String transparent = "#00000000";
    static String semiTransparent = "#00000055";

    TimetableManager tm;

    AnchorPane bg;
    GridPane subjectGrid;

    EventHandler<ActionEvent> nameAction;
    EventHandler<ActionEvent> dayAction;
    EventHandler<MouseEvent> dayPressed;
    EventHandler<KeyEvent> dayKeyReleased;
    EventHandler<ActionEvent> timeAction;
    EventHandler<MouseEvent> timePressed;
    EventHandler<KeyEvent> timeKeyReleased;
    EventHandler<ActionEvent> subjectAction;
    EventHandler<MouseEvent> subjectPressed;
    EventHandler<KeyEvent> subjectKeyReleased;
    MenuButton name;
    HBox tabBox;
    Label tabA;
    Label tabB;
    JFXButton[] days;
    JFXButton[] times;
    JFXButton[][] subjects;
    JFXButton selectedDay;
    JFXButton selectedTime;
    JFXButton selectedSubject;

    EventHandler<KeyEvent> hideAllMenusK;
    List<Hideable> menus;

    //menu
    EventHandler<Event> menuOnShow;
    EventHandler<Event> menuOnHide;
    EventHandler<KeyEvent> writeMenuData;
    EventHandler<ActionEvent> timetableButtonPressed;
    SidebarPane menu;
    MenuBackgroundPane menuBackgroundPane;
    JFXTextField menuName;
    JFXButton settings;
    JFXButton addTimetable;
    JFXButton deleteTimetable;
    ScrollPane menuScrollPane;
    TimetablePane timetablePane;

    //day menu
    EventHandler<Event> dayMenuOnShow;
    EventHandler<Event> dayMenuOnHide;
    EventHandler<Event> dayToggled;
    AdvancedOptionsPane dayMenu;
    GridPane[] dayPanes;
    JFXToggleButton[] dayToggles;
    Label[] dayLabels;

    //day context menu
    OptionsPane dayContextMenu;
    JFXButton clearColumn;
    JFXButton deleteColumn;

    //time menu
    EventHandler<Event> timeMenuOnHide;
    TimePickerPane timeMenu;

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

    //subject context menu
    OptionsPane subjectContextMenu;
    JFXButton clear;
    JFXButton delete;
    JFXButton addAbove;
    JFXButton addBelow;

    boolean menuPaneHidden = true;
    boolean dayOverlayHidden = true;

    public GUI() {

        bg = new AnchorPane();
        bg.setPrefSize(900, 600);
        subjectGrid = new GridPane();
        subjectGrid.setVgap(GAP_SIZE);
        subjectGrid.setHgap(GAP_SIZE);
        bg.getChildren().add(subjectGrid);
        bg.setTopAnchor(subjectGrid, GAP_SIZE);
        bg.setRightAnchor(subjectGrid, GAP_SIZE);
        bg.setBottomAnchor(subjectGrid, GAP_SIZE);
        bg.setLeftAnchor(subjectGrid, GAP_SIZE);

        hideAllMenusK = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                hideAllMenus();
            }
        };

        initControlArrays();

        tm = new TimetableManager();

        initNewTimetable();

        menus = new ArrayList<Hideable>();

        //menu
        menuOnShow = (Event event) -> {
            hideOtherMenus(menu, menuBackgroundPane);
            updateMenuData();
            updateMenuTimetables();
            scaleMenu();
            menuBackgroundPane.show();
        };
        menuOnHide = (Event event) -> {
            menuBackgroundPane.hide();
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
        menuBackgroundPane = new MenuBackgroundPane(bg);
        menuBackgroundPane.setOnMousePressed(event -> {
            menu.hide();
        });
        menus.add(menuBackgroundPane);
        menu = new SidebarPane(bg);
        menus.add(menu);
        menu.setOnShow(menuOnShow);
        menu.setOnHide(menuOnHide);
        menuName = new JFXTextField();
        menuName.setPrefHeight(100);
        menuName.setPrefWidth(500);
        menuName.setPromptText("name");
        menuName.getStyleClass().add("customTextfield");
        menuName.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        menuName.addEventHandler(KeyEvent.KEY_RELEASED, writeMenuData);
        settings = new JFXButton("settings");
        settings.getStyleClass().add("notRoundedButton");
        settings.setPrefWidth(500);
        settings.setPrefHeight(150);
        settings.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        settings.addEventHandler(ActionEvent.ACTION, event -> {
            System.out.println("todo: add settings");
        });
        addTimetable = new JFXButton("add timetable");
        addTimetable.getStyleClass().add("notRoundedButton");
        addTimetable.setPrefWidth(500);
        addTimetable.setPrefHeight(150);
        addTimetable.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        addTimetable.addEventHandler(ActionEvent.ACTION, event -> {
            addTimetable();
        });
        deleteTimetable = new JFXButton("delete timetable");
        deleteTimetable.getStyleClass().add("notRoundedButton");
        deleteTimetable.setPrefWidth(500);
        deleteTimetable.setPrefHeight(150);
        deleteTimetable.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
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
        timetablePane.update(tm.getTimetablePairs(), timetableButtonPressed, tm.getTimeTableIndex());
        menuScrollPane.setContent(timetablePane.getPane());
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(60);
        menu.getPane().getRowConstraints().addAll(new RowConstraints(), new RowConstraints(), new RowConstraints(), new RowConstraints(), rc);
        menu.add(menuName);
        menu.add(settings);
        menu.add(addTimetable);
        menu.add(deleteTimetable);
        menu.add(menuScrollPane);

        //day menu
        dayMenuOnShow = (Event event) -> {
            scaleDayMenu();
            updateDayContextMenuData();
        };
        dayMenuOnHide = (Event event) -> {
            writeDayData();
        };
        dayToggled = (Event event) -> {
            dayToggled();
        };
        dayMenu = new AdvancedOptionsPane(bg);
        menus.add(dayMenu);
        dayMenu.setWidthFactor(3);
        dayMenu.setOnShow(dayMenuOnShow);
        dayMenu.setOnHide(dayMenuOnHide);
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
            dayToggles[i].addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
            dayLabels[i] = new Label(dayNames[i]);

            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS);
            dayPanes[i].addColumn(0, dayLabels[i]);
            dayPanes[i].addColumn(2, dayToggles[i]);
            dayPanes[i].getColumnConstraints().add(cc);
            dayMenu.add(dayPanes[i]);
        }
        dayMenu.setSpecificFocus(dayToggles[0]);
        dayMenu.setRequestSpecificFocus(true);

        //day context menu 
        dayContextMenu = new OptionsPane(bg);
        menus.add(dayContextMenu);
        clearColumn = new JFXButton("clear column");
        clearColumn.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        clearColumn.addEventHandler(ActionEvent.ACTION, event -> {
            clearColumn();
        });
        deleteColumn = new JFXButton("delete column");
        deleteColumn.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        deleteColumn.addEventHandler(ActionEvent.ACTION, event -> {
            deleteColumn();
        });
        dayContextMenu.addButton(clearColumn);
        dayContextMenu.addButton(deleteColumn);

        //tim menu
        timeMenuOnHide = (Event event) -> {
            tm.getCurrentTable().setTime(timeMenu.getTime(), tm.gettIndexI());
            initNewTimetable();
        };
        timeMenu = new TimePickerPane(bg);
        menus.add(timeMenu);
        timeMenu.setOnHide(timeMenuOnHide);

        //time context menu
        timeContextMenu = new OptionsPane(bg);
        menus.add(timeContextMenu);
        clearRow = new JFXButton("clear row");
        clearRow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        clearRow.addEventHandler(ActionEvent.ACTION, event -> {
            clearRow();
        });
        deleteRow = new JFXButton("delete row");
        deleteRow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        deleteRow.addEventHandler(ActionEvent.ACTION, event -> {
            deleteRow();
        });
        addRowAbove = new JFXButton("add row above");
        addRowAbove.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        addRowAbove.addEventHandler(ActionEvent.ACTION, event -> {
            addRowAbove();
        });
        addRowBelow = new JFXButton("add row below");
        addRowBelow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
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
        subjectName.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        subjectName.setPrefWidth(500);
        subjectName.setPrefHeight(100);
        subjectName.setPromptText("name");
        subjectName.getStyleClass().add("customTextfield");
        subjectRoom = new JFXTextField();
        subjectRoom.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        subjectRoom.addEventHandler(KeyEvent.KEY_RELEASED, writeSubjectData);
        subjectRoom.setPrefWidth(500);
        subjectRoom.setPrefHeight(100);
        subjectRoom.getStyleClass().add("customTextfield");
        subjectRoom.setPromptText("room");
        subjectTeacher = new JFXTextField();
        subjectTeacher.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
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
        clear.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        clear.addEventHandler(ActionEvent.ACTION, event -> {
            clear();
        });
        delete = new JFXButton("delete");
        delete.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        delete.addEventHandler(ActionEvent.ACTION, event -> {
            delete();
        });
        addAbove = new JFXButton("add above");
        addAbove.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        addAbove.addEventHandler(ActionEvent.ACTION, event -> {
            addAbove();
        });
        addBelow = new JFXButton("add below");
        addBelow.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        addBelow.addEventHandler(ActionEvent.ACTION, event -> {
            addBelow();
        });
        subjectContextMenu.addButton(clear);
        subjectContextMenu.addButton(delete);
        subjectContextMenu.addButton(addAbove);
        subjectContextMenu.addButton(addBelow);

        updateColors();

        bg.widthProperty().addListener(event -> {
            cancelMenus();
            new Timeline(
                    new KeyFrame(Duration.millis(1), n -> resizeFonts())
            ).play();
        });
        bg.heightProperty().addListener(event -> {
            cancelMenus();
            new Timeline(
                    new KeyFrame(Duration.millis(1), n -> resizeFonts())
            ).play();
        });
        subjectName.focusedProperty().addListener(event -> {
            if (!subjectName.isFocused()) {
                autoCompletePane.hide();
            }
        });

        new Timeline(
                new KeyFrame(Duration.millis(1), event -> bg.requestFocus())
        ).play();

        Locale l = Locale.getDefault();
        if (LocalDate.now().get(WeekFields.of(l).weekOfWeekBasedYear()) % 2 == 0) {
            selectTabA();
        } else {
            selectTabB();
        }
    }

    public void initControlArrays() {

        //name
        nameAction = (ActionEvent event) -> {
            menu();
        };
        name = new MenuButton();
        name.getStyleClass().add("menuButton");
        name.setMinSize(100, 40);
        name.setPrefSize(500, 150);
        name.addEventHandler(ActionEvent.ANY, nameAction);
        subjectGrid.add(name, 0, 0, 1, 2);

        //tabs
        tabBox = new HBox();
        tabBox.getStyleClass().add("roundedTopButton");
        tabBox.getStyleClass().add("unselectedTimeButton");
        tabBox.setSpacing(GAP_SIZE * 3);

        tabA = new Label("A");
        tabA.setAlignment(Pos.CENTER);
        tabA.setMinSize(0, 0);
        tabA.setPrefSize(1000, 110);
        tabA.getStyleClass().add("roundedTopButton");
        tabA.setOnMousePressed(event -> {
            selectTabA();
        });

        tabB = new Label("B");
        tabB.setAlignment(Pos.CENTER);
        tabB.setMinSize(0, 0);
        tabB.setPrefSize(1000, 110);
        tabB.getStyleClass().add("roundedTopButton");
        tabB.setOnMousePressed(event -> {
            selectTabB();
        });

        tabBox.getChildren().addAll(tabA, tabB);

        //days
        dayAction = (ActionEvent event) -> {
            dayMenu(event);
        };
        dayPressed = (MouseEvent event) -> {
            dayPressed(event);
        };
        dayKeyReleased = (KeyEvent event) -> {
            dayKeyReleased(event);
        };
        days = new JFXButton[7];
        for (int i = 0; i < days.length; i++) {
            JFXButton day = new JFXButton(ENGLISH_DAY_NAMES[i]);
            day.getStyleClass().add("dayButton");
            day.setMinSize(100, 40);
            day.setPrefSize(500, 150);
            day.addEventHandler(ActionEvent.ANY, dayAction);
            day.addEventHandler(MouseEvent.MOUSE_PRESSED, dayPressed);
            day.addEventHandler(KeyEvent.KEY_RELEASED, dayKeyReleased);
            days[i] = day;
        }

        //times
        timeAction = (ActionEvent event) -> {
            timeMenu(event);
        };
        timePressed = (MouseEvent event) -> {
            timePressed(event);
        };
        timeKeyReleased = (KeyEvent event) -> {
            timeKeyReleased(event);
        };
        times = new JFXButton[10];
        for (int i = 0; i < times.length; i++) {
            JFXButton time = new JFXButton();
            time.getStyleClass().add("timeButton");
            time.setMinSize(100, 40);
            time.setPrefSize(500, 150);
            time.addEventHandler(ActionEvent.ANY, timeAction);
            time.addEventHandler(MouseEvent.MOUSE_PRESSED, timePressed);
            time.addEventHandler(KeyEvent.KEY_RELEASED, timeKeyReleased);
            times[i] = time;
        }

        //subjects
        subjectAction = (ActionEvent event) -> {
            subjectMenu(event);
        };
        subjectPressed = (MouseEvent event) -> {
            subjectPressed(event);
        };
        subjectKeyReleased = (KeyEvent event) -> {
            subjectKeyReleased(event);
        };
        subjects = new JFXButton[7][10];
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                JFXButton subject = new JFXButton();
                subject.getStyleClass().add("subjectButton");
                subject.setMinSize(100, 40);
                subject.setPrefSize(500, 150);
                subject.addEventHandler(ActionEvent.ANY, subjectAction);
                subject.addEventHandler(MouseEvent.MOUSE_PRESSED, subjectPressed);
                subject.addEventHandler(KeyEvent.KEY_RELEASED, subjectKeyReleased);
                subjects[i][j] = subject;
            }
        }
    }

    public void resizeFonts() {
        double scaleFactor1 = 0.09;
        double scaleFactorHeightDependent1 = 0.3;
        double scaleFactorHeightDependent2 = 0.25;

        double w = times[0].getWidth();
        double h = times[0].getHeight();

        name.setFont(new Font((h + w) * scaleFactor1));

        tabA.setFont(new Font((h) * scaleFactorHeightDependent2));
        tabB.setFont(new Font((h) * scaleFactorHeightDependent2));

        for (JFXButton b : days) {
            b.setFont(new Font((h + w) * scaleFactor1));
        }
        for (JFXButton b : times) {
            b.setFont(new Font((h) * scaleFactorHeightDependent1));
        }
        for (JFXButton[] ba : subjects) {
            for (JFXButton b : ba) {
                b.setFont(new Font((h) * scaleFactorHeightDependent2));
            }
        }
    }

    public void initNewTimetable() {
        subjectGrid.getChildren().removeIf(node -> (node.getClass() == JFXButton.class));
        subjectGrid.getChildren().remove(tabBox);
        subjectGrid.getRowConstraints().clear();
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(5);
        subjectGrid.getRowConstraints().add(rc);

        name.setText(tm.getCurrentTablePair().getName());

        subjectGrid.add(tabBox, 1, 0, tm.getCurrentTable().getDisplayedDayCout(), 1);

        //display days
        int pos = 0;
        for (int i = 0; i < days.length; i++) {
            if (tm.getCurrentTable().isDayDisplayed(i)) {
                subjectGrid.add(days[i], pos + 1, 1, 1, 1);
                pos++;
            }
        }

        //display times
        for (int i = 0; i < times.length; i++) {
            if (i < tm.getCurrentTable().getLessons()) {
                subjectGrid.add(times[i], 0, i + 2, 1, 1);
                times[i].setText(tm.getCurrentTable().getTimeText(i));
            }
        }

        //display subjects
        pos = 0;
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (j < tm.getCurrentTable().getLessons() && tm.getCurrentTable().isDayDisplayed(i)) {
                    subjectGrid.add(subjects[i][j], pos + 1, j + 2, 1, 1);
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

        new Timeline(
                new KeyFrame(Duration.millis(1), event -> resizeFonts())
        ).play();;
    }

    public void updateColors() {
        //base controls
        bg.setStyle("-fx-background-color:" + bg1);
        name.updateColor();
        name.setRipplerFill(Color.web(GUI.fg2));
        tabBox.setStyle("-fx-background-color:" + bg4);
        selectTabA();
        for (JFXButton day : days) {
            day.setStyle("-fx-background-color:" + bg4);
            day.setRipplerFill(Color.web(rpf));
        }
        for (JFXButton time : times) {
            time.setStyle("-fx-background-color:" + bg3);
            time.setRipplerFill(Color.web(rpf));
        }
        for (JFXButton[] subjectA : subjects) {
            for (JFXButton subject : subjectA) {
                subject.setStyle("-fx-background-color:" + bg2);
                subject.setRipplerFill(Color.web(ac1));
            }
        }
        //menu
        menu.updateBaseColor();
        menuBackgroundPane.updateColor();
        menuName.setStyle("-fx-prompt-text-fill:" + fg2);
        menuName.setUnFocusColor(Color.web(fg2));
        menuName.setFocusColor(Color.web(ac1));
        settings.setTextFill(Color.web(text));
        settings.setRipplerFill(Color.web(ac1));
        addTimetable.setRipplerFill(Color.web(ac1));
        addTimetable.setTextFill(Color.web(text));
        deleteTimetable.setRipplerFill(Color.web(ac1));
        deleteTimetable.setTextFill(Color.web(text));
        menuScrollPane.setStyle("-fx-background-color: bg1;");
        timetablePane.updateColor();
        //day menu
        dayMenu.updateBaseColor();
        for(Label l : dayLabels){
            l.setTextFill(Color.web(text));
        }
        for(JFXToggleButton t : dayToggles){
            t.setToggleColor(Color.web(ac1));
            t.setToggleLineColor(Color.web(ac2));
            t.setUnToggleColor(Color.web(fg1));
            t.setUnToggleLineColor(Color.web(fg2));
        }
        //day context menu
        dayContextMenu.updateColor();
        //timeMenu
        timeMenu.updateColor();
    }

    public void cancelMenus() {
        for (Hideable h : menus) {
            h.cancel();
        }
    }

    public void hideOtherMenus(Hideable... hidables) {
        for (Hideable h1 : menus) {
            boolean hide = true;
            for (Hideable h2 : hidables) {
                if (h1.equals(h2)) {
                    hide = false;
                    break;
                }
            }
            if (hide) {
                h1.hide();
            }
        }
    }

    public void hideAllMenus() {
        for (Hideable h : menus) {
            h.hide();
        }
    }

    //
    //################################menu################################
    //
    public void selectTabA() {
        hideAllMenus();
        tm.setIsA(true);
        tabB.getStyleClass().removeIf(s -> (s == "selectedRightTabButton"));
        tabB.setStyle("-fx-background-color:" + bg4);
        tabA.getStyleClass().add("selectedLeftTabButton");
        tabA.setStyle("-fx-background-color:" + bg1);
        initNewTimetable();
        new Timeline(
                new KeyFrame(Duration.millis(2), n -> resizeFonts())
        ).play();
    }

    public void selectTabB() {
        hideAllMenus();
        tm.setIsA(false);
        tabA.getStyleClass().removeIf(s -> (s == "selectedLeftTabButton"));
        tabA.setStyle("-fx-background-color:" + bg4);
        tabB.getStyleClass().add("selectedRightTabButton");
        tabB.setStyle("-fx-background-color:" + bg1);
        initNewTimetable();
        new Timeline(
                new KeyFrame(Duration.millis(2), n -> resizeFonts())
        ).play();
    }

    public void switchTab() {
        if (tm.IsA()) {
            selectTabB();
        } else {
            selectTabA();
        }
    }

    //
    //################################menu################################
    //
    public void menu() {
        menu.show(name.getButton());
    }

    public void scaleMenu() {
        double h = times[0].getHeight();
        menu.getPane().setMargin(menuName, new Insets(0, 0, h * 0.1, 0));
        menuName.setPadding(new Insets(h * 0.4, h * 0.4, 0, h * 0.4));
        menuName.setFont(new Font(h * FONT_FACTOR));
        settings.setFont(new Font(h * FONT_FACTOR));
        addTimetable.setFont(new Font(h * FONT_FACTOR));
        deleteTimetable.setFont(new Font(h * FONT_FACTOR));
        menu.getDone().setFont(new Font(h * FONT_FACTOR));
        timetablePane.scale(h, menu.getPane().getPrefWidth());
    }

    public void updateMenuData() {
        menuName.setText(tm.getCurrentTablePair().getName());
    }

    public void writeMenuData() {
        tm.getCurrentTablePair().setName(menuName.getText());
        name.setText(menuName.getText());
    }

    public void updateMenuTimetables() {
        timetablePane.update(tm.getTimetablePairs(), timetableButtonPressed, tm.getTimeTableIndex());
    }

    public void timetableButtonPressed(ActionEvent event) {
        int size = timetablePane.getPane().getChildren().size();
        for (int i = 0; i < size; i++) {
            if (event.getSource().equals(timetablePane.getPane().getChildren().get(i))) {
                writeMenuData();
                tm.setCurrentTablePair(i);
                initNewTimetable();
                updateMenuData();
                updateMenuTimetables();
                scaleMenu();
            }
        }
    }

    public void addTimetable() {
        tm.addTimetablePair();
        updateMenuTimetables();
        scaleMenu();
        initNewTimetable();
        updateMenuData();
    }

    public void deleteTimetable() {
        tm.deleteCurrentTimetablePair();
        updateMenuTimetables();
        scaleMenu();
        initNewTimetable();
        updateMenuData();
    }

    //
    //################################dayMenu################################
    //
    public void dayMenu(ActionEvent event) {
        getSelectedDay(event);
        dayMenu.show(selectedDay);
        hideOtherMenus(dayMenu);
    }

    public void scaleDayMenu() {
        double h = selectedDay.getHeight();
        for (int i = 0; i < dayToggles.length; i++) {
            dayToggles[i].setSelected(tm.getCurrentTable().isDayDisplayed(i));
            dayToggles[i].setScaleX(h * 0.012);
            dayToggles[i].setScaleY(h * 0.012);
            dayToggles[i].autosize();
            dayToggles[i].setPadding(new Insets(0, h * 0.2, 0, 0));
            dayToggles[i].setMinHeight(h * dayMenu.getHeightFactor());
            dayToggles[i].setPrefHeight(h * dayMenu.getHeightFactor());
            dayToggles[i].setMaxHeight(h * dayMenu.getHeightFactor());
            dayLabels[i].setFont(new Font(h * FONT_FACTOR));
            dayPanes[i].setPadding(new Insets(0, h * 0.1, 0, h * 0.4));
        }
        dayMenu.getPane().setPadding(new Insets(h * 0.2, 0, 0, 0));
        dayMenu.getDone().setPadding(new Insets(h * 0.15, 0, h * 0.15, 0));
        dayMenu.getDone().setFont(new Font(h * FONT_FACTOR));
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
        new Timeline(
                new KeyFrame(Duration.millis(1), n -> resizeFonts())
        ).play();
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

    //
    //################################dayContextMenu################################
    //
    public void dayPressed(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedDay(event);
            dayContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedDay);
            hideOtherMenus(dayContextMenu);
        }
    }

    public void dayKeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            getSelectedDay(event);
            dayContextMenu.show(selectedDay);
            hideOtherMenus(dayContextMenu);
        }
    }

    public void clearColumn() {
        tm.clearColumn();
        initNewTimetable();
    }

    public void deleteColumn() {
        tm.deleteColumn();
        initNewTimetable();
    }

    private void getSelectedDay(Event event) {
        for (int i = 0; i < days.length; i++) {
            if (days[i] == event.getSource()) {
                selectedDay = days[i];
                tm.setdIndexI(i);
                break;
            }
        }
    }

    //
    //################################timeMenu################################
    //
    public void timeMenu(ActionEvent event) {
        getSelectedTime(event);
        timeMenu.show(selectedTime, tm.getCurrentTable().getTime(tm.gettIndexI()));
        hideOtherMenus(timeMenu);
    }

    //
    //################################timeContextMenu################################
    //
    public void timePressed(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedTime(event);
            timeContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedTime);
            hideOtherMenus(timeContextMenu);
        }
    }

    public void timeKeyReleased(KeyEvent event) {
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
    public void subjectMenu(ActionEvent event) {
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
        subjectName.setFont(new Font(h * FONT_FACTOR));
        subjectRoom.setFont(new Font(h * FONT_FACTOR));
        subjectTeacher.setFont(new Font(h * FONT_FACTOR));
        subjectMenu.getDone().setFont(new Font(h * FONT_FACTOR));
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
        for (int i = 0; i < tm.getCurrentTablePair().getOptions().size(); i++) {
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
            tm.getCurrentTablePair().updateReferences();
            List<Subject> options = tm.getCurrentTablePair().getAutocompleteOptions(subjectName.getText());
            if (options.size() > 0) {
                autoCompletePane.setFields(subjectName, options);
                autoCompletePane.show(subjectName);
            } else {
                autoCompletePane.hide();
            }
        }
    }

    private void writeAutocomplete() {
        setSubjectMenuFields(tm.getCurrentTablePair().getOption(autoCompletePane.getAutocompleteIndex()));
        autoCompletePane.hide();
    }

    private void writeAutocomplete(int index) {
        setSubjectMenuFields(tm.getCurrentTablePair().getOption(index));
        autoCompletePane.hide();
    }

    //
    //################################subjectContextMenu################################
    //
    public void subjectPressed(MouseEvent event) {
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
    public void subjectKeyReleased(KeyEvent event) {
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
        for (int d = 0; d < tm.getCurrentTable().getDays().length; d++) {
            if (tm.getCurrentTable().isDayDisplayed(d)) {
                days++;
            }
        }
        if (i < days) {
            subjects[i + 1][j].requestFocus();
        }
    }

    public Parent getParent() {
        return bg;
    }
}
