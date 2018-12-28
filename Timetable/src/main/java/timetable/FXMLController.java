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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class FXMLController implements Initializable {

    JFXButton[] days;
    JFXButton[] times;
    JFXButton[][] subjects;
    JFXToggleButton[] dayToggles;
    Label[] dayLabels;
    GridPane[] dayPanes;
    JFXButton selectedDay;
    JFXButton selectedTime;
    JFXButton selectedSubject;

    FadeTransition menuIconFadeIn;
    TranslateTransition menuIconSlideIn;
    FadeTransition menuIconFadeOut;
    TranslateTransition menuIconSlideOut;
    ParallelTransition showMenuIcon;
    ParallelTransition hideMenuIcon;

    TranslateTransition dayOverlayComeUp;
    FadeTransition dayOverlayFadeIn;
    TranslateTransition dayOverlayGoDown;
    FadeTransition dayOverlayFadeOut;
    ParallelTransition showDayOverlay;
    ParallelTransition hideDayOverlay;

    TranslateTransition timeOverlayComeUp;
    FadeTransition timeOverlayFadeIn;
    TranslateTransition timeOverlayGoDown;
    FadeTransition timeOverlayFadeOut;
    ParallelTransition showTimeOverlay;
    ParallelTransition hideTimeOverlay;

    TranslateTransition subjectOverlayComeUp;
    FadeTransition subjectOverlayFadeIn;
    TranslateTransition subjectOverlayGoDown;
    FadeTransition subjectOverlayFadeOut;
    ParallelTransition showSubjectOverlay;
    ParallelTransition hideSubjectOverlay;

    List<Timetable> timetables = new ArrayList<Timetable>();
    Timetable currentTable = new Timetable();

    int animationDuration = 300;
    int animationDistance = 30;
    int dIndexI = 0;
    int tIndexI = 0;
    int sIndexI = 0;
    int sIndexJ = 0;

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
    private AnchorPane timeOverlay;
    @FXML
    private JFXButton tOverlayDone;
    @FXML
    private JFXButton tOverlayDelete;
    @FXML
    private JFXButton tOverlayAddBelow;
    @FXML
    private JFXButton tOverlayAddAbove;
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
    private Separator seperator1;
    @FXML
    private Separator seperator2;
    @FXML
    private ImageView menuIcon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initControlArrays();
        initNewTimetable();

        //menu icon transition
        menuIconSlideIn = new TranslateTransition();
        menuIconSlideIn.setDuration(Duration.millis(animationDuration));
        menuIconSlideIn.setFromX(-animationDistance);
        menuIconSlideIn.setToX(0);

        menuIconFadeIn = new FadeTransition();
        menuIconFadeIn.setDuration(Duration.millis(animationDuration));
        menuIconFadeIn.setFromValue(0);
        menuIconFadeIn.setToValue(1);

        menuIconSlideOut = new TranslateTransition();
        menuIconSlideOut.setDuration(Duration.millis(animationDuration));
        menuIconSlideOut.setFromX(0);
        menuIconSlideOut.setToX(-animationDistance);

        menuIconFadeOut = new FadeTransition();
        menuIconFadeOut.setDuration(Duration.millis(animationDuration));
        menuIconFadeOut.setFromValue(1);
        menuIconFadeOut.setToValue(0);

        showMenuIcon = new ParallelTransition(menuIcon);
        showMenuIcon.getChildren().add(menuIconFadeIn);
        showMenuIcon.getChildren().add(menuIconSlideIn);

        hideMenuIcon = new ParallelTransition(menuIcon);
        hideMenuIcon.getChildren().add(menuIconFadeOut);
        hideMenuIcon.getChildren().add(menuIconSlideOut);

        //day overlay transitions        
        dayOverlayComeUp = new TranslateTransition();
        dayOverlayComeUp.setDuration(Duration.millis(animationDuration));
        dayOverlayComeUp.setFromY(animationDistance);
        dayOverlayComeUp.setToY(0);

        dayOverlayFadeIn = new FadeTransition();
        dayOverlayFadeIn.setDuration(Duration.millis(animationDuration));
        dayOverlayFadeIn.setFromValue(0);
        dayOverlayFadeIn.setToValue(1);

        dayOverlayGoDown = new TranslateTransition();
        dayOverlayGoDown.setDuration(Duration.millis(animationDuration));
        dayOverlayGoDown.setFromY(0);
        dayOverlayGoDown.setToY(animationDistance);

        dayOverlayFadeOut = new FadeTransition();
        dayOverlayFadeOut.setDuration(Duration.millis(animationDuration));
        dayOverlayFadeOut.setFromValue(1);
        dayOverlayFadeOut.setToValue(0);

        showDayOverlay = new ParallelTransition(dayOverlay);
        showDayOverlay.getChildren().add(dayOverlayComeUp);
        showDayOverlay.getChildren().add(dayOverlayFadeIn);

        hideDayOverlay = new ParallelTransition(dayOverlay);
        hideDayOverlay.getChildren().add(dayOverlayGoDown);
        hideDayOverlay.getChildren().add(dayOverlayFadeOut);

        //time overlay transitions        
        timeOverlayComeUp = new TranslateTransition();
        timeOverlayComeUp.setDuration(Duration.millis(animationDuration));
        timeOverlayComeUp.setFromY(animationDistance);
        timeOverlayComeUp.setToY(0);

        timeOverlayFadeIn = new FadeTransition();
        timeOverlayFadeIn.setDuration(Duration.millis(animationDuration));
        timeOverlayFadeIn.setFromValue(0);
        timeOverlayFadeIn.setToValue(1);

        timeOverlayGoDown = new TranslateTransition();
        timeOverlayGoDown.setDuration(Duration.millis(animationDuration));
        timeOverlayGoDown.setFromY(0);
        timeOverlayGoDown.setToY(animationDistance);

        timeOverlayFadeOut = new FadeTransition();
        timeOverlayFadeOut.setDuration(Duration.millis(animationDuration));
        timeOverlayFadeOut.setFromValue(1);
        timeOverlayFadeOut.setToValue(0);

        showTimeOverlay = new ParallelTransition(timeOverlay);
        showTimeOverlay.getChildren().add(timeOverlayComeUp);
        showTimeOverlay.getChildren().add(timeOverlayFadeIn);

        hideTimeOverlay = new ParallelTransition(timeOverlay);
        hideTimeOverlay.getChildren().add(timeOverlayGoDown);
        hideTimeOverlay.getChildren().add(timeOverlayFadeOut);

        //subject overlay transitions
        subjectOverlayComeUp = new TranslateTransition();
        subjectOverlayComeUp.setDuration(Duration.millis(animationDuration));
        subjectOverlayComeUp.setFromY(animationDistance);
        subjectOverlayComeUp.setToY(0);

        subjectOverlayFadeIn = new FadeTransition();
        subjectOverlayFadeIn.setDuration(Duration.millis(animationDuration));
        subjectOverlayFadeIn.setFromValue(0);
        subjectOverlayFadeIn.setToValue(1);

        subjectOverlayGoDown = new TranslateTransition();
        subjectOverlayGoDown.setDuration(Duration.millis(animationDuration));
        subjectOverlayGoDown.setFromY(0);
        subjectOverlayGoDown.setToY(animationDistance);

        subjectOverlayFadeOut = new FadeTransition();
        subjectOverlayFadeOut.setDuration(Duration.millis(animationDuration));
        subjectOverlayFadeOut.setFromValue(1);
        subjectOverlayFadeOut.setToValue(0);

        showSubjectOverlay = new ParallelTransition(subjectOverlay);
        showSubjectOverlay.getChildren().add(subjectOverlayComeUp);
        showSubjectOverlay.getChildren().add(subjectOverlayFadeIn);

        hideSubjectOverlay = new ParallelTransition(subjectOverlay);
        hideSubjectOverlay.getChildren().add(subjectOverlayGoDown);
        hideSubjectOverlay.getChildren().add(subjectOverlayFadeOut);

        bg.widthProperty().addListener(n -> {
            subjectOverlay.setVisible(false);
            timeOverlay.setVisible(false);
            dayOverlay.setVisible(false);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> resizeFonts()));
            timeline.play();
        });
        bg.heightProperty().addListener(n -> {
            subjectOverlay.setVisible(false);
            timeOverlay.setVisible(false);
            dayOverlay.setVisible(false);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> resizeFonts()));
            timeline.play();
        });

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

        double scaleFactor = 0.075;
        for (JFXButton b : days) {
            b.setFont(new Font((name.getHeight() + name.getWidth()) * scaleFactor));
        }
        for (JFXButton b : times) {
            b.setFont(new Font((name.getHeight() + name.getWidth()) * scaleFactor));
        }
        for (JFXButton[] ba : subjects) {
            for (JFXButton b : ba) {
                b.setFont(new Font((name.getHeight()) * 0.2));
            }
        }
    }

    public void initNewTimetable() {
        subjectGrid.getChildren().removeIf(e -> (e.getClass() == JFXButton.class));
        subjectGrid.getColumnConstraints().clear();
        subjectGrid.getRowConstraints().clear();

        subjectGrid.add(name, 0, 0, 1, 1);
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
            }
        }
        pos = 0;
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (j < currentTable.getLessons() && currentTable.isDayDisplayed(i)) {
                    subjectGrid.add(subjects[i][j], pos + 1, j + 1, 1, 1);
                    subjects[i][j].setText(currentTable.getSubjectText(i, j) + "\n" + currentTable.getRoomText(i, j));
                }
            }
            if (currentTable.isDayDisplayed(i)) {
                pos++;
            }
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> resizeFonts()));
        timeline.play();
    }

    public void cancelOverlays() {
        dayOverlay.setVisible(false);
        timeOverlay.setVisible(false);
        subjectOverlay.setVisible(false);
    }

    public void hideDayOverlay() {
        if (selectedDay != null) {
            selectedDay.requestFocus();
        }
        for (int i = 0; i < 7; i++) {
            currentTable.setDayDisplayed(dayToggles[i].isSelected(), i);
        }
        initNewTimetable();
        hideDayOverlay.play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(animationDuration), e -> dayOverlay.setVisible(false)));
        timeline.play();
    }

    public void hideTimeOverlay() {
        if (selectedTime != null) {
            selectedTime.requestFocus();
        }
        hideTimeOverlay.play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(animationDuration), e -> timeOverlay.setVisible(false)));
        timeline.play();
    }

    public void hideSubjectOverlay() {
        writeSOverlayData();
        if (selectedSubject != null) {
            selectedSubject.requestFocus();
        }
        hideSubjectOverlay.play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(animationDuration), e -> subjectOverlay.setVisible(false)));
        timeline.play();
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
    private void showSubjectOverlay(ActionEvent event) {

        cancelOverlays();

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

        subjectOverlay.setPrefWidth(w);
        subjectOverlay.setPrefHeight(h);

        if (subjectGrid.getWidth() - x > w) {
            subjectOverlay.setLayoutX(x);
        } else {
            subjectOverlay.setLayoutX(subjectGrid.getWidth() - w);
        }

        if (subjectGrid.getHeight() - y > h) {
            subjectOverlay.setLayoutY(y);
        } else {
            subjectOverlay.setLayoutY(subjectGrid.getHeight() - h);
        }

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
        Timeline focus = new Timeline(new KeyFrame(Duration.millis(animationDuration * 0.7), e -> sOverlaySubject.requestFocus()));
        focus.play();
        showSubjectOverlay.play();
    }

    @FXML
    private void callHideSubjectOverlay(ActionEvent event) {
        hideSubjectOverlay();
        currentTable.toString();
    }

    @FXML
    private void sOverlaySubject_kp(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            hideSubjectOverlay();
        } else if (sOverlaySubject.getText().length() > 0 || event.getText().length() > 0) {
            List<Subject> options = currentTable.getAutocompleteOptions(sOverlaySubject.getText() + event.getText());
            if (options.size() > 0) {
                for (Subject s : options) {
                    System.out.println(s.getSubject());
                }
            }
        }
    }

    @FXML
    private void sOverlayRoom_kp(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            hideSubjectOverlay();
        }
    }

    @FXML
    private void sOverlayTeacher_kp(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            hideSubjectOverlay();
        }
    }

    @FXML
    private void showTimeOverlay(MouseEvent event) {

        cancelOverlays();

        for (int i = 0; i < times.length; i++) {
            if (event.getSource() == times[i]) {
                selectedTime = times[i];
                tIndexI = i;
                break;
            }
        }

        if (event.isSecondaryButtonDown()) {

            double hf = 2.5;
            double x = event.getSceneX();
            double y = event.getSceneY();
            double w = selectedTime.getHeight() * 1.8;
            double h = selectedTime.getHeight() * hf;

            timeOverlay.setPrefWidth(w);
            timeOverlay.setPrefHeight(h);

            if (subjectGrid.getWidth() - x > w) {
                timeOverlay.setLayoutX(x);
            } else {
                timeOverlay.setLayoutX(subjectGrid.getWidth() - w);
            }

            if (subjectGrid.getHeight() - y > h) {
                timeOverlay.setLayoutY(y);
            } else {
                timeOverlay.setLayoutY(subjectGrid.getHeight() - h);
            }

            seperator1.setPadding(new Insets(0, h / hf * 0.4, 0, h / hf * 0.4));
            seperator2.setPadding(new Insets(0, h / hf * 0.4, 0, h / hf * 0.4));

            tOverlayDelete.setFont(new Font(h / hf * 0.2));
            tOverlayAddAbove.setFont(new Font(h / hf * 0.2));
            tOverlayAddBelow.setFont(new Font(h / hf * 0.2));
            tOverlayDone.setFont(new Font(h / hf * 0.2));

            timeOverlay.setVisible(true);
            showTimeOverlay.play();
        } else {
            double x = selectedTime.getLayoutX();
            double y = selectedTime.getLayoutY();
        }
    }

    @FXML
    private void showDayOverlay(MouseEvent event) {

        cancelOverlays();

        if (event.isSecondaryButtonDown()) {

            for (int i = 0; i < days.length; i++) {
                if (event.getSource() == days[i]) {
                    selectedDay = days[i];
                    break;
                }
            }

            double hf = 4.2;
            double x = event.getSceneX();
            double y = event.getSceneY();
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
        }
    }

    @FXML
    private void menu(ActionEvent event) {
    }

    @FXML
    private void callHideTimeOverlay(ActionEvent event) {
        hideTimeOverlay();
    }

    @FXML
    private void callHideDayOverlay(ActionEvent event) {
        hideDayOverlay();
    }

    @FXML
    private void deleteRow(ActionEvent event) {
        timeOverlay.setVisible(false);
        currentTable.removeLessonRow(tIndexI);
        initNewTimetable();
    }

    @FXML
    private void addRowBelow(ActionEvent event) {
        timeOverlay.setVisible(false);
        currentTable.addLessonRowBelow(tIndexI);
        initNewTimetable();
    }

    @FXML
    private void addRowAbove(ActionEvent event) {
        timeOverlay.setVisible(false);
        currentTable.addLessonRowAbove(tIndexI);
        initNewTimetable();
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
    private void hideMenuIcon(MouseEvent event) {
        hideMenuIcon.play();
        System.out.println("out");
    }

    @FXML
    private void showMenuIcon(MouseEvent event) {
        showMenuIcon.play();
        System.out.println("in");
    }
}
