package timetable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class DataManager {

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

}
