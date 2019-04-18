package timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import timetable.Datatypes.Timetable;

/**
 *
 * @author Tobias
 */
public class TimetableManager {

    final static String TIMETABLES_STRING = "timetables";
    final static String CURRENT_TABLE_STRING = "currentTable";

    ArrayList<Timetable> timetables;
    Timetable currentTable;
    DataManager dm;

    int tIndexI = 0;
    int sIndexI = 0;
    int sIndexJ = 0;
    int autocompleteIndex = 0;
    int timetableIndex = 0;
    int tableCount = 0;

    public TimetableManager() {
        dm = new DataManager("timetables.cfg");

        Object tempTimetables = dm.readObject(TIMETABLES_STRING);

        if (tempTimetables != null && tempTimetables.getClass() == ArrayList.class) {
            ArrayList<Timetable> tempTimetables2 = (ArrayList<Timetable>) tempTimetables;

            try {
                if (tempTimetables2.get(0).getClass() == Timetable.class) {
                    Object tempCurrentTable = dm.readObject(CURRENT_TABLE_STRING);
                    System.out.println(tempCurrentTable.getClass());

                    if (tempCurrentTable != null && tempCurrentTable.getClass() == Timetable.class) {
                        currentTable = (Timetable) tempCurrentTable;
                    } else {
                        addTimetable(tIndexI);
                    }
                } else {
                    System.out.println("nope");
                    timetables = new ArrayList<Timetable>();
                    addTimetable();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("nope");
                timetables = new ArrayList<Timetable>();
                addTimetable();
            }

        } else {
            System.out.println("nope");
            timetables = new ArrayList<Timetable>();
            addTimetable();
        }

        Timeline t = new Timeline(
                new KeyFrame(Duration.millis(5000), n -> {
                    writeDataToFile();
                })
        );
        t.setCycleCount(Timeline.INDEFINITE);

    }

    public void writeDataToFile() {
        System.out.println("writing data");
        dm.writeObject(TIMETABLES_STRING, timetables);
        dm.writeObject(CURRENT_TABLE_STRING, currentTable);
    }

    public void addTimetable() {
        currentTable = new Timetable("Timetable " + tableCount);
        timetables.add(currentTable);
        timetableIndex = timetables.size() - 1;
        tableCount++;
    }

    public void addTimetable(int index) {
        currentTable = new Timetable("Timetable " + tableCount);
        timetables.add(currentTable);
        timetableIndex = index;
        tableCount++;
    }

    public void deleteCurrentTimetable() {
        timetables.remove(currentTable);

        if (timetables.size() == 0) {
            addTimetable();
        } else if (timetableIndex < timetables.size()) {
            currentTable = timetables.get(timetableIndex);
        } else {
            timetableIndex--;
            currentTable = timetables.get(timetableIndex);
        }
    }

    public void deleteTimetable(int index) {
        timetables.remove(index);
        if (timetableIndex >= index) {
            timetableIndex--;
        }
    }

    public int getTimeTableIndex() {
        return timetableIndex;
    }

    public Timetable getCurrentTable() {
        return currentTable;
    }

    public Timetable getTimetable(int index) {
        return timetables.get(index);
    }

    public void setTimeTableIndex(int timetableIndex) {
        this.timetableIndex = timetableIndex;
    }

    public void setCurrentTable(int index) {
        if (index < timetables.size()) {
            currentTable = timetables.get(index);
            timetableIndex = index;
        }
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public int gettIndexI() {
        return tIndexI;
    }

    public void settIndexI(int tIndexI) {
        this.tIndexI = tIndexI;
    }

    public int getsIndexI() {
        return sIndexI;
    }

    public void setsIndexI(int sIndexI) {
        this.sIndexI = sIndexI;
    }

    public int getsIndexJ() {
        return sIndexJ;
    }

    public void setsIndexJ(int sIndexJ) {
        this.sIndexJ = sIndexJ;
    }

    public void clearRow() {
        currentTable.clearLessonRow(tIndexI);
    }

    public void deleteRow() {
        currentTable.deleteLessonRow(tIndexI);
    }

    public void addRowAbove() {
        currentTable.addLessonRowAbove(tIndexI);
    }

    public void addRowBelow() {
        currentTable.addLessonRowBelow(tIndexI);
    }

    public void clearSubject() {
        currentTable.clearSubject(sIndexI, sIndexJ);
    }

    public void deleteSubject() {
        currentTable.deleteSubject(sIndexI, sIndexJ);
    }

    public void addSubjectAbove() {
        currentTable.addSubjectAbove(sIndexI, sIndexJ);
    }

    public void addSubjectBelow() {
        currentTable.addSubjectBelow(sIndexI, sIndexJ);
    }

}
