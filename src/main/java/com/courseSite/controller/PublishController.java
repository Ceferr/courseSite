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
                         @RequestParam(value = "type") String type,
                         @RequestParam(value = "fileID") Long fileID,
                         @RequestParam(value = "teacherID") Long teacherID,
                         HttpServletRequest request, HttpServletResponse response){
        Result result = publishServiceImpl.uploadPublish(files,type,fileID,teacherID);
        return result;
    }

    //下载作业或实践报告说明书
    @RequestMapping(method = RequestMethod.POST,value = "/download")
    @ResponseBody
    public Result download(@RequestParam(value = "fileID")Long fileID,
                           @RequestParam(value = "filename") String filename,
                           @RequestParam(value = "type") String type,
                           @RequestParam(value = "studentID")Long studentID,
                           HttpServletRequest request,HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        Result result = publishServiceImpl.downloadPublish(fileID,filename,outputStream,type,studentID);
        return result;
    }

    //删除作业或实践报告说明
    @RequestMapping(method = RequestMethod.POST,value = "/rmPublish")
    @ResponseBody
    public Result rmPublish(@RequestParam(value = "fileID") Long fileID,
                            @RequestParam(value = "filename") String filename,
                            @RequestParam(value = "type")String type,
                            @RequestParam(value = "teacherID")Long teacherID){
        Result result = publishServiceImpl.rmPublish(fileID,filename,type,teacherID);
        return result;
    }

    //分页查询作业或实践报告说明或课件
    @RequestMapping(method = RequestMethod.GET,value = "/getPublishByPage")
    @ResponseBody
    public Result getPublishByPage(@RequestParam(value = "type") String type,
                                   @RequestParam(value = "start")Integer start,
                                   @RequestParam(value = "size")Integer size){
        Result result = publishServiceImpl.getAllPublishByPage(type, start, size);
        return result;
    }

    //创建文件夹
    @RequestMapping(method = RequestMethod.POST,value = "/makeDir")
    @ResponseBody
    public Result makedir(@RequestParam(value = "type")String type,
                          @RequestParam(value = "num")Integer num,
                          @RequestParam(value = "teacherID")Long teacherID){
        Result result = publishServiceImpl.makeDir(type,num,teacherID);
        return result;
    }
}
