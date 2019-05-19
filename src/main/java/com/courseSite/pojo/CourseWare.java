package com.courseSite.pojo;

import javax.persistence.*;

@Entity
@Table(name = "courseware")
public class CourseWare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "downloadCount")
    private Long downloadCount;

    @Column(name = "courseWareID")
    private Long courseWareID;

    @Column(name = "teacherID")
    private Long teacherID;

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

    public Long getCourseWareID() {
        return courseWareID;
    }

    public void setCourseWareID(Long courseWareID) {
        this.courseWareID = courseWareID;
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

    public CourseWare(Long courseWareID,String name, String path,  Long downloadCount,Long teacherID) {
        this.name = name;
        this.path = path;
        this.downloadCount = downloadCount;
        this.courseWareID = courseWareID;
        this.teacherID = teacherID;
    }

    public CourseWare() {
    }

    @Override
    public String toString() {
        return "CourseWare{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", courseWareID=" + courseWareID +
                ", teacherID=" + teacherID +
                '}';
    }
}
