package com.courseSite.controller;

import com.courseSite.ResponseResult.Result;
import com.courseSite.pojo.Teacher;
import com.courseSite.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherServiceImpl;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result addStudent(@RequestParam(value = "teacherID") Long teacherID,
                             @RequestParam(value = "name")String name,
                             @RequestParam(value = "password")String password,
                             @RequestParam(value = "sex")String sex){
        Teacher teacher = new Teacher(teacherID,name,password,sex);
        Result result = teacherServiceImpl.addTeacher(teacher);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Result getStudent(@RequestParam(value = "teacherID",defaultValue = "0") Long teacherID){
        Result result = teacherServiceImpl.getByTeacherID(teacherID);
        return result;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public  Result deleteStudent(@RequestParam(value = "teacherID") Long teacherID){
        Result result = teacherServiceImpl.deleteByTeacherID(teacherID);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/updatePassword")
    @ResponseBody
    public Result updatePassword(@RequestParam(value = "teacherID")Long teacherID,
                                 @RequestParam(value = "originalPassword")String originalPassword,
                                 @RequestParam(value = "password")String password){
        Result result = teacherServiceImpl.updatePassword(teacherID, originalPassword, password);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestParam(value = "teacherID")Long teacherID,
                             @RequestParam(value = "name")String name,
                             @RequestParam(value = "sex")String sex){
        Result result = teacherServiceImpl.updateInfo(teacherID, name, sex);
        return result;
    }
}
