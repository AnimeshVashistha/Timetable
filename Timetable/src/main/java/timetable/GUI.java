package timetable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.util.ArrayList;
import java.util.List;
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

    static String primaryColor = "-fx-color: #66DD7744";
    static String selectedColor = "-fx-background-color: #66DD7744";
    static String unselectedColor = "-fx-background-color: #00000000";
    static String ripplerFill = "#66DD77";
    static String[] dayNames = ENGLISH_DAY_NAMES;

    TimetableManager tm;

    AnchorPane bg;
    GridPane subjectGrid;

    EventHandler<ActionEvent> nameAction;
    EventHandler<MouseEvent> dayPressed;
    EventHandler<KeyEvent> dayKeyReleased;
    EventHandler<MouseEvent> timePressed;
    EventHandler<KeyEvent> timeKeyReleased;
    EventHandler<ActionEvent> subjectAction;
    EventHandler<MouseEvent> subjectPressed;
    EventHandler<KeyEvent> subjectKeyReleased;
    MenuButton name;
    JFXButton[] days;
    JFXButton[] times;
    JFXButton[][] subjects;
    JFXButton selectedDay;
    JFXButton selectedTime;
    JFXButton selectedSubject;

    EventHandler<KeyEvent> hideAllMenusK;
    EventHandler<ActionEvent> hideAllMenusA;
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
        hideAllMenusA = (ActionEvent event) -> {
            hideAllMenus();
        };

        initControlArrays();

        tm = new TimetableManager();

        initNewTimetable();

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
        menuName.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        menuName.addEventHandler(KeyEvent.KEY_RELEASED, writeMenuData);
        addTimetable = new JFXButton("add timetable");
        addTimetable.setPrefWidth(500);
        addTimetable.setPrefHeight(150);
        addTimetable.setRipplerFill(Color.web(ripplerFill));
        addTimetable.addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
        addTimetable.addEventHandler(ActionEvent.ACTION, event -> {
            addTimetable();
        });
        deleteTimetable = new JFXButton("delete timetable");
        deleteTimetable.setPrefWidth(500);
        deleteTimetable.setPrefHeight(150);
        deleteTimetable.setRipplerFill(Color.web(ripplerFill));
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
            dayToggles[i].addEventHandler(KeyEvent.KEY_RELEASED, hideAllMenusK);
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
        subjectName.focusedProperty().addListener(event -> {
            if (!subjectName.isFocused()) {
                autoCompletePane.hide();
            }
        });

        Timeline unfocus = new Timeline(
                new KeyFrame(Duration.millis(1), event -> bg.requestFocus())
        );

        unfocus.play();
    }

    public void initControlArrays() {

        nameAction = (ActionEvent event) -> {
            menu();
        };
        name = new MenuButton();
        name.setRipplerFill(Color.web("#888888"));
        name.addEventHandler(ActionEvent.ANY, nameAction);
        subjectGrid.add(name, 0, 0, 1, 1);

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
            day.setRipplerFill(Color.web("000000"));
            day.setMinSize(100, 40);
            day.setPrefSize(500, 150);
            day.addEventHandler(ActionEvent.ANY, hideAllMenusA);
            day.addEventHandler(MouseEvent.MOUSE_PRESSED, dayPressed);
            day.addEventHandler(KeyEvent.KEY_RELEASED, dayKeyReleased);
            days[i] = day;
        }

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
            time.setRipplerFill(Color.web("#000000"));
            time.setMinSize(100, 40);
            time.setPrefSize(500, 150);
            time.addEventHandler(ActionEvent.ANY, hideAllMenusA);
            time.addEventHandler(MouseEvent.MOUSE_PRESSED, timePressed);
            time.addEventHandler(KeyEvent.KEY_RELEASED, timeKeyReleased);
            times[i] = time;
        }

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
                subject.setRipplerFill(Color.web(ripplerFill));
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

        name.setFont(new Font((name.getHeight() + name.getWidth()) * scaleFactor1));

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

        name.setText(tm.getCurrentTable().getName());

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

    public void hideAllMenus() {
        for (SomePane s : menus) {
            s.hide();
        }
    }

    //
    //################################menu################################
    //
    public void menu() {
        hideOtherMenus(menu);
        menu.show(name.getButton());
    }

    public void scaleMenu() {
        double h = name.getButton().getHeight();
        menu.getPane().setMargin(menuName, new Insets(0, 0, h * 0.1, 0));
        menuName.setPadding(new Insets(h * 0.4, h * 0.4, 0, h * 0.4));
        menuName.setFont(new Font(h * FONT_FACTOR));
        addTimetable.setFont(new Font(h * FONT_FACTOR));
        deleteTimetable.setFont(new Font(h * FONT_FACTOR));
        menu.getDone().setFont(new Font(h * FONT_FACTOR));
        timetablePane.scale(h, menu.getPane().getPrefWidth());
    }

    public void updateMenuData() {
        menuName.setText(tm.getCurrentTable().getName());
    }

    public void writeMenuData() {
        tm.getCurrentTable().setName(menuName.getText());
        name.setText(menuName.getText());
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
            dayLabels[i].setFont(new Font(h * FONT_FACTOR));
            dayPanes[i].setPadding(new Insets(0, h * 0.1, 0, h * 0.4));
        }
        dayContextMenu.getPane().setPadding(new Insets(h * 0.2, 0, 0, 0));
        dayContextMenu.getDone().setPadding(new Insets(h * 0.15, 0, h * 0.15, 0));
        dayContextMenu.getDone().setFont(new Font(h * FONT_FACTOR));
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
