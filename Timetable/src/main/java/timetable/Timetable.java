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
    LocalTime startTime = LocalTime.of(7, 30);
    int smallPause = 0;
    int middlePause = 15;
    int bigPause = 30;
    int lessonlength = 45;

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

        initTimes();

        days = new boolean[]{true, true, true, true, true, false, false};
    }

    private void initTimes() {
        int toAdd = 0;
        for (int i = 0; i < times.length; i++) {
            times[i] = startTime.plusMinutes(toAdd);
            toAdd += lessonlength;
            if (i % 2 == 0) {
                toAdd += smallPause;
            }
            if (i % 2 == 1 && i != 5) {
                toAdd += middlePause;
            }
            if (i == 5) {
                toAdd += bigPause;
            }
        }
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
        return times[i].format(HoursAndMinutes) + "\n" + times[i].plusMinutes(lessonlength).format(HoursAndMinutes);
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getSmallPause() {
        return smallPause;
    }

    public void setSmallPause(int smallPause) {
        this.smallPause = smallPause;
    }

    public int getMiddlePause() {
        return middlePause;
    }

    public void setMiddlePause(int middlePause) {
        this.middlePause = middlePause;
    }

    public int getBigPause() {
        return bigPause;
    }

    public void setBigPause(int bigPause) {
        this.bigPause = bigPause;
    }

    public int getLessonlength() {
        return lessonlength;
    }

    public void setLessonlength(int lessonlength) {
        this.lessonlength = lessonlength;
    }

    public void clearLessonRow(int index) {
        for (int i = 0; i < subjects.length; i++) {
            subjects[i][index] = new Subject();
        }
    }

    public void deleteLessonRow(int index) {
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

    public void deleteSubject(int indexI, int indexJ) {
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

    public void moveSubjectUp(int indexI, int indexJ) {
        if (indexJ > 0) {
            switchSubjects(indexI, indexJ, indexI, indexJ - 1);
        }
    }

    public void moveSubjectDown(int indexI, int indexJ) {
        if (indexJ < lessons - 1) {
            switchSubjects(indexI, indexJ, indexI, indexJ + 1);
        }
    }

    public void moveSubjectLeft(int indexI, int indexJ) {
        if (indexI > 0) {
            for (int i = indexI - 1; i >= 0; i--) {
                if (days[i]) {
                    switchSubjects(indexI, indexJ, i, indexJ);
                    break;
                }
            }
        }
    }

    public void moveSubjectRight(int indexI, int indexJ) {
        int day = 0;
        for (int d = 0; d < days.length; d++) {
            if (isDayDisplayed(d)) {
                day++;
            }
        }
        if (indexI < day) {
            for (int i = indexI + 1; i < days.length; i++) {
                if (days[i]) {
                    switchSubjects(indexI, indexJ, i, indexJ);
                    break;
                }
            }
        }
    }

    public void switchSubjects(int i1, int j1, int i2, int j2) {
        Subject temp = subjects[i1][j1];
        subjects[i1][j1] = subjects[i2][j2];
        subjects[i2][j2] = temp;
    }

}
