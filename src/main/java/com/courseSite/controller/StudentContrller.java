package com.courseSite.controller;


import com.courseSite.ResponseResult.Result;
import com.courseSite.pojo.Student;
import com.courseSite.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/student")
public class StudentContrller {

    @Autowired
    private StudentService studentServiceImpl;

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT},consumes="application/json;charset=UTF-8")
    @ResponseBody
    public Result addStudent(@RequestBody Student student){
        Result result = studentServiceImpl.addStudent(student);
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
    public  Result deleteStudent(@RequestParam(value = "studentID",defaultValue = "0") Long studentID){
        Result result = studentServiceImpl.deleteByStudentID(studentID);
        return result;
    }
}
