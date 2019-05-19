package com.courseSite.pojo;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "reportID")
    private Long reportID;

//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "studentID")
//    private Student student;
    @Column(name = "studentID")
    private Long studentID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public Report(String name, String path, Long reportID, Long studentID) {
        this.name = name;
        this.path = path;
        this.reportID = reportID;
        this.studentID = studentID;
    }

    public Report() {
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", reportID=" + reportID +
                ", studentID=" + studentID +
                '}';
    }
}
