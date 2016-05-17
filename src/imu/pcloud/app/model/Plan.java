package imu.pcloud.app.model;

import imu.pcloud.app.utils.DateTool;

import java.sql.Time;
import java.util.Date;

public class Plan {
    String startTimeString;
    String endTimeString;
    String content;
    String title;

    public Plan() {

    }


    public Plan(String startTimeString, String endTimeString, String content,
                String title) {
        super();
        this.startTimeString = startTimeString;
        this.endTimeString = endTimeString;
        this.content = content;
        this.title = title;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public void setStartTimeString(Date date) {
        this.setStartTimeString(DateTool.timeToString((Time) date));
    }

    public void setEndTimeString(Date date) {
        this.setEndTimeString(DateTool.timeToString((Time) date));
    }
    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
