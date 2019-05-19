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

    private Result result = new Result();

    @Override
    public Result uploadHomeWorkorReport(MultipartFile[] files,String path,String type,Long fileID,Long studentID) {
        result.clear();
        try {
            for (MultipartFile file:files){
                String name = file.getOriginalFilename();
                File dir = new File(path);
                String storePath = path+"/"+name;
                Student student = studentDaoImpl.getByStudentID(studentID);
                if (!dir.exists()){
                    result.setFail(400,"文件夹不存在");
                    return result;
                }else if (student == null){
                    result.setFail(400,"该学生不存在");
                    return result;
                }else {

                    if (type.equals("homework")){
                        HomeWork_publish homeWork_publish = homeWork_publishDaoImpl.getByHomeWorkID(fileID);
                        if (homeWork_publish == null) {
                            result.setFail(400,"该作业未发布");
                            return result;
                        }else {
                            HomeWork homeWork = new HomeWork(name,storePath,fileID,studentID);
                            homeWorkDaoImpl.save(homeWork);
                            file.transferTo(new File(storePath));
                            result.setOK("上传成功",homeWork);
                        }
                    }else if (type.equals("report")){
                        Report_publish report_publish = report_publishDaoImpl.getByReportID(fileID);
                        if (report_publish == null) {
                            result.setFail(400,"该实践报告未发布");
                            return result;
                        }else {
                            Report report = new Report(name,storePath,fileID,studentID);
                            reportDaoImpl.save(report);
                            result.setOK("上传成功",report);
                            file.transferTo(new File(storePath));
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
    public Result makedir(String dirname) {
        result.clear();
        String root = Constant.HOMEWORK_ROOT;
        File dir = new File(root+dirname);
        if(!dir.exists()){
            dir.mkdir();
            result.setOK("创建成功",dir);
        }else {
            result.setFail(400,"文件已存在");
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

//    @Override
//    public Result upload(MultipartFile[] files, String dirname) {
//        result.clear();
//        try {
//            for (MultipartFile file:files){
//                //System.out.println(file.getOriginalFilename());
//                String name = file.getOriginalFilename();
//                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
//                String url = Constant.HOMEWORK_ROOT;
//                File dir = new File(url+dirname);
//                if (!dir.exists()) dir.mkdirs();
//                file.transferTo(new File(url+dirname+"/"+name));
//                result.setOK("上传成功",url+dirname+"/"+name);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    @Override
    public Result download(String filename, String path,OutputStream outputStream) {
        result.clear();
        result = Util.download(filename,path,outputStream);
        System.out.println(result.getMessage());
        return result;
    }
}
