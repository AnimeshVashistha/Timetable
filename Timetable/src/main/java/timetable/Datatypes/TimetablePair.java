package timetable.Datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class TimetablePair implements Serializable {

    ArrayList<Subject> references = new ArrayList<Subject>();
    ArrayList<Subject> options;
    String name = "new Timetable";

    Timetable a = new Timetable();
    Timetable b = new Timetable();

    public TimetablePair() {

    }

    public TimetablePair(String name) {
        this.name = name;
    }

    public TimetablePair(Timetable a, Timetable b) {
        this.a = a;
        this.b = b;
    }

    public void updateReferences() {
        references.clear();
        updateReferences(a.getSubjects());
        updateReferences(b.getSubjects());
    }

    private void updateReferences(Subject[][] subjects) {
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                if (references.size() > 0) {
                    boolean n = true;
                    for (int c = 0; c < references.size(); c++) {
                        if (subjects[i][j].getSubject().equals(references.get(c).getSubject())
                                && subjects[i][j].getRoom().equals(references.get(c).getRoom())
                                && subjects[i][j].getTeacher().equals(references.get(c).getTeacher())) {
                            n = false;
                            break;
                        }
                    }
                    if (n && subjects[i][j].getSubject().length() > 0) {
                        references.add(subjects[i][j]);
                    }
                } else if (subjects[i][j].getSubject().length() > 0) {
                    references.add(subjects[i][j]);
                }
            }
        }
    }

    public ArrayList<Subject> getAutocompleteOptions(String subject) {
        ArrayList<Subject> options = new ArrayList<Subject>();

        for (Subject s : references) {
            if (s.getSubject().toLowerCase().startsWith(subject.toLowerCase())) {
                options.add(s);
            }
        }

        this.options = options;

        return options;
    }

    public Timetable get(boolean isA) {
        if (isA) {
            return a;
        } else {
            return b;
        }
    }

    public Timetable getA() {
        return a;
    }

    public void setA(Timetable a) {
        this.a = a;
    }

    public Timetable getB() {
        return b;
    }

    public void setB(Timetable b) {
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getOptions() {
        return options;
    }

    public Subject getOption(int i) {
        return options.get(i);
    }

}
