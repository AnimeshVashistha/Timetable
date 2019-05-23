package timetable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import timetable.Datatypes.SimpleTime;
import timetable.Datatypes.Subject;
import timetable.Datatypes.Timetable;
import timetable.Datatypes.TimetablePair;

public class DataManager {

    final static String timetablePairString = "timetablePair";
    final static String timetableString = "timetable";

    HashMap<String, Object> hm;
    String filename;

    public DataManager(String filename) {
        hm = new HashMap<String, Object>();
        this.filename = filename;
    }

    public Object readObject(String name) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<String, Object> hm = (HashMap<String, Object>) ois.readObject();
            fis.close();

            return hm.get(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeObject(String name, Serializable object) {
        hm.put(name, object);

        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hm);
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeData(ArrayList<TimetablePair> timetables, int index) {

        JSONArray jsonArray = new JSONArray();

        int i = 0;

        for (TimetablePair tp : timetables) {
            jsonArray.add(convertTimetablePair(tp));
        }

        try (FileWriter file = new FileWriter("test.json")) {
            file.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    final static String TIMETABLE_PAIR_NAME = "name";
    final static String TIMETABLE_PAIR_A = "a";
    final static String TIMETABLE_PAIR_B = "b";

    public JSONObject convertTimetablePair(TimetablePair timetablePair) {
        JSONObject tp = new JSONObject();
        tp.put(TIMETABLE_PAIR_NAME, timetablePair.getName());
        tp.put(TIMETABLE_PAIR_A, convertTimetable(timetablePair.getA()));
        tp.put(TIMETABLE_PAIR_B, convertTimetable(timetablePair.getB()));

        return tp;
    }

    final static String TIMETABLE_DAYS = "days";
    final static String TIMETABLE_TIMES = "times";
    final static String TIMETABLE_SUBJECTS = "SUBJECTS";

    public JSONObject convertTimetable(Timetable timetable) {
        JSONObject t = new JSONObject();

        JSONArray days = new JSONArray();
        for (boolean b : timetable.getDays()) {
            days.add(b);
        }
        t.put(TIMETABLE_DAYS, days);
        JSONArray times = new JSONArray();
        for (SimpleTime st : timetable.getTimes()) {
            times.add(convertSimpleTime(st));
        }
        t.put(TIMETABLE_TIMES, times);
        JSONArray subjects = new JSONArray();
        for (Subject[] sa : timetable.getSubjects()) {
            JSONArray subjectsInner = new JSONArray();
            for (Subject s : sa) {
                subjectsInner.add(convertSubject(s));
            }
            subjects.add(sa);
        }
        t.put(TIMETABLE_SUBJECTS, convertSimpleTime(timetable.getStartTime()));

        t.put("lessons", timetable.getLessons());
        t.put("smallPause", timetable.getSmallPause());
        t.put("middlePause", timetable.getMiddlePause());
        t.put("bigPause", timetable.getBigPause());
        t.put("lessonLenght", timetable.getLessonlength());

        return t;
    }

    final static String SIMPLE_TIME_HOURS = "hours";
    final static String SIMPLE_TIME_MINUTES = "minutes";

    public JSONObject convertSimpleTime(SimpleTime simpleTime) {
        JSONObject st = new JSONObject();
        st.put(SIMPLE_TIME_HOURS, simpleTime.getHours());
        st.put(SIMPLE_TIME_MINUTES, simpleTime.getMinutes());

        return st;
    }

    final static String SUBJECT_NAME = "name";
    final static String SUBJECT_ROOM = "room";
    final static String SUBJECT_TEACHER = "teacher";

    public JSONObject convertSubject(Subject subject) {
        JSONObject s = new JSONObject();
        s.put(SUBJECT_NAME, subject.getSubject());
        s.put(SUBJECT_ROOM, subject.getRoom());
        s.put(SUBJECT_TEACHER, subject.getTeacher());

        return s;
    }

    public void readData() {

    }
}
