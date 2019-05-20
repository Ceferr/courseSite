package com.courseSite.controller;

import com.courseSite.ResponseResult.Result;
import com.courseSite.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping(value = "/publish")
public class PublishController {

    @Autowired
    private PublishService publishServiceImpl;

    //上传作业或实践报告说明
    @RequestMapping(method = RequestMethod.POST,value = "/upload")
    @ResponseBody
    public Result upload(@RequestParam(value = "files") MultipartFile[] files,
                         @RequestParam(value = "path") String path,
                         @RequestParam(value = "type") String type,
                         @RequestParam(value = "fileID") Long fileID,
                         @RequestParam(value = "teacherID") Long teacherID,
                         HttpServletRequest request, HttpServletResponse response){
        Result result = publishServiceImpl.uploadPublish(files,path,type,fileID,teacherID);
        return result;
    }

    //下载作业或实践报告说明书
    @RequestMapping(method = RequestMethod.POST,value = "/download")
    @ResponseBody
    public Result download(@RequestParam(value = "filename")String filename,
                           @RequestParam(value = "dirname") String dirname,
                           @RequestParam(value = "type") String type,
                           @RequestParam(value = "studentID")Long studentID,
                           HttpServletRequest request,HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        Result result = publishServiceImpl.downloadPublish(filename,dirname,outputStream,type,studentID);
        return result;
    }

    //删除作业或实践报告说明
    @RequestMapping(method = RequestMethod.POST,value = "/rmPublish")
    @ResponseBody
    public Result rmPublish(@RequestParam(value = "filename") String filename,
                            @RequestParam(value = "path")String path,
                            @RequestParam(value = "type")String type){
        Result result = publishServiceImpl.rmPublish(filename,path,type);
        return result;
    }
}
