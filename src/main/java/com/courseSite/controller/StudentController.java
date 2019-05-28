package com.courseSite.controller;


import com.courseSite.ResponseResult.Result;
import com.courseSite.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@Controller
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    private StudentService studentServiceImpl;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result addStudent(@RequestParam(value = "studentID") Long studentID,
                             @RequestParam(value = "name")String name,
                             @RequestParam(value = "password")String password,
                             @RequestParam(value = "sex")String sex,
                             @RequestParam(value = "teacherID")Long teacherID){
        Result result = studentServiceImpl.addStudent(studentID,name,password,sex,teacherID);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/importStudent")
    @ResponseBody
    public Result importStudent(@RequestParam(value = "file")MultipartFile file){
        Result result = studentServiceImpl.importFromExcel(file);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/exportStudent")
    @ResponseBody
    public Result exportStudent(@RequestParam(value = "teacherID")Long teacherID,
                                HttpServletRequest request, HttpServletResponse response)throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename= " + teacherID+".xlsx");
        Result result = studentServiceImpl.exportToExcel(teacherID,outputStream);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Result getStudent(@RequestParam(value = "studentID",defaultValue = "0") Long studentID){
        Result result = studentServiceImpl.getByStudentID(studentID);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/getAllStudent")
    @ResponseBody
    public Result getAllStudent(){
        Result result = studentServiceImpl.getAllStudent();
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/getAllStudentByPage")
    @ResponseBody
    public Result getAllStudentByPage(@RequestParam(value = "start") Integer start,
                                      @RequestParam(value = "size")Integer size){
        Result result = studentServiceImpl.getAllStudentByPage(start,size);
        return result;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public  Result deleteStudent(@RequestParam(value = "studentID") Long studentID){
        Result result = studentServiceImpl.deleteByStudentID(studentID);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/updatePassword")
    @ResponseBody
    public Result updatePassword(@RequestParam(value = "studentID")Long studentID,
                                 @RequestParam(value = "originalPassword")String originalPassword,
                                 @RequestParam(value = "password")String password){
        Result result = studentServiceImpl.updatePassword(studentID, originalPassword, password);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestParam(value = "studentID")Long studentID,
                                 @RequestParam(value = "name")String name,
                                 @RequestParam(value = "sex")String sex){
        Result result = studentServiceImpl.updateInfo(studentID,name,sex);
        return result;
    }
}
