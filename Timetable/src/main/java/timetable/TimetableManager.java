package timetable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import timetable.Datatypes.Timetable;
import timetable.Datatypes.TimetablePair;

/**
 *
 * @author Tobias
 */
public class TimetableManager {

    static final String TIMETABLES_STRING = "timetables";
    static final String TIMETABLE_INDEX_STRING = "timetableIndex";

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

    public TimetableManager(JSONObject data) {
        openData(data);
    }

    public JSONObject getDataToSave() {
        DataManager dm = new DataManager("");
        JSONObject data = new JSONObject();
        JSONArray jsonTimetables = new JSONArray();

        for (TimetablePair tp : timetables) {
            jsonTimetables.add(dm.convertTimetablePairToJSON(tp));
        }

        data.put(TIMETABLES_STRING, jsonTimetables);
        data.put(TIMETABLE_INDEX_STRING, timetableIndex);

        return data;
    }

    public void openData(JSONObject data) {
        DataManager dm = new DataManager("");
        timetables = new ArrayList();
        try {
            JSONObject jo = (JSONObject) data.get(Main.TIMETABLE_MANAGER);

            JSONArray jsonTimtables = (JSONArray) jo.get(TIMETABLES_STRING);
            Iterator<JSONObject> it = jsonTimtables.iterator();
            while (it.hasNext()) {
                TimetablePair tp = dm.convertJSONTOTimetablePair(it.next());
                timetables.add(tp);
            }
            try {
                timetableIndex = (int) (long) jo.get(TIMETABLE_INDEX_STRING);
            } catch (Exception e) {
                e.printStackTrace();
                timetableIndex = 0;
            }
            if (timetables.size() > 0) {
                currentTablePair = timetables.get(timetableIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addTimetablePair();
        }

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
