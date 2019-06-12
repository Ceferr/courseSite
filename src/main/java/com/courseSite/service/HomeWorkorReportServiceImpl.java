package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.constant.Constant;
import com.courseSite.dao.*;
import com.courseSite.pojo.*;
import com.courseSite.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeWorkorReportServiceImpl implements HomeWorkorReportService{

    @Autowired
    private HomeWorkDao homeWorkDaoImpl;
    @Autowired
    private ReportDao reportDaoImpl;
    @Autowired
    private HomeWork_publishDao homeWork_publishDaoImpl;
    @Autowired
    private Report_publishDao report_publishDaoImpl;
    @Autowired
    private StudentDao studentDaoImpl;
    @Autowired
    private HomeWork_uploadDao homeWork_uploadDaoImpl;
    @Autowired
    private Report_uploadDao report_uploadDaoImpl;

    private Result result = new Result();

    @Override
    public Result uploadHomeWorkorReport(MultipartFile[] files,String type,Long fileID,Long studentID) {
        result.clear();
        try {
            for (MultipartFile file:files){
                String name = file.getOriginalFilename();
                //Teacher teacher = teacherDaoImpl.getByTeacherID(studentDaoImpl.getByStudentID(studentID).getTeacherID());
                String path = Constant.ROOT+studentDaoImpl.getByStudentID(studentID).getTeacherID()+"/"+type+"/"+"第"+fileID+"次";
                File dir = new File(path);
                String storePath = path+"/"+name;
                Student student = studentDaoImpl.getByStudentID(studentID);
                if (!dir.exists()){
                    result.setFail(111,"文件夹不存在");
                    return result;
                }else if (student == null){
                    result.setFail(400,"该学生不存在");
                    return result;
                }else {
                    if (type.equals("homework")){
                        HomeWork_publish homeWork_publish = homeWork_publishDaoImpl.getByHomeWorkID(fileID);
                        if (homeWork_publish == null) {
                            result.setFail(115,"该作业未发布");
                            return result;
                        }else {
                            HomeWork homeWork = new HomeWork(name,storePath,fileID,studentID);
                            homeWorkDaoImpl.save(homeWork);
                            HomeWork_upload homeWork_upload = new HomeWork_upload(storePath,studentID);
                            homeWork_uploadDaoImpl.save(homeWork_upload);
                            file.transferTo(new File(storePath));
                            result.setOK("上传成功",homeWork);
                        }
                    }else if (type.equals("report")){
                        Report_publish report_publish = report_publishDaoImpl.getByReportID(fileID);
                        if (report_publish == null) {
                            result.setFail(115,"该实践报告未发布");
                            return result;
                        }else {
                            Report report = new Report(name,storePath,fileID,studentID);
                            reportDaoImpl.save(report);
                            Report_upload report_upload = new Report_upload(storePath,studentID);
                            report_uploadDaoImpl.save(report_upload);
                            file.transferTo(new File(storePath));
                            result.setOK("上传成功",report);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public Result removefile(String filename,String path,String type) {
        result.clear();
        String storePath = path +"/"+ filename;
        File targetFile = new File(storePath);
        if (!targetFile.exists()) {
            result.setFail(400,"文件不存在");
            return result;
        }else {
            if (type.equals("homework")){
                homeWorkDaoImpl.delete(storePath);
            }else if (type.equals("report")){
                reportDaoImpl.delete(storePath);
            }
            Util.remove(storePath);
            result.setOK("删除成功",storePath);
        }
        return result;
    }

    @Override
    public Result download(Long fileID,String filename, String type,Long teacherID,OutputStream outputStream) {
        result.clear();
        String path = Constant.ROOT+teacherID+"/"+type+"/"+"第"+fileID+"次";
        result = Util.download(filename,path,outputStream);
        System.out.println(result.getMessage());
        return result;
    }

    @Override
    public Result getAllHomeWorkOrReportByPage(Long fileID,String type, Integer start, Integer size) {
        result.clear();
        Long count = 0l;
        List lists = new ArrayList();
        if (type.equals("homework")){
            List<HomeWork> homeWorks = homeWorkDaoImpl.findByPage("homeworkID",fileID,start,size);
            for (HomeWork homeWork : homeWorks){
                lists.add(homeWork);
            }
            count = homeWorkDaoImpl.getCount();
        }else if (type.equals("report")){
            List<Report> reports = reportDaoImpl.findByPage("reportID",fileID,start,size);
            for (Report report: reports){
                lists.add(report);
            }
            count = reportDaoImpl.getCount();
        }
        result.setOK(count.toString(),lists);
        return result;
    }
}
