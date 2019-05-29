package timetable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import timetable.Datatypes.SimpleTime;
import timetable.Datatypes.Subject;
import timetable.Datatypes.Timetable;
import timetable.Datatypes.TimetablePair;

/**
 *
 * @author Tobias
 */
public class DataManager {

    String filename;
    JSONObject jSONObject;

    public DataManager(String filename) {
        this.filename = filename;
    }

    public void writeData(JSONObject jSONObject) {
        File f = new File(filename);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
        }
        try (FileWriter file = new FileWriter(filename)) {
            file.write(jSONObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject readData() {
        JSONParser parser = new JSONParser();
        try {
            Reader reader = new FileReader(filename);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            return jsonObject;
        } catch (Exception ex) {
            return null;
        }
    }

    static final String TIMETABLE_PAIR_NAME = "name";
    static final String TIMETABLE_PAIR_START_TIME = "startTime";
    static final String TIMETABLE_PAIR_SMALL_PAUSE = "smallPause";
    static final String TIMETABLE_PAIR_MIDDLE_PAUSE = "middlePause";
    static final String TIMETABLE_PAIR_BIG_PAUSE = "bigPause";
    static final String TIMETABLE_PAIR_LESSON_LENGTH = "lessonLength";
    static final String TIMETABLE_PAIR_A = "a";
    static final String TIMETABLE_PAIR_B = "b";

    public JSONObject convertTimetablePairToJSON(TimetablePair timetablePair) {
        JSONObject tp = new JSONObject();
        tp.put(TIMETABLE_PAIR_NAME, timetablePair.getName());
        tp.put(TIMETABLE_PAIR_START_TIME, convertSimpleTimeToJSON(timetablePair.getStartTime()));
        tp.put(TIMETABLE_PAIR_SMALL_PAUSE, timetablePair.getSmallPause());
        tp.put(TIMETABLE_PAIR_MIDDLE_PAUSE, timetablePair.getMiddlePause());
        tp.put(TIMETABLE_PAIR_BIG_PAUSE, timetablePair.getBigPause());
        tp.put(TIMETABLE_PAIR_LESSON_LENGTH, timetablePair.getLessonLength());
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
        tp.setStartTime(convertJSONTOSimpleTime((JSONObject) jsonObject.get(TIMETABLE_PAIR_START_TIME)));
        tp.setSmallPause((int) (long) jsonObject.get(TIMETABLE_PAIR_SMALL_PAUSE));
        tp.setMiddlePause((int) (long) jsonObject.get(TIMETABLE_PAIR_MIDDLE_PAUSE));
        tp.setBigPause((int) (long) jsonObject.get(TIMETABLE_PAIR_BIG_PAUSE));
        tp.setLessonLength((int) (long) jsonObject.get(TIMETABLE_PAIR_LESSON_LENGTH));
        tp.setA(a);
        tp.setB(b);

        return tp;
    }

    static final String TIMETABLE_DAYS = "days";
    static final String TIMETABLE_TIMES = "times";
    static final String TIMETABLE_SUBJECTS = "subjects";
    static final String TIMETABLE_LESSONS = "lessons";

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
        t.put(TIMETABLE_LESSONS, timetable.getLessons());

        return t;
    }

    public Timetable convertJSONObjectTOTimetable(JSONObject jsonObject) {
        Timetable t = new TimetablePair().getA();

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
        t.setLessons((int) (long) jsonObject.get(TIMETABLE_LESSONS));

        return t;
    }

    static final String SIMPLE_TIME_HOURS = "hours";
    static final String SIMPLE_TIME_MINUTES = "minutes";

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

    static final String SUBJECT_NAME = "name";
    static final String SUBJECT_ROOM = "room";
    static final String SUBJECT_TEACHER = "teacher";

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

}
