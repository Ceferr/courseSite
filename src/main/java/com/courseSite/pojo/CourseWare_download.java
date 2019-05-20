package com.courseSite.pojo;

import javax.persistence.*;

@Entity
@Table(name = "courseWare_download")
public class CourseWare_download {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "studentID")
    private Long studentID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public CourseWare_download(String path, Long studentID) {
        this.path = path;
        this.studentID = studentID;
    }

    public CourseWare_download() {
    }

    @Override
    public String toString() {
        return "HomeWork_publish_download{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", studentID=" + studentID +
                '}';
    }
}
