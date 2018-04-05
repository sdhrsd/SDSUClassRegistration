package com.herasiddiqui.sdsuclassregistration;

/**
 * Created by admin on 4/2/18.
 */

public class ClassDetails {

    private String description;
    private String department;
    private String suffix;
    private String building;
    private String startTime;
    private String meetingType;
    private String section;
    private String endTime;
    private int enrolled;
    private String days;
    private String prerequisite;
    private String title;
    private int id;
    private String instructor;
    private String scheduleNo;
    private String units;
    private String room;
    private int waitlist;
    private int seats;
    private String fullTitle;
    private String subject;
    private String courseNo;
    private boolean studentEnrolled = false;

//    public ClassDetails(String description, String department, String suffix, String building, String startTime, String meetingType, String section, String endTime, int enrolled, String days, String prerequisite, String title, int id, String instructor, String scheduleNo, String units, String room, int waitlist, int seats, String fullTitle, String subject, String courseNo) {
//        this.description = description;
//        this.department = department;
//        this.suffix = suffix;
//        this.building = building;
//        this.startTime = startTime;
//        this.meetingType = meetingType;
//        this.section = section;
//        this.endTime = endTime;
//        this.enrolled = enrolled;
//        this.days = days;
//        this.prerequisite = prerequisite;
//        this.title = title;
//        this.id = id;
//        this.instructor = instructor;
//        this.scheduleNo = scheduleNo;
//        this.units = units;
//        this.room = room;
//        this.waitlist = waitlist;
//        this.seats = seats;
//        this.fullTitle = fullTitle;
//        this.subject = subject;
//        this.courseNo = courseNo;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(int waitlist) {
        this.waitlist = waitlist;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public boolean isStudentEnrolled() {
        return studentEnrolled;
    }

    public void setStudentEnrolled(boolean studentEnrolled) {
        this.studentEnrolled = studentEnrolled;
    }
}
