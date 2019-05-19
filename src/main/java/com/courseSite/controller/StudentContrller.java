package com.courseSite.controller;


import com.courseSite.ResponseResult.Result;
import com.courseSite.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/student")
public class StudentContrller {

    @Autowired
    private StudentService studentServiceImpl;

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT})
    @ResponseBody
    public Result addStudent(@RequestParam(value = "studentID") Long studentID,
                             @RequestParam(value = "name")String name,
                             @RequestParam(value = "password")String password,
                             @RequestParam(value = "sex")String sex,
                             @RequestParam(value = "teacherID")Long teacherID){
        Result result = studentServiceImpl.addStudent(studentID,name,password,sex,teacherID);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Result getStudent(@RequestParam(value = "studentID",defaultValue = "0") Long studentID){
        Result result = studentServiceImpl.getByStudentID(studentID);
        return result;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public  Result deleteStudent(@RequestParam(value = "studentID") Long studentID){
        Result result = studentServiceImpl.deleteByStudentID(studentID);
        return result;
    }
}
