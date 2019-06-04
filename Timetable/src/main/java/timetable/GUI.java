package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import timetable.Datatypes.Subject;
import timetable.Datatypes.Timetable;

/**
 *
 * @author Tobias
 */
public class GUI {

    //constants for animation and scaling of the application
    static final int ANIMATION_DURATION = 300;
    static final int ANIMATION_DISTANCE = 50;
    static final double FOCUS_ANIMATION_OFFSET_FACTOR = 0.6;
    static final double GAP_SIZE = 2;
    static final double FONT_FACTOR = 0.22;

    //RGB HEX strings for light theme
    static final String lightfg1 = "#FFFFFF";
    static final String lightfg2 = "#888888";
    static final String lightbg1 = "#EEEEEE";
    static final String lightbg2 = "#E8E8E8";
    static final String lightbg3 = "#DDDDDD";
    static final String lightbg4 = "#CCCCCC";
    static final String lightrpf = "#000000";
    static final String lighttext = "#292929";
    static final String lighttransparent = "#00000000";
    static final String lightsemiTransparent = "#00000055";

    //RGB HEX strings for dark theme
    static final String darkfg1 = "#BBBBBB";
    static final String darkfg2 = "#555555";
    static final String darkbg1 = "#111111";
    static final String darkbg2 = "#181818";
    static final String darkbg3 = "#222222";
    static final String darkbg4 = "#333333";
    static final String darkrpf = "#FFFFFF";
    static final String darktext = "#DDDDDD";
    static final String darktransparent = "#00000000";
    static final String darksemiTransparent = "#BBBBBB55";

    //RGB HEX strings for default accent colors
    static final String[] ac1s = {"#66CC55", "#EE9933", "#DD3344", "#5599DD"};
    static final String[] ac2s = {"#55BB44", "#EE8822", "#C42233", "#4488CC"};

