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

    public JSONObject convertTimetablePair(TimetablePair timetablePair) {
        JSONObject tp = new JSONObject();
        tp.put("name", timetablePair.getName());
        tp.put("a", convertTimetable(timetablePair.getA()));
        tp.put("b", convertTimetable(timetablePair.getB()));
        
        return tp;
    }
    
    public JSONObject convertTimetable(Timetable timetable){
        JSONObject t = new JSONObject();
        
        JSONArray days = new JSONArray();
        JSONArray times = new JSONArray();
        JSONArray subjects = new JSONArray();
        JSONObject startTime = new JSONObject();
        
        t.put("lessons", timetable.getLessons());
        t.put("smallPause", timetable.getSmallPause());
        t.put("middlePause", timetable.getMiddlePause());
        t.put("bigPause", timetable.getBigPause());
        t.put("lessonLenght", timetable.getLessonlength());
        
        return t;
    }

    public void readData() {

    }
}
