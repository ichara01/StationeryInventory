package com.example.ioanna.reportcard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ioanna on 28/05/2017.
 */

public class ReportCard {

    public enum Grades {
        A,
        B,
        C,
        D,
        E,
        F
    }

    public enum Semester {
        First,
        Second,
        Third,
        Fourth,
        Fifth,
        Sixth,
        Seventh,
        Eighth,
        Nineth,
        Tenth
    }

    private String mStudentID;
    private HashMap<String, Grades> mStudentCourses = new HashMap<String, Grades>();
    private int mYear;
    private Semester mSemester;

    ReportCard(String stdID, int year, Semester sem) {
        this.mStudentID = stdID;
        this.mYear = year;
        this.mSemester = sem;
    }

    ReportCard(String stdID, int year, Semester sem, HashMap<String, Grades> coursesGrades) {
        this.mStudentID = stdID;
        this.mYear = year;
        this.mSemester = sem;
        this.mStudentCourses.putAll(coursesGrades);
    }

    public void setStudentID(String mStudentID) {
        this.mStudentID = mStudentID;
    }

    public void setYear(int mYear) {
        this.mYear = mYear;
    }

    public void setSemester(Semester mSemester) {
        this.mSemester = mSemester;
    }

    //Add/Update student course with its grade
    public void setCourseGrade(String courseCode, Grades grade) {

        mStudentCourses.put(courseCode, grade);
    }

    public String getStudentID() {
        return mStudentID;
    }

    public int getYear() {
        return mYear;
    }

    public Semester getSemester() {
        return mSemester;
    }

    public HashMap<String, Grades> getStudentCourses() {
        return mStudentCourses;
    }

    public Grades getStudentGradeByCourseCode(String courseCode) {
        boolean hasAttend = mStudentCourses.containsKey(courseCode);
        if (hasAttend)
            return mStudentCourses.get(courseCode);
        else
            return null;
    }

    @Override
    public String toString() {
        String s = "Studend ID: " + mStudentID + "\n";
        s = s + "Year: " + mYear + " Semester: " + mSemester + "\n";
        Set set = mStudentCourses.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry course = (Map.Entry) iterator.next();
            s += "Course Code: " + course.getKey() + " with " + course.getValue() + " grade \n";
        }
        return s;
    }
}