    //
    static final String[] ENGLISH_DAY_NAMES = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    static final String[] GERMAN_DAY_NAMES = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};

    //RGB HEX strings for current theme
    static String ac1 = ac1s[0];
    static String ac2 = ac2s[0];
    static String[] customAcs = {"#888888", "#888888", "#888888", "#888888"};
    static String fg1 = lightfg1;
    static String fg2 = lightfg2;
    static String bg1 = lightbg1;
    static String bg2 = lightbg2;
    static String bg3 = lightbg3;
    static String bg4 = lightbg4;
    static String rpf = lightrpf;
    static String text = lighttext;
    static String transparent = lighttransparent;
    static String semiTransparent = lightsemiTransparent;
    //global variables indicating current theme
    static boolean darkMode = false;
    static boolean customColor = false;
    static int colorIndex = 0;

    static String[] dayNames = ENGLISH_DAY_NAMES;

    TimetableManager tm;

    AnchorPane bg;
    GridPane subjectGrid;

    EventHandler<ActionEvent> nameAction;
    EventHandler<MouseEvent> namePressed;
    EventHandler<KeyEvent> nameKeyReleased;

    EventHandler<ActionEvent> dayAction;
    EventHandler<MouseEvent> dayPressed;
    EventHandler<KeyEvent> dayKeyReleased;

    EventHandler<ActionEvent> timeAction;
    EventHandler<MouseEvent> timePressed;
    EventHandler<KeyEvent> timeKeyReleased;

    EventHandler<ActionEvent> subjectAction;
    EventHandler<MouseEvent> subjectPressed;
    EventHandler<MouseEvent> subjectDragged;
    EventHandler<MouseEvent> subjectReleased;
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

    //list of menus that implement the hidable interface
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

    //settingsMenu
    Menu settingsMenu;

    //context menu
    OptionsPane contextMenu;
    JFXButton clearTimetable;
    JFXButton duplicateA;
    JFXButton duplicateB;

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
    JFXButton copy;
    JFXButton cut;
    JFXButton paste;
    JFXButton clear;
    JFXButton delete;
    JFXButton addAbove;
    JFXButton addBelow;

    //subject preview
    Label subjectPreview;
    boolean firstDrag = true;
    boolean primaryButton;
    double subjectStartX;
    double subjectStartY;
    double subjectInnerX;
    double subjectInnerY;

    boolean controlDown = false;

    public GUI(JSONObject data) {

        //opening application data
        openData(data);
        if (darkMode) {
            setDarkColors();
        } else {
            setLightColors();
        }

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

        initControlArrays();

        tm = new TimetableManager(data);

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
            hideAllMenus();
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
        menuName.setPromptText("Name");
        menuName.getStyleClass().add("customTextfield");
        menuName.setOnKeyReleased(writeMenuData);
        menuName.addEventFilter(KeyEvent.ANY, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.H) {
                event.consume();
                hideAllMenus();
            }
        });
        settings = new JFXButton("Settings");
        settings.getStyleClass().add("notRoundedButton");
        settings.setPrefWidth(500);
        settings.setPrefHeight(150);
        settings.setOnAction(event -> {
            if (settingsMenu.isHidden()) {
                settingsMenu();
            } else {
                settingsMenu.hide();
            }
        });
        addTimetable = new JFXButton("Add Timetable");
        addTimetable.getStyleClass().add("notRoundedButton");
        addTimetable.setPrefWidth(500);
        addTimetable.setPrefHeight(150);
        addTimetable.setOnAction(event -> {
            addTimetable();
        });
        deleteTimetable = new JFXButton("Delete Timetable");
        deleteTimetable.getStyleClass().add("notRoundedButton");
        deleteTimetable.setPrefWidth(500);
        deleteTimetable.setPrefHeight(150);
        deleteTimetable.setOnAction(event -> {
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
        timetablePane.getPane().setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 1;
            double width = menuScrollPane.getContent().getBoundsInLocal().getWidth();
            double vvalue = menuScrollPane.getVvalue();
            menuScrollPane.setVvalue(vvalue + -deltaY / width);
        });
        menuScrollPane.setContent(timetablePane.getPane());
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(60);
        menu.getPane().getRowConstraints().addAll(new RowConstraints(), new RowConstraints(), new RowConstraints(), new RowConstraints(), rc);
        menu.add(menuName);
        menu.add(settings);
        menu.add(addTimetable);
        menu.add(deleteTimetable);
        menu.add(menuScrollPane);

        //settings menu
        settingsMenu = new Menu(this);
        menus.add(settingsMenu);
        menuBackgroundPane.getChildren().add(settingsMenu.getPane());

        //context menu
        contextMenu = new OptionsPane(bg);
        menus.add(contextMenu);
        clearTimetable = new JFXButton("Clear Timetable");
        clearTimetable.setOnAction(event -> {
            if (tm.IsA()) {
                tm.getCurrentTablePair().setA(new Timetable(tm.getCurrentTablePair()));
            } else {
                tm.getCurrentTablePair().setB(new Timetable(tm.getCurrentTablePair()));
            }
            initNewTimetable();
        });
        duplicateA = new JFXButton("Duplicate A");
        duplicateA.setOnAction(event -> {
            tm.getCurrentTablePair().duplicateA();
            initNewTimetable();
        });
        duplicateB = new JFXButton("Duplicate B");
        duplicateB.setOnAction(event -> {
            tm.getCurrentTablePair().duplicateB();
            initNewTimetable();
            resize();
        });
        contextMenu.addButton(clearTimetable);
        contextMenu.addButton(duplicateA);
        contextMenu.addButton(duplicateB);

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
        dayMenu.setHeightFactor(0.6);
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
        clearColumn = new JFXButton("Clear Column");
        clearColumn.setOnAction(event -> {
            clearColumn();
        });
        deleteColumn = new JFXButton("Delete Column");
        deleteColumn.setOnAction(event -> {
            deleteColumn();
        });
        dayContextMenu.addButton(clearColumn);
        dayContextMenu.addButton(deleteColumn);

        //time menu
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
        clearRow = new JFXButton("Clear Row");
        clearRow.setOnAction(event -> {
            clearRow();
        });
        deleteRow = new JFXButton("Delete Row");
        deleteRow.setOnAction(event -> {
            deleteRow();
        });
        addRowAbove = new JFXButton("Add Row Above");
        addRowAbove.setOnAction(event -> {
            addRowAbove();
        });
        addRowBelow = new JFXButton("Add Row Below");
        addRowBelow.setOnAction(event -> {
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
            hideOtherMenus(subjectMenu);
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
        subjectName.setPrefWidth(500);
        subjectName.setPrefHeight(100);
        subjectName.getStyleClass().add("customTextfield");
        subjectName.setPromptText("Subject");
        subjectName.addEventFilter(KeyEvent.ANY, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.H) {
                event.consume();
                hideAllMenus();
            }
        });
        subjectRoom = new JFXTextField();
        subjectRoom.setOnKeyReleased(writeSubjectData);
        subjectRoom.setPrefWidth(500);
        subjectRoom.setPrefHeight(100);
        subjectRoom.getStyleClass().add("customTextfield");
        subjectRoom.setPromptText("Room");
        subjectRoom.addEventFilter(KeyEvent.ANY, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.H) {
                event.consume();
                hideAllMenus();
            }
        });
        subjectTeacher = new JFXTextField();
        subjectTeacher.setOnKeyReleased(writeSubjectData);
        subjectTeacher.setPrefWidth(500);
        subjectTeacher.setPrefHeight(100);
        subjectTeacher.getStyleClass().add("customTextfield");
        subjectTeacher.setPromptText("Teacher");
        subjectTeacher.addEventFilter(KeyEvent.ANY, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.H) {
                event.consume();
                hideAllMenus();
            }
        });
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
        menus.add(autoCompletePane);
        subjectName.setOnKeyReleased(subjectMenuKeyPressed);
        autoCompletePane.setOnClick(autocompleteOnClick);

        //subject context menu
        subjectContextMenu = new OptionsPane(bg);
        menus.add(subjectContextMenu);
        copy = new JFXButton("Copy");
        copy.setOnAction(event -> {
            copy(event);
        });
        cut = new JFXButton("Cut");
        cut.setOnAction(event -> {
            cut(event);
        });
        paste = new JFXButton("Paste");
        paste.setOnAction(event -> {
            paste(event);
        });
        clear = new JFXButton("Clear");
        clear.setOnAction(event -> {
            clear();
        });
        delete = new JFXButton("Delete");
        delete.setOnAction(event -> {
            delete();
        });
        addAbove = new JFXButton("Add Above");
        addAbove.setOnAction(event -> {
            addAbove();
        });
        addBelow = new JFXButton("Add Below");
        addBelow.setOnAction(event -> {
            addBelow();
        });
        subjectContextMenu.addButton(copy);
        subjectContextMenu.addButton(cut);
        subjectContextMenu.addButton(paste);
        subjectContextMenu.addButton(clear);
        subjectContextMenu.addButton(delete);
        subjectContextMenu.addButton(addAbove);
        subjectContextMenu.addButton(addBelow);
        subjectName.focusedProperty().addListener(event -> {
            if (!subjectName.isFocused()) {
                autoCompletePane.hide();
            }
        });

        //subject preview
        subjectPreview = new Label();
        subjectPreview.getStyleClass().add("roundedButton");
        subjectPreview.setAlignment(Pos.CENTER);
        subjectPreview.prefWidthProperty().bind(subjects[0][0].widthProperty());
        subjectPreview.prefHeightProperty().bind(subjects[0][0].heightProperty());
        subjectPreview.setVisible(false);
        bg.getChildren().add(subjectPreview);

        Locale l = Locale.getDefault();
        if (LocalDate.now().get(WeekFields.of(l).weekOfWeekBasedYear()) % 2 == 0) {
            selectTabA();
        } else {
            selectTabB();
        }

        updateColors();

        resize();
    }

    static final String COLOR_INDEX = "colorIndex";
    static final String CUSTOM_COLOR = "customColor";
    static final String CUSTOM_COLORS = "customColors";
    static final String AC_1 = "ac1";
    static final String AC_2 = "ac2";
    static final String DARK_MODE = "darkMode";

    public JSONObject getDataToSave() {
        JSONObject data = new JSONObject();

        data.put(COLOR_INDEX, colorIndex);
        data.put(CUSTOM_COLOR, customColor);
        JSONArray colors = new JSONArray();
        for (String s : customAcs) {
            colors.add(s);
        }
        data.put(CUSTOM_COLORS, colors);
        data.put(AC_1, ac1);
        data.put(AC_2, ac2);
        data.put(DARK_MODE, darkMode);

        return data;
    }

    public void openData(JSONObject data) {
        if (data != null) {
            try {
                JSONObject jo = (JSONObject) data.get(Main.GUI);
                try {
                    colorIndex = (int) (long) jo.get(COLOR_INDEX);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    customColor = (boolean) jo.get(CUSTOM_COLOR);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray colors = (JSONArray) jo.get(CUSTOM_COLORS);
                    Iterator<Object> it = colors.iterator();
                    int index = 0;
                    while (it.hasNext()) {
                        customAcs[index] = (String) it.next();
                        index++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    ac1 = (String) jo.get(AC_1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    ac2 = (String) jo.get(AC_2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    darkMode = (boolean) jo.get(DARK_MODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initControlArrays() {

        //name
        nameAction = (ActionEvent event) -> {
            menu();
        };
        namePressed = (MouseEvent event) -> {
            namePressed(event);
        };
        nameKeyReleased = (KeyEvent event) -> {
            nameKeyReleased(event);
        };

        name = new MenuButton();
        name.getStyleClass().add("menuButton");
        name.setMinSize(100, 40);
        name.setPrefSize(500, 500);
        name.getButton().setOnAction(nameAction);
        name.getButton().setOnMousePressed(namePressed);
        name.getButton().setOnKeyReleased(nameKeyReleased);
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
            day.getStyleClass().add("lightRoundedShadowedButton");
            day.setMinSize(100, 40);
            day.setPrefSize(500, 500);
            day.addEventHandler(ActionEvent.ANY, dayAction);
            day.setOnMousePressed(dayPressed);
            day.setOnKeyReleased(dayKeyReleased);
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
            time.getStyleClass().add("roundedButton");
            time.setMinSize(100, 40);
            time.setPrefSize(500, 500);
            time.addEventHandler(ActionEvent.ANY, timeAction);
            time.setOnMousePressed(timePressed);
            time.setOnKeyReleased(timeKeyReleased);
            times[i] = time;
        }

        //subjects
        subjectAction = (ActionEvent event) -> {
            subjectMenu(event);
        };
        subjectPressed = (MouseEvent event) -> {
            subjectPressed(event);
        };
        subjectDragged = (MouseEvent event) -> {
            subjectDragged(event);
        };
        subjectReleased = (MouseEvent event) -> {
            subjectReleased(event);
        };
        subjectKeyReleased = (KeyEvent event) -> {
            subjectKeyReleased(event);
        };
        subjects = new JFXButton[7][10];
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                JFXButton subject = new JFXButton();
                subject.getStyleClass().add("roundedButton");
                subject.setMinSize(100, 40);
                subject.setPrefSize(500, 500);
                subject.setOnAction(subjectAction);
                subject.setOnMousePressed(subjectPressed);
                subject.setOnMouseDragged(subjectDragged);
                subject.setOnMouseReleased(subjectReleased);
                subject.setOnKeyReleased(subjectKeyReleased);
                subjects[i][j] = subject;
            }
        }
    }

    public void resize() {
        new Timeline(
                new KeyFrame(Duration.millis(5), event -> resizeFonts())
        ).play();
    }

    public void resizeFonts() {
        double w = times[0].getWidth();
        double h = times[0].getHeight();

        Font font1 = new Font((h + w) * 0.09);
        Font font2 = new Font(h * 0.3);
        Font font3 = new Font(h * 0.25);

        name.setFont(font1);

        tabA.setFont(font3);
        tabB.setFont(font3);

        for (JFXButton b : days) {
            b.setFont(font1);
        }
        for (JFXButton b : times) {
            b.setFont(font2);
        }
        for (JFXButton[] ba : subjects) {
            for (JFXButton b : ba) {
                b.setFont(font3);
            }
        }

        subjectPreview.setFont(font3);
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
                    int height = 1;
                    subjectGrid.add(subjects[i][j], pos + 1, j + 2, 1, 1);
                    subjects[i][j].setText(
                            tm.getCurrentTable().getSubjectText(i, j)
                            + "\n" + tm.getCurrentTable().getRoomText(i, j)
                    );
                    j += height - 1;
                }
            }
            if (tm.getCurrentTable().isDayDisplayed(i)) {
                pos++;
            }
        }

        resize();
    }

    //
    //################################colors################################
    //
    public void updateColors() {

        settingsMenu.updateColors();

        //base controls
        bg.setStyle("-fx-background-color:" + bg1);
        name.updateColor();
        tabBox.setStyle("-fx-background-color:" + bg3);
        if (tm.IsA()) {
            tabB.setStyle("-fx-background-color:" + bg3);
            tabA.setStyle("-fx-background-color:" + bg1);
        } else {
            tabA.setStyle("-fx-background-color:" + bg3);
            tabB.setStyle("-fx-background-color:" + bg1);
        }
        tabA.setTextFill(Color.web(text));
        tabB.setTextFill(Color.web(text));

        for (JFXButton day : days) {
            day.setStyle("-fx-background-color:" + bg4);
            day.setTextFill(Color.web(text));
            day.setRipplerFill(Color.web(rpf));
            if (darkMode) {
                day.getStyleClass().removeIf(s -> (s == "lightRoundedShadowedButton"));
                day.getStyleClass().add("darkRoundedShadowedButton");
            } else {
                day.getStyleClass().removeIf(s -> (s == "darkRoundedShadowedButton"));
                day.getStyleClass().add("lightRoundedShadowedButton");
            }
        }
        for (JFXButton time : times) {
            time.setStyle("-fx-background-color:" + bg3);
            time.setTextFill(Color.web(text));
            time.setRipplerFill(Color.web(rpf));
        }
        for (JFXButton[] subjectA : subjects) {
            for (JFXButton subject : subjectA) {
                subject.setStyle("-fx-background-color:" + bg2);
                subject.setTextFill(Color.web(text));
                subject.setRipplerFill(Color.web(ac1));
            }
        }
        //menu
        menu.updateBaseColor();
        menuBackgroundPane.updateColor();
        menuName.setFocusColor(Color.web(ac1));
        menuName.setUnFocusColor(Color.web(text));
        menuName.setStyle("-fx-prompt-text-fill:" + text);
        menuName.setStyle("-fx-text-inner-color:" + text);
        settings.setRipplerFill(Color.web(ac1));
        settings.setTextFill(Color.web(text));
        addTimetable.setRipplerFill(Color.web(ac1));
        addTimetable.setTextFill(Color.web(text));
        deleteTimetable.setRipplerFill(Color.web(ac1));
        deleteTimetable.setTextFill(Color.web(text));

        timetablePane.updateColor();
        //context menu
        contextMenu.updateColor();
        //day menu
        dayMenu.updateBaseColor();
        for (Label l : dayLabels) {
            l.setTextFill(Color.web(text));
        }
        for (JFXToggleButton t : dayToggles) {
            t.setToggleColor(Color.web(ac1));
            t.setToggleLineColor(Color.web(ac2));
            t.setUnToggleColor(Color.web(fg1));
            t.setUnToggleLineColor(Color.web(fg2));
        }
        //day context menu
        dayContextMenu.updateColor();
        //timeMenu
        timeMenu.updateColor();
        //time context menu
        timeContextMenu.updateColor();
        //subject menu
        subjectMenu.updateBaseColor();
        subjectName.setFocusColor(Color.web(ac1));
        subjectName.setUnFocusColor(Color.web(text));
        subjectName.setStyle("-fx-prompt-text-fill:" + text);
        subjectName.setStyle("-fx-text-inner-color:" + text);
        subjectRoom.setFocusColor(Color.web(ac1));
        subjectRoom.setUnFocusColor(Color.web(text));
        subjectRoom.setStyle("-fx-prompt-text-fill:" + text);
        subjectRoom.setStyle("-fx-text-inner-color:" + text);
        subjectTeacher.setFocusColor(Color.web(ac1));
        subjectTeacher.setUnFocusColor(Color.web(text));
        subjectTeacher.setStyle("-fx-prompt-text-fill:" + text);
        subjectTeacher.setStyle("-fx-text-inner-color:" + text);
        //autocomplete pane        
        autoCompletePane.updateColor();
        //subject context menu
        subjectContextMenu.updateColor();
        //subject preview
        subjectPreview.setStyle("-fx-background-color:" + bg1 + 44);
        subjectPreview.setTextFill(Color.web(text));
    }

    public void setAccentColor(int index) {
        colorIndex = index;
        customColor = false;
        ac1 = ac1s[index];
        ac2 = ac2s[index];
    }

    public void setCustomAccentColor(int index) {
        colorIndex = index;
        customColor = true;
        ac1 = customAcs[index];
        Color ac = Color.web(ac1);
        ac.darker();
        ac.darker();
        ac2 = toRGBCode(ac);
    }

    public static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }

    public void toggleColorMode() {
        if (darkMode) {
            setLightColors();
        } else {
            setDarkColors();
        }
    }

    public void setLightColors() {
        fg1 = lightfg1;
        fg2 = lightfg2;
        bg1 = lightbg1;
        bg2 = lightbg2;
        bg3 = lightbg3;
        bg4 = lightbg4;
        rpf = lightrpf;
        text = lighttext;
        transparent = lighttransparent;
        semiTransparent = lightsemiTransparent;
        darkMode = false;
    }

    public void setDarkColors() {
        fg1 = darkfg1;
        fg2 = darkfg2;
        bg1 = darkbg1;
        bg2 = darkbg2;
        bg3 = darkbg3;
        bg4 = darkbg4;
        rpf = darkrpf;
        text = darktext;
        transparent = darktransparent;
        semiTransparent = darksemiTransparent;
        darkMode = true;
    }

    //
    //################################menus################################
    //
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
    //################################tabs################################
    //
    public void selectTabA() {
        hideAllMenus();
        tm.setIsA(true);
        tabB.getStyleClass().removeIf(s -> (s == "selectedRightTabButton"));
        tabB.setStyle("-fx-background-color:" + bg3);
        tabA.getStyleClass().add("selectedLeftTabButton");
        tabA.setStyle("-fx-background-color:" + bg1);
        initNewTimetable();
    }

    public void selectTabB() {
        hideAllMenus();
        tm.setIsA(false);
        tabA.getStyleClass().removeIf(s -> (s == "selectedLeftTabButton"));
        tabA.setStyle("-fx-background-color:" + bg3);
        tabB.getStyleClass().add("selectedRightTabButton");
        tabB.setStyle("-fx-background-color:" + bg1);
        initNewTimetable();
    }

    public void switchTab() {
        if (tm.IsA()) {
            selectTabB();
        } else {
            selectTabA();
        }
    }

    //
    //################################settingsMenu################################
    //
    public void settingsMenu() {
        settingsMenu.show(
                menu.getPane().getWidth() + 2,
                0,
                bg.getWidth() - menu.getPane().getWidth() - 2,
                bg.getHeight()
        );
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
    //################################contextMenu################################
    //
    public void namePressed(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            contextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), times[0]);
            hideOtherMenus(contextMenu);
        }
    }

    public void nameKeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            contextMenu.showOnCoordinates(name.getLayoutX(), name.getLayoutY(), times[0]);
            hideOtherMenus(contextMenu);
        }
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
    private void clearColumn() {
        tm.clearColumn();
        initNewTimetable();
    }

    private void deleteColumn() {
        tm.deleteColumn();
        initNewTimetable();
    }

    //
    //################################days################################
    //
    private void dayPressed(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedDay(event);
            dayContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedDay);
            hideOtherMenus(dayContextMenu);
        }
    }

    private void dayKeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            getSelectedDay(event);
            dayContextMenu.show(selectedDay);
            hideOtherMenus(dayContextMenu);
        }
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
    private void timeMenu(ActionEvent event) {
        getSelectedTime(event);
        timeMenu.show(selectedTime, tm.getCurrentTable().getTime(tm.gettIndexI()));
        hideOtherMenus(timeMenu);
    }

    //
    //################################timeContextMenu################################
    //
    private void clearRow() {
        tm.clearRow();
        initNewTimetable();
    }

    private void deleteRow() {
        tm.deleteRow();
        initNewTimetable();
    }

    private void addRowAbove() {
        tm.addRowAbove();
        initNewTimetable();
    }

    private void addRowBelow() {
        tm.addRowBelow();
        initNewTimetable();
    }

    //
    //################################times################################
    //
    private void timePressed(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            getSelectedTime(event);
            timeContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedTime);
            hideOtherMenus(timeContextMenu);
        }
    }

    private void timeKeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.SPACE || event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            getSelectedTime(event);
            timeContextMenu.show(selectedTime);
            hideOtherMenus(timeContextMenu);

        }
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
    private void subjectMenu(ActionEvent event) {
        getSelectedSubject(event);
        subjectMenu.show(selectedSubject);
    }

    private void scaleSubjectMenu() {
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

    private void updateSubjectMenuData() {
        subjectName.setText(tm.getCurrentTable().getSubjectText(tm.getsIndexI(), tm.getsIndexJ()));
        subjectRoom.setText(tm.getCurrentTable().getRoomText(tm.getsIndexI(), tm.getsIndexJ()));
        subjectTeacher.setText(tm.getCurrentTable().getTeacherText(tm.getsIndexI(), tm.getsIndexJ()));
    }

    private void setSubjectMenuFields(Subject s) {
        subjectName.setText(s.getSubject());
        subjectRoom.setText(s.getRoom());
        subjectTeacher.setText(s.getTeacher());
    }

    private void writeSubjectData() {
        selectedSubject.setText(subjectName.getText() + "\n" + subjectRoom.getText());
        tm.getCurrentTable().setSubjectText(subjectName.getText(), tm.getsIndexI(), tm.getsIndexJ());
        tm.getCurrentTable().setRoomText(subjectRoom.getText(), tm.getsIndexI(), tm.getsIndexJ());
        tm.getCurrentTable().setTeacherText(subjectTeacher.getText(), tm.getsIndexI(), tm.getsIndexJ());
    }

    //
    //################################autoCompletePane################################
    //
    private void autocompleteLabelClicked(MouseEvent event) {
        for (int i = 0; i < autoCompletePane.getPane().getChildren().size(); i++) {
            if (event.getSource() == autoCompletePane.getPane().getChildren().get(i)) {
                writeAutocomplete(i / 3);
            }
        }
    }

    private void autocompleteSubject(KeyEvent event) {
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
            List<Subject> options = tm.getCurrentTablePair().getSubjectAutocompleteOptions(subjectName.getText());
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

    //
    //################################subjects################################
    //
    private void subjectPressed(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            primaryButton = false;
            getSelectedSubject(event);
            subjectContextMenu.showOnCoordinates(event.getSceneX(), event.getSceneY(), selectedSubject);
            hideOtherMenus(subjectContextMenu);
        } else {
            primaryButton = true;
            subjectStartX = event.getSceneX();
            subjectStartY = event.getSceneY();
            subjectInnerX = event.getX();
            subjectInnerY = event.getY();
        }
    }

    private void subjectDragged(MouseEvent event) {
        if (primaryButton) {
            if (firstDrag) {
                hideAllMenus();
                for (int i = 0; i < subjects.length; i++) {
                    for (int j = 0; j < subjects[0].length; j++) {
                        if (subjects[i][j] == event.getSource()) {
                            subjectPreview.setText(
                                    tm.getCurrentTable().getSubjectText(i, j)
                                    + "\n" + tm.getCurrentTable().getRoomText(i, j)
                            );
                            break;
                        }
                    }
                }
                firstDrag = false;
            }
            subjectPreview.setVisible(true);
            subjectPreview.setLayoutX(event.getSceneX() - subjectInnerX);
            subjectPreview.setLayoutY(event.getSceneY() - subjectInnerY);
        }
    }

    private void subjectReleased(MouseEvent event) {
        if (primaryButton) {
            boolean onSubject = false;
            int is1 = 0;
            int js1 = 0;
            int is2 = 0;
            int js2 = 0;

            for (int i = 0; i < subjects.length; i++) {
                for (int j = 0; j < subjects[0].length; j++) {
                    if (subjects[i][j] == event.getSource()) {
                        is1 = i;
                        js1 = j;
                        break;
                    }
                }
            }
            for (int i = 0; i < subjects.length; i++) {
                for (int j = 0; j < subjects[0].length; j++) {
                    if (subjects[i][j].getLayoutX() < event.getSceneX()
                            && subjects[i][j].getLayoutX() + subjects[i][j].getWidth() > event.getSceneX()
                            && subjects[i][j].getLayoutY() < event.getSceneY()
                            && subjects[i][j].getLayoutY() + subjects[i][j].getHeight() > event.getSceneY()) {
                        if (subjects[i][j] == event.getSource()) {
                            getSelectedSubject(event);
                            subjectMenu.hide.stop();
                            subjectMenu.show(selectedSubject);
                        } else if (tm.getCurrentTable().isDayDisplayed(i)) {
                            is2 = i;
                            js2 = j;
                            onSubject = true;
                            break;
                        }
                    }
                }
            }
            if (onSubject) {
                if (controlDown) {
                    tm.copy(is1, js1);
                    tm.paste(is2, js2);
                } else {
                    tm.getCurrentTable().switchSubjects(is1, js1, is2, js2);
                }
                initNewTimetable();
            }
            subjectPreview.setVisible(false);
            firstDrag = true;
        }
    }

    private void subjectKeyReleased(KeyEvent event) {
        getSelectedSubject(event);
        if (event.isControlDown()) {
            if (event.isShiftDown()) {
                if (event.getCode() == KeyCode.PLUS) {
                    tm.addSubjectAbove();
                    initNewTimetable();
                }
            } else {
                if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                    subjectContextMenu.show(selectedSubject);
                } else if (event.getCode() == KeyCode.UP) {
                    moveSubjectUp(tm.getsIndexI(), tm.getsIndexJ());
                } else if (event.getCode() == KeyCode.DOWN) {
                    moveSubjectDown(tm.getsIndexI(), tm.getsIndexJ());
                } else if (event.getCode() == KeyCode.LEFT) {
                    moveSubjectLeft(tm.getsIndexI(), tm.getsIndexJ());
                } else if (event.getCode() == KeyCode.RIGHT) {
                    moveSubjectRight(tm.getsIndexI(), tm.getsIndexJ());
                } else if (event.getCode() == KeyCode.C) {
                    copy(event);
                } else if (event.getCode() == KeyCode.X) {
                    cut(event);
                } else if (event.getCode() == KeyCode.V) {
                    paste(event);
                } else if (event.getCode() == KeyCode.E) {
                    tm.clearSubject();
                    initNewTimetable();
                } else if (event.getCode() == KeyCode.R) {
                    tm.deleteSubject();
                    initNewTimetable();
                } else if (event.getCode() == KeyCode.PLUS) {
                    tm.addSubjectBelow();
                    initNewTimetable();
                }
            }
        } else if (event.getCode() == KeyCode.DELETE) {
            tm.deleteSubject();
            initNewTimetable();
        }
    }

    /**
     * copies the currently focused subject to clipboard
     *
     * @param event
     */
    private void copy(Event event) {
        getSelectedSubject(event);
        tm.copyCurrentClipboard();
        initNewTimetable();
    }

    /**
     * cuts the currently focused subject to clipboard
     *
     * @param event
     */
    private void cut(Event event) {
        getSelectedSubject(event);
        tm.copyCurrentClipboard();
        tm.clearSubject();
        initNewTimetable();
    }

    /**
     * pastes the clipboard to the currently focused subject
     *
     * @param event
     */
    private void paste(Event event) {
        getSelectedSubject(event);
        tm.pasteCurrentClipboard();
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

    /**
     * moves the subject at the indexes up
     *
     * @param i
     * @param j
     */
    public void moveSubjectUp(int i, int j) {
        tm.getCurrentTable().moveSubjectUp(tm.getsIndexI(), tm.getsIndexJ());
        initNewTimetable();
        if (j > 0) {
            subjects[i][j - 1].requestFocus();
        }
    }

    /**
     * moves the subject at the indexes right
     *
     * @param i
     * @param j
     */
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

    /**
     * moves the subject at the indexes down
     *
     * @param i
     * @param j
     */
    public void moveSubjectDown(int i, int j) {
        tm.getCurrentTable().moveSubjectDown(tm.getsIndexI(), tm.getsIndexJ());
        initNewTimetable();
        if (j < tm.getCurrentTable().getLessons()) {
            subjects[i][j + 1].requestFocus();
        }
    }

    /**
     * moves the subject at the indexes left
     *
     * @param i
     * @param j
     */
    public void moveSubjectLeft(int i, int j) {
        tm.getCurrentTable().moveSubjectLeft(tm.getsIndexI(), tm.getsIndexJ());
        initNewTimetable();
        if (i > 0) {
            subjects[i - 1][j].requestFocus();
        }
    }

    //
    //################################getters################################
    //
    public Menu getSettings() {
        return settingsMenu;
    }

    public Pane getPane() {
        return bg;
    }

    public JFXButton getSettingsButton() {
        return settings;
    }
}
