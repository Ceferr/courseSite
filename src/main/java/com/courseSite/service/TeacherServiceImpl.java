package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.StudentDao;
import com.courseSite.dao.TeacherDao;
import com.courseSite.pojo.Student;
import com.courseSite.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDaoImpl;
    @Autowired
    private StudentDao studentDaoImpl;

    private Result result = new Result();

    @Override
    public Result addTeacher(Teacher teacher) {
        result.clear();
        if (teacher.getTeacherID().equals("")){
            result.setFail(101,"未填写教师号");
            return result;
        }
        Teacher testTeacher = teacherDaoImpl.getByTeacherID(teacher.getTeacherID());
        if (testTeacher!=null){
            result.setFail(109,"该教师已存在");
            return result;
        }
        teacherDaoImpl.save(teacher);
        result.setOK("添加成功",teacher);
        return result;
    }

    @Override
    public Result deleteByTeacherID(Long teacherID) {
        result.clear();
        Teacher teacher = teacherDaoImpl.getByTeacherID(teacherID);
        if (teacher == null){
            result.setFail(110,"该教师不存在");
            return result;
        }else {
            List<Student> students = studentDaoImpl.get(teacherID,"teacherID");
            for (Student student:students){
                studentDaoImpl.deleteByStudentID(student.getStudentID());
            }
            teacherDaoImpl.delete(teacher.getId());
            result.setOK("删除成功",teacher);
        }
        return result;
    }

    @Override
    public Result getByTeacherID(Long teacherID) {
        result.clear();
        Teacher teacher = teacherDaoImpl.getByTeacherID(teacherID);
        if (teacher != null){
            result.setOK("查询成功",teacher);
            return result;
        }else {
            result.setFail(110,"该教师不存在");
            return result;
        }
    }

    @Override
    public Result updatePassword(Long teacherID, String originalPassword, String password) {
        result.clear();
        Teacher teacher = teacherDaoImpl.getByTeacherID(teacherID);
        if (!originalPassword.equals(teacher.getPassword())){
            result.setFail(105,"原密码错误");
            return result;
        }else {
            teacherDaoImpl.updatePassword(teacherID,password);
            result.setOK("密码修改成功",password);
        }
        return result;
    }

    @Override
    public Result updateInfo(Long teacherID, String name, String sex) {
        result.clear();
        teacherDaoImpl.updateInfo(teacherID,name,sex);
        result.setOK("信息修改成功",name+" "+sex);
        return result;
    }
}
