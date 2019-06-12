package com.courseSite.controller;

import com.courseSite.ResponseResult.Result;
import com.courseSite.service.HomeWorkorReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping(value = "/homeWorkOrReport")
public class HomeWorkorReportController {

    @Autowired
    private HomeWorkorReportService homeWorkServiceImpl;


    //删除文件
    @RequestMapping(method = RequestMethod.POST,value = "/rmfile")
    @ResponseBody
    public Result removedir(@RequestParam(value = "filename")String filename,
                            @RequestParam(value = "path")String path,
                            @RequestParam(value = "type")String type){
        Result result = homeWorkServiceImpl.removefile(filename,path,type);
        return result;
    }


    //上传文件
    @RequestMapping(method = RequestMethod.POST,value = "/upload")
    @ResponseBody
    public Result upload(@RequestParam(value = "files") MultipartFile[] files,
                         @RequestParam(value = "type") String type,
                         @RequestParam(value = "fileID") Long fileID,
                         @RequestParam(value = "studentID") Long studentID,
                         HttpServletRequest request, HttpServletResponse response){
        Result result = homeWorkServiceImpl.uploadHomeWorkorReport(files,type,fileID,studentID);
        return result;
    }

    //下载文件
    @RequestMapping(method = RequestMethod.POST,value = "/download")
    @ResponseBody
    public Result download(@RequestParam(value = "fileID") Long fileID,
                           @RequestParam(value = "filename")String filename,
                           @RequestParam(value = "type") String type,
                           @RequestParam(value = "teacherID") Long teacherID,
                           HttpServletRequest request,HttpServletResponse response) throws IOException{
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        Result result = homeWorkServiceImpl.download(fileID,filename,type,teacherID,outputStream);
        return result;
    }

    //分页查询上交的作业或实践报告
    @RequestMapping(method = RequestMethod.GET,value = "/getHomeWorkOrReportByPage")
    @ResponseBody
    public Result getHomeWorkOrReportByPage(@RequestParam(value = "fileID") Long fileID,
                                            @RequestParam(value = "type") String type,
                                            @RequestParam(value = "start")Integer start,
                                            @RequestParam(value = "size")Integer size){
        Result result = homeWorkServiceImpl.getAllHomeWorkOrReportByPage(fileID,type, start, size);
        return result;
    }

}
