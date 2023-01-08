package com.example.termtracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey
    private int courseId;
    private String courseName;
    private String courseStartDate;
    private String courseEndDate;
    private String courseStatus;
    private String courseNotes;
    private int  courseTermId;

    public Course(int courseId, String courseName, String courseStartDate, String courseEndDate, String courseStatus, String courseNotes, int courseTermId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseNotes = courseNotes;
        this.courseTermId = courseTermId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public int getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(int courseTermId) {
        this.courseTermId = courseTermId;
    }
}
