package timetable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;

public class DataManager {

    ObjectMapper objectMapper = new ObjectMapper();
    HashMap<String, Object> hm;
    String filename;

    public DataManager(String filename) {
        hm = new HashMap<String, Object>();
        this.filename = filename;
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public Object readObject(String name) {

        try {
//            FileInputStream fis = new FileInputStream(filename);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            HashMap<String, Object> hm = (HashMap<String, Object>) ois.readObject();
//            fis.close();

            HashMap<String, Object> hm = (HashMap<String, Object>) objectMapper.readValue(new File(filename), HashMap.class);

            return hm.get(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeObject(String name, Serializable object) {
        hm.put(name, object);

        try {
//            FileOutputStream fos = new FileOutputStream(filename);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(hm);
//            oos.flush();
//            oos.close();
//            fos.close();

            objectMapper.writeValue(new File(filename), hm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
