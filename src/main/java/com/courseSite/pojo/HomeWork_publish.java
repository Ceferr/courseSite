package com.courseSite.pojo;

import javax.persistence.*;

@Entity
@Table(name = "homework_publish",uniqueConstraints = {@UniqueConstraint(columnNames = {"homeworkID"})})
public class HomeWork_publish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "homeworkID")
    private Long homeWorkID;

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

    public Long getHomeWorkID() {
        return homeWorkID;
    }

    public void setHomeWorkID(Long homeWorkID) {
        this.homeWorkID = homeWorkID;
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

    public HomeWork_publish(Long homeWorkID, String name, String path, Long downloadCount, Long teacherID) {
        this.homeWorkID = homeWorkID;
        this.name = name;
        this.path = path;
        this.downloadCount = downloadCount;
        this.teacherID = teacherID;
    }

    public HomeWork_publish() {
    }

    @Override
    public String toString() {
        return "HomeWork{" +
                "id=" + id +
                ", homeWorkID=" + homeWorkID +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", downloadCount=" + downloadCount +
                ", teacherID=" + teacherID +
                '}';
    }
}
