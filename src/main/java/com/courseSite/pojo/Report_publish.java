package com.courseSite.pojo;

import javax.persistence.*;

@Entity
@Table(name = "report_publish")
public class Report_publish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reportID")
    private Long reportID;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "downloadCount")
    private Long downloadCount;

    @Column(name = "teacherID")
    private Long teacherID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
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

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Long teacherID) {
        this.teacherID = teacherID;
    }

    public Report_publish(Long reportID, String name, String path, Long downloadCount, Long teacherID) {
        this.reportID = reportID;
        this.name = name;
        this.path = path;
        this.downloadCount = downloadCount;
        this.teacherID = teacherID;
    }

    public Report_publish() {
    }

    @Override
    public String toString() {
        return "Report_publish{" +
                "id=" + id +
                ", reportID=" + reportID +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", downloadCount=" + downloadCount +
                ", teacherID=" + teacherID +
                '}';
    }
}
