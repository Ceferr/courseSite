package com.courseSite.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "homework")
public class HomeWork {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "homeworkID")
    private Long homeWorkID;

//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "studentID")
//    private Student student;
    @Column(name = "studentID")
    private Long studentID;


    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

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

    public Long getHomeWorkID() {
        return homeWorkID;
    }

    public void setHomeWorkID(Long homeWorkID) {
        this.homeWorkID = homeWorkID;
    }


    public HomeWork(String name, String path, Long homeWorkID, Long studentID) {
        this.name = name;
        this.path = path;
        this.homeWorkID = homeWorkID;
        this.studentID = studentID;
    }

    public HomeWork() {
    }

    @Override
    public String toString() {
        return "HomeWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", homeWorkID=" + homeWorkID +
                ", studentID=" + studentID +
                '}';
    }
}
