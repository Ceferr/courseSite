package com.courseSite.pojo;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "teacherID")
    private Long teacherID;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "intro")
    private String intro;
    @Column(name = "sex")
    private String sex;

//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JoinColumn(name = "teacherID")
//    private List<Student> students;


//    public List<Student> getStudents() {
//        return students;
//    }
//
//    public void setStudents(List<Student> students) {
//        this.students = students;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Long teacherID) {
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", teacherID=" + teacherID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", intro='" + intro + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
