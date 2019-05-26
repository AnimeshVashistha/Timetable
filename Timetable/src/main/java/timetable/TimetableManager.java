package timetable;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import timetable.Datatypes.Timetable;
import timetable.Datatypes.TimetablePair;

/**
 *
 * @author Tobias
 */
public class TimetableManager {

    final static String TIMETABLES_STRING = "timetables";
    final static String TIMETABLE_INDEX_STRING = "timetableIndex";

    ArrayList<TimetablePair> timetables;
    TimetablePair currentTablePair;

    boolean isA = true;
    int dIndexI = 0;
    int tIndexI = 0;
    int sIndexI = 0;
    int sIndexJ = 0;
    int autocompleteIndex = 0;
    int timetableIndex = 0;
    int tableCount = 0;

    public TimetableManager() {
        
    }

    public void addTimetablePair() {
        currentTablePair = new TimetablePair("Timetable " + tableCount);
        timetables.add(currentTablePair);
        timetableIndex = timetables.size() - 1;
        tableCount++;
    }

    public void addTimetablePair(int index) {
        currentTablePair = new TimetablePair("Timetable " + tableCount);
        timetables.add(currentTablePair);
        timetableIndex = index;
        tableCount++;
    }

    public void deleteCurrentTimetablePair() {
        timetables.remove(currentTablePair);

        if (timetables.size() == 0) {
            addTimetablePair();
        } else if (timetableIndex < timetables.size()) {
            currentTablePair = timetables.get(timetableIndex);
        } else {
            timetableIndex--;
            currentTablePair = timetables.get(timetableIndex);
        }
    }

    public void deleteTimetablePair(int index) {
        timetables.remove(index);
        if (timetableIndex >= index) {
            timetableIndex--;
        }
    }

    public int getTimeTableIndex() {
        return timetableIndex;
    }

    public TimetablePair getCurrentTablePair() {
        return currentTablePair;
    }

    public TimetablePair getTimetablePair(int index) {
        return timetables.get(index);
    }

    public void setTimeTableIndex(int timetableIndex) {
        this.timetableIndex = timetableIndex;
    }

    public void setCurrentTablePair(int index) {
        if (index < timetables.size()) {
            currentTablePair = timetables.get(index);
            timetableIndex = index;
        }
    }

    public List<TimetablePair> getTimetablePairs() {
        return timetables;
    }

    public Timetable getCurrentTable() {
        return currentTablePair.get(isA);
    }

    public boolean IsA() {
        return isA;
    }

    public void setIsA(boolean isA) {
        this.isA = isA;
    }

    public int getdIndexI() {
        return dIndexI;
    }

    public void setdIndexI(int dIndexI) {
        this.dIndexI = dIndexI;
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

    public void clearColumn() {
        currentTablePair.get(isA).clearLessonColumn(dIndexI);
    }

    public void deleteColumn() {
        currentTablePair.get(isA).deleteLessonColumn(dIndexI);
    }

    public void clearRow() {
        currentTablePair.get(isA).clearLessonRow(tIndexI);
    }

    public void deleteRow() {
        currentTablePair.get(isA).deleteLessonRow(tIndexI);
    }

    public void addRowAbove() {
        currentTablePair.get(isA).addLessonRowAbove(tIndexI);
    }

    public void addRowBelow() {
        currentTablePair.get(isA).addLessonRowBelow(tIndexI);
    }

    public void clearSubject() {
        currentTablePair.get(isA).clearSubject(sIndexI, sIndexJ);
    }

    public void deleteSubject() {
        currentTablePair.get(isA).deleteSubject(sIndexI, sIndexJ);
    }

    public void addSubjectAbove() {
        currentTablePair.get(isA).addSubjectAbove(sIndexI, sIndexJ);
    }

    public void addSubjectBelow() {
        currentTablePair.get(isA).addSubjectBelow(sIndexI, sIndexJ);
    }

}
