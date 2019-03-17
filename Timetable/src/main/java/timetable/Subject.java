/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import java.io.Serializable;

/**
 *
 * @author Tobias
 */
public class Subject implements Serializable {

    String subject = "";
    String room = "";
    String teacher = "";

    public Subject() {
    }

    public Subject(String subject) {
        this.subject = subject;
    }

    public Subject(String subject, String room) {
        this.subject = subject;
        this.room = room;
    }

    public Subject(String subject, String room, String teacher) {
        this.subject = subject;
        this.room = room;
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

}
