/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tobias
 */
public class Timetable implements Serializable {

    List<Subject> references = new LinkedList<Subject>();
    List<Subject> options;
    String name = "new Timetable";
    int lessons = 8;
    Subject[][] subjects = new Subject[7][10];
    LocalTime[] times = new LocalTime[10];
    boolean[] days = new boolean[7];
    DateTimeFormatter HoursAndMinutes = DateTimeFormatter.ofPattern("HH:mm");

    public Timetable() {
        init();
    }

    public Timetable(String name) {
        init();
        this.name = name;
    }

    public void init() {
        for (int i = 0; i < subjects.length; i++) {
            for (int j = 0; j < subjects[0].length; j++) {
                subjects[i][j] = new Subject("");
            }
        }

        int toAdd = 0;
        for (int i = 1; i < times.length; i++) {
            toAdd += 45;
            if (i % 2 == 0 && i != 0) {
                toAdd += 15;
            }
            times[i] = LocalTime.of(7, 30).plusMinutes(toAdd);
        }

        days = new boolean[]{true, true, true, true, true, false, false};
    }

    public void updateReferences() {
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

    public List<Subject> getAutocompleteOptions(String subject) {
        List<Subject> options = new ArrayList<Subject>();

        for (Subject s : references) {
            if (s.getSubject().toLowerCase().startsWith(subject.toLowerCase())) {
                options.add(s);
            }
        }

        this.options = options;

        return options;
    }

    public String getSubjectText(int i, int j) {
        return subjects[i][j].getSubject();
    }

    public String getRoomText(int i, int j) {
        return subjects[i][j].getRoom();
    }

    public String getTeacherText(int i, int j) {
        return subjects[i][j].getTeacher();
    }

    public void setSubjectText(String s, int i, int j) {
        subjects[i][j].setSubject(s);
    }

    public void setRoomText(String s, int i, int j) {
        subjects[i][j].setRoom(s);
    }

    public void setTeacherText(String s, int i, int j) {
        subjects[i][j].setTeacher(s);
    }

    public String getTimeText(int i) {
        return times[i].format(HoursAndMinutes);
    }

    public LocalTime getTime(int i) {
        return times[i];
    }

    public void setTime(LocalTime time, int i) {
        times[i] = time;
    }

    public boolean isDayDisplayed(int i) {
        return days[i];
    }

    public void setDayDisplayed(boolean day, int i) {
        days[i] = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLessons() {
        return lessons;
    }

    public void setLessons(int lessons) {
        this.lessons = lessons;
    }

    public Subject getOption(int i) {
        return options.get(i);
    }

    public void clearLessonRow(int index) {
        for (int i = 0; i < subjects.length; i++) {
            subjects[i][index] = new Subject();
        }
    }

    public void removeLessonRow(int index) {
        if (lessons > 1) {
            lessons--;
            for (int i = 0; i < subjects.length; i++) {
                for (int j = index; j < lessons - 1; j++) {
                    subjects[i][j] = subjects[i][j + 1];
                }
                subjects[i][lessons] = new Subject();
            }
        }
    }

    public void addLessonRowAbove(int index) {
        if (lessons < 10) {
            lessons++;
            for (int i = 0; i < subjects.length; i++) {
                for (int j = lessons - 1; j > index; j--) {
                    subjects[i][j] = subjects[i][j - 1];
                }
                subjects[i][index] = new Subject();
            }
        }
    }

    public void addLessonRowBelow(int index) {
        if (lessons < 10) {
            lessons++;
            for (int i = 0; i < subjects.length; i++) {
                for (int j = lessons - 1; j > index + 1; j--) {
                    subjects[i][j] = subjects[i][j - 1];
                }
                subjects[i][index + 1] = new Subject();
            }
        }
    }

    public void clearSubject(int indexI, int indexJ) {
        subjects[indexI][indexJ] = new Subject();
    }

    public void removeSubject(int indexI, int indexJ) {
        for (int j = indexJ; j < lessons - 1; j++) {
            subjects[indexI][j] = subjects[indexI][j + 1];
        }
        subjects[indexI][lessons - 1] = new Subject();
    }

    public void addSubjectAbove(int indexI, int indexJ) {
        for (int j = lessons - 1; j > indexJ; j--) {
            subjects[indexI][j] = subjects[indexI][j - 1];
        }
        subjects[indexI][indexJ] = new Subject();
    }

    public void addSubjectBelow(int indexI, int indexJ) {
        for (int j = lessons - 1; j > indexJ + 1; j--) {
            subjects[indexI][j] = subjects[indexI][j - 1];
        }
        subjects[indexI][indexJ + 1] = new Subject();
    }

}
