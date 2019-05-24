package timetable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import timetable.Datatypes.SimpleTime;
import timetable.Datatypes.Subject;
import timetable.Datatypes.Timetable;
import timetable.Datatypes.TimetablePair;

public class DataManager {

    String filename;

    public DataManager(String filename) {
        this.filename = filename;
    }

    final static String TIMETABLES = "timetables";
    final static String TIMETABLE_INDEX = "timetableIndex";

    public void writeData(ArrayList<TimetablePair> timetables, int index) {

        JSONObject jSONObject = new JSONObject();
        JSONArray jsonTimetables = new JSONArray();

        for (TimetablePair tp : timetables) {
            jsonTimetables.add(convertTimetablePairToJSON(tp));
        }

        jSONObject.put(TIMETABLES, jsonTimetables);
        jSONObject.put(TIMETABLE_INDEX, index);

        try (FileWriter file = new FileWriter(filename)) {
            file.write(jSONObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ArrayList<TimetablePair> timetables;
    int index;

    public void readData() {
        JSONParser parser = new JSONParser();

        try {
            Reader reader = new FileReader(filename);

            timetables = new ArrayList();

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray jsonTimtables = (JSONArray) jsonObject.get(TIMETABLES);

            Iterator<JSONObject> it = jsonTimtables.iterator();
            while (it.hasNext()) {
                TimetablePair tp = convertJSONTOTimetablePair(it.next());
                timetables.add(tp);
            }

            index = (int) (long) jsonObject.get(TIMETABLE_INDEX);

        } catch (Exception ex) {
            timetables = new ArrayList();
            index = 0;
            ex.printStackTrace();
        }
    }

    final static String TIMETABLE_PAIR_NAME = "name";
    final static String TIMETABLE_PAIR_A = "a";
    final static String TIMETABLE_PAIR_B = "b";

    public JSONObject convertTimetablePairToJSON(TimetablePair timetablePair) {
        JSONObject tp = new JSONObject();
        tp.put(TIMETABLE_PAIR_NAME, timetablePair.getName());
        tp.put(TIMETABLE_PAIR_A, convertTimetableToJSON(timetablePair.getA()));
        tp.put(TIMETABLE_PAIR_B, convertTimetableToJSON(timetablePair.getB()));

        return tp;
    }

    public TimetablePair convertJSONTOTimetablePair(JSONObject jsonObject) {
        TimetablePair tp = new TimetablePair();
        String name = (String) jsonObject.get(TIMETABLE_PAIR_NAME);
        Timetable a = convertJSONObjectTOTimetable((JSONObject) jsonObject.get(TIMETABLE_PAIR_A));
        Timetable b = convertJSONObjectTOTimetable((JSONObject) jsonObject.get(TIMETABLE_PAIR_B));
        tp.setName(name);
        tp.setA(a);
        tp.setB(b);

        return tp;
    }

    final static String TIMETABLE_DAYS = "days";
    final static String TIMETABLE_TIMES = "times";
    final static String TIMETABLE_SUBJECTS = "subjects";
    final static String TIMETABLE_START_TIME = "startTime";
    final static String TIMETABLE_LESSONS = "lessons";
    final static String TIMETABLE_SMALL_PAUSE = "smallPause";
    final static String TIMETABLE_MIDDLE_PAUSE = "middlePause";
    final static String TIMETABLE_BIG_PAUSE = "bigPause";
    final static String TIMETABLE_LESSON_LENGTH = "lessonLength";

    public JSONObject convertTimetableToJSON(Timetable timetable) {
        JSONObject t = new JSONObject();

        JSONArray days = new JSONArray();
        for (boolean b : timetable.getDays()) {
            days.add(b);
        }
        t.put(TIMETABLE_DAYS, days);
        JSONArray times = new JSONArray();
        for (SimpleTime st : timetable.getTimes()) {
            times.add(convertSimpleTimeToJSON(st));
        }
        t.put(TIMETABLE_TIMES, times);
        JSONArray subjectsParent = new JSONArray();
        for (Subject[] sa : timetable.getSubjects()) {
            JSONArray subjectsChild = new JSONArray();
            for (Subject s : sa) {
                subjectsChild.add(convertSubjectToJSON(s));
            }
            subjectsParent.add(subjectsChild);
        }
        t.put(TIMETABLE_SUBJECTS, subjectsParent);

        t.put(TIMETABLE_START_TIME, convertSimpleTimeToJSON(timetable.getStartTime()));

        t.put(TIMETABLE_LESSONS, timetable.getLessons());
        t.put(TIMETABLE_SMALL_PAUSE, timetable.getSmallPause());
        t.put(TIMETABLE_MIDDLE_PAUSE, timetable.getMiddlePause());
        t.put(TIMETABLE_BIG_PAUSE, timetable.getBigPause());
        t.put(TIMETABLE_LESSON_LENGTH, timetable.getLessonlength());

        return t;
    }

    public Timetable convertJSONObjectTOTimetable(JSONObject jsonObject) {
        Timetable t = new Timetable();

        JSONArray days = (JSONArray) jsonObject.get(TIMETABLE_DAYS);
        for (int i = 0; i < days.size(); i++) {
            t.setDayDisplayed((boolean) days.get(i), i);
        }
        JSONArray times = (JSONArray) jsonObject.get(TIMETABLE_TIMES);
        for (int i = 0; i < times.size(); i++) {
            t.setTime(convertJSONTOSimpleTime((JSONObject) times.get(i)), i);
        }
        JSONArray subjectsParent = (JSONArray) jsonObject.get(TIMETABLE_SUBJECTS);
        for (int i = 0; i < subjectsParent.size(); i++) {
            JSONArray subjectsChild = (JSONArray) subjectsParent.get(i);
            for (int j = 0; j < subjectsChild.size(); j++) {
                JSONObject subject = (JSONObject) subjectsChild.get(j);
                Subject s = convertJSONToSubject(subject);
                t.setSubject(s, i, j);
            }
        }

        t.setStartTime(convertJSONTOSimpleTime((JSONObject) jsonObject.get(TIMETABLE_START_TIME)));

        t.setLessons((int) (long) jsonObject.get(TIMETABLE_LESSONS));
        t.setSmallPause((int) (long) jsonObject.get(TIMETABLE_SMALL_PAUSE));
        t.setMiddlePause((int) (long) jsonObject.get(TIMETABLE_MIDDLE_PAUSE));
        t.setBigPause((int) (long) jsonObject.get(TIMETABLE_BIG_PAUSE));
        t.setLessonlength((int) (long) jsonObject.get(TIMETABLE_LESSON_LENGTH));

        return t;
    }

    final static String SIMPLE_TIME_HOURS = "hours";
    final static String SIMPLE_TIME_MINUTES = "minutes";

    public JSONObject convertSimpleTimeToJSON(SimpleTime simpleTime) {
        JSONObject st = new JSONObject();
        st.put(SIMPLE_TIME_HOURS, simpleTime.getHours());
        st.put(SIMPLE_TIME_MINUTES, simpleTime.getMinutes());

        return st;
    }

    public SimpleTime convertJSONTOSimpleTime(JSONObject jsonObject) {
        SimpleTime st = new SimpleTime();
        st.setHours((int) (long) jsonObject.get(SIMPLE_TIME_HOURS));
        st.setMinutes((int) (long) jsonObject.get(SIMPLE_TIME_MINUTES));

        return st;
    }

    final static String SUBJECT_NAME = "name";
    final static String SUBJECT_ROOM = "room";
    final static String SUBJECT_TEACHER = "teacher";

    public JSONObject convertSubjectToJSON(Subject subject) {
        JSONObject s = new JSONObject();
        s.put(SUBJECT_NAME, subject.getSubject());
        s.put(SUBJECT_ROOM, subject.getRoom());
        s.put(SUBJECT_TEACHER, subject.getTeacher());

        return s;
    }

    public Subject convertJSONToSubject(JSONObject jsonObject) {
        Subject s = new Subject();
        s.setSubject((String) jsonObject.get(SUBJECT_NAME));
        s.setRoom((String) jsonObject.get(SUBJECT_ROOM));
        s.setTeacher((String) jsonObject.get(SUBJECT_TEACHER));

        return s;
    }

    public ArrayList<TimetablePair> getTimetables() {
        return timetables;
    }

    public int getIndex() {
        return index;
    }

}
