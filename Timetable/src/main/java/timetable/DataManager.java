package timetable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {

    String filename;
    String tables = "timetables";
    String ctable = "currentTable";

    public DataManager(String filename) {
        this.filename = filename;
    }

    public List<Timetable> readTimeTables() {
        ArrayList<Timetable> temp = new ArrayList<Timetable>();
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<String, Object> hm = (HashMap<String, Object>) ois.readObject();
            fis.close();

            temp = (ArrayList< Timetable>) hm.get(tables);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }

    public Timetable readCurrentTable() {
        Timetable temp = new Timetable();
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<String, Object> hm = (HashMap<String, Object>) ois.readObject();
            fis.close();

            temp = (Timetable) hm.get(ctable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return temp;
    }

    public void saveTimetables(List<Timetable> timetables, Timetable currentTable) {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put(tables, timetables);
        hm.put(ctable, currentTable);

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

}
