/*
 * This is property of Tobias Schmitz GmbH Eitorf
 * 
 */
package timetable.Datatypes;

import java.io.Serializable;

/**
 *
 * @author tobi
 */
public class SimpleTime implements Serializable{

    int hours = 0;
    int minutes = 0;

    public SimpleTime(int hours, int minutes) {
        setHours(hours);
        setMinutes(minutes);
    }

    public SimpleTime(int hours) {
        this.hours = hours;
    }

    public SimpleTime() {
        
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        if (hours > 59) {
            this.hours = 59;
        } else if (hours < 0) {
            this.hours = 0;
        } else {
            this.hours = hours;
        }
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (minutes > 59) {
            this.minutes = 59;
        } else if (minutes < 0) {
            this.minutes = 0;
        } else {
            this.minutes = minutes;
        }
    }

    public String format() {
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
    }

    public SimpleTime plusMinutes(int minutesToAdd) {
        int hours = this.hours + (minutesToAdd + this.minutes) / 60;
        int minutes = (this.minutes + minutesToAdd) % 60;
        return new SimpleTime(hours, minutes);
    }

}
