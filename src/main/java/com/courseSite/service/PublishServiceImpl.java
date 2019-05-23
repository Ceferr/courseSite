package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.constant.Constant;
import com.courseSite.dao.*;
import com.courseSite.pojo.*;
import com.courseSite.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private TeacherDao teacherDaoImpl;
    @Autowired
    private StudentDao studentDaoImpl;
    @Autowired
    private HomeWork_publishDao homeWork_publishDaoImpl;
    @Autowired
    private HomeWorkDao homeWorkDaoImpl;
    @Autowired
    private Report_publishDao report_publishDaoImpl;
    @Autowired
    private ReportDao reportDaoImpl;
    @Autowired
    private CourseWareDao courseWareDaoImpl;
    @Autowired
    private HomeWork_publish_downloadDao homeWork_publish_downloadDaoImpl;
    @Autowired
    private Report_publish_downloadDao report_publish_downloadDaoImpl;
    @Autowired
    private CourseWare_downloadDao courseWare_downloadDaoImpl;
    @Autowired
    private HomeWorkorReportService homeWorkorReportServiceImpl;

    private Result result = new Result();

    @Override
    public Result uploadPublish(MultipartFile[] files,String type, Long fileID, Long teacherID) {
        result.clear();
        Teacher teacher = teacherDaoImpl.getByTeacherID(teacherID);
        if (teacher == null){
            result.setFail(102,"该教师不存在");
            return result;
        }
        try {
            for (MultipartFile file:files){
                String name = file.getOriginalFilename();
                if (type.equals("courseware")){
                   String path = Constant.ROOT+teacherID+"/"+type;
                   File dir = new File(path);
                   String storePath = path+"/"+name;
                   if (!dir.exists()) {
                       result.setFail(111, "文件夹不存在");
                       return result;
                   }else {
                       if(!courseWareDaoImpl.get(fileID,"courseWareID").isEmpty()){
                           result.setFail(112,"文件号重复");
                           return result;
                       }
                       CourseWare courseWare = new CourseWare(fileID,name,storePath,0L,teacherID);
                       file.transferTo(new File(storePath));
                       courseWareDaoImpl.save(courseWare);
                       result.setOK("上传成功",storePath);
                   }
                }else{
                    String path = Constant.ROOT+teacherID+"/"+type+"/"+"第"+fileID+"次";
                    File dir = new File(path);
                    String storePath = path+"/"+name;
                    System.out.println(storePath);
                    if (!dir.exists()){
                        result.setFail(111,"文件夹不存在");
                        return result;
                    }else {

                        if (type.equals("homework")){
                            if(!homeWork_publishDaoImpl.get(fileID,"homeWorkID").isEmpty()){
                                result.setFail(112,"文件号重复");
                                return result;
                            }
                            HomeWork_publish homeWork_publish = new HomeWork_publish(fileID,name,storePath,0L,teacherID);
                            file.transferTo(new File(storePath));
                            homeWork_publishDaoImpl.save(homeWork_publish);
                            result.setOK("上传成功",storePath);
                        }else if (type.equals("report")){
                            if(!report_publishDaoImpl.get(fileID,"reportID").isEmpty()){
                                result.setFail(112,"文件号重复");
                                return result;
                            }
                            Report_publish report_publish = new Report_publish(fileID,name,storePath,0L,teacherID);
                            file.transferTo(new File(storePath));
                            report_publishDaoImpl.save(report_publish);
                            result.setOK("上传成功",storePath);
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
    public Result downloadPublish(Long fileID, String filename,OutputStream outputStream,String type,Long studentID) {
        result.clear();
        String path = null;
        Long teacherID = studentDaoImpl.getByStudentID(studentID).getTeacherID();
        if (type.equals("courseware")){
            path = Constant.ROOT+teacherID+"/"+type;
            CourseWare courseWare = courseWareDaoImpl.getByCourseWareID(fileID);
            filename = courseWare.getName();
            String storePath = path+"/"+filename;
            System.out.println(storePath);
            System.out.println(filename);
            File tempFile = new File(storePath);
            if (!tempFile.exists()){
                result.setFail(113,"文件不存在");
            }else {
                Long downloadCount = courseWare.getDownloadCount();
                downloadCount++;
                courseWare.setDownloadCount(downloadCount);
                System.out.println(downloadCount);
                courseWareDaoImpl.updateDownloadCount(courseWare.getId(),downloadCount);
                CourseWare_download courseWare_download = new CourseWare_download(storePath,studentID);
                courseWare_downloadDaoImpl.save(courseWare_download);
            }
        }else {
            path = Constant.ROOT+teacherID+"/"+type+"/"+"第"+fileID+"次";

                if (type.equals("homework")){
                    HomeWork_publish homeWork_publish = homeWork_publishDaoImpl.getByHomeWorkID(fileID);
                    filename = homeWork_publish.getName();
                    String storePath = path+"/"+filename;
                    File tempFile = new File(storePath);
                    if (!tempFile.exists()){
                        result.setFail(113,"文件不存在");
                    }else {
                        Long downloadCount = homeWork_publish.getDownloadCount();
                        downloadCount++;
                        homeWork_publish.setDownloadCount(downloadCount);
                        System.out.println(downloadCount);
                        homeWork_publishDaoImpl.updateDownloadCount(homeWork_publish.getId(),downloadCount);
                        HomeWork_publish_download homeWork_publish_download = new HomeWork_publish_download(storePath,studentID);
                        homeWork_publish_downloadDaoImpl.save(homeWork_publish_download);
                    }
                }else if (type.equals("report")){
                    Report_publish report_publish = report_publishDaoImpl.getByReportID(fileID);
                    filename = report_publish.getName();
                    String storePath = path+"/"+filename;
                    File tempFile = new File(storePath);
                    if (!tempFile.exists()){
                        result.setFail(113,"文件不存在");
                    }else {
                        Long downloadCount = report_publish.getDownloadCount();
                        downloadCount++;
                        report_publish.setDownloadCount(downloadCount);
                        System.out.println(downloadCount);
                        report_publishDaoImpl.updateDownloadCount(report_publish.getId(),downloadCount);
                        Report_publish_download report_publish_download = new Report_publish_download(storePath,studentID);
                        report_publish_downloadDaoImpl.save(report_publish_download);
                    }
                }
            }
            result = Util.download(filename,path,outputStream);
        return result;
    }

    @Override
    public Result rmPublish(Long fileID, String filename, String type,Long teacherID) {
        result.clear();
        String path = null;
        String storePath = null;
        if (type.equals("courseware")){
            path = Constant.ROOT +teacherID+"/"+ type;
            storePath = path +"/"+ filename;
            File targetFile = new File(storePath);
            if (!targetFile.exists()) {
                result.setFail(113,"文件不存在");
                return result;
            }
        }else {
            path = Constant.ROOT +teacherID+"/"+ type +"/"+ "第"+fileID+"次";
            storePath = path +"/"+filename;
            File targetFile = new File(storePath);
            if (!targetFile.exists()) {
                result.setFail(113,"文件不存在");
                return result;
            }
        }

            if (type.equals("homework")){
                List<HomeWork> homeWorks = homeWorkDaoImpl.get(homeWork_publishDaoImpl.get(storePath).getHomeWorkID(),"homeWorkID");
                if (homeWorks!=null){
                    for (HomeWork homeWork :homeWorks){
                        homeWorkorReportServiceImpl.removefile(homeWork.getName(),homeWork.getPath().replaceAll("/"+homeWork.getName(),""),"homework");
                    }
                }
                if (homeWork_publish_downloadDaoImpl.get(storePath,"path")!=null){
                    homeWork_publish_downloadDaoImpl.delete(storePath);
                }
                homeWork_publish_downloadDaoImpl.delete(storePath);
                homeWork_publishDaoImpl.delete(storePath);
            }else if (type.equals("report")){
                List<Report> reports = reportDaoImpl.get(report_publishDaoImpl.get(storePath).getReportID(),"reportID");
                if (reports!=null){
                    for (Report report:reports){
                        homeWorkorReportServiceImpl.removefile(report.getName(),report.getPath().replaceAll("/"+report.getName(),""),"report");
                    }
                }
                if (report_publish_downloadDaoImpl.get(storePath,"path")!=null){
                    report_publish_downloadDaoImpl.delete(storePath);
                }
                report_publishDaoImpl.delete(storePath);
            }else if (type.equals("courseware")){
                if (courseWare_downloadDaoImpl.get(storePath,"path")!=null){
                    courseWare_downloadDaoImpl.delete(storePath);
                }
                courseWareDaoImpl.delete(storePath);
            }
            Util.remove(storePath);
            result.setOK("删除成功",storePath);

        return result;
    }

    @Override
    public Result getAllPublishByPage(String type, Integer start, Integer size) {
        result.clear();
        Long count = 0l;
        List lists = new ArrayList();
        if (type.equals("homework")){
            List<HomeWork_publish> homeWork_publishes = homeWork_publishDaoImpl.findAllByPage(start,size);
            for (HomeWork_publish homeWork_publish : homeWork_publishes){
                lists.add(homeWork_publish);
            }
            count = homeWork_publishDaoImpl.getCount();
        }else if (type.equals("report")){
            List<Report_publish> report_publishes = report_publishDaoImpl.findAllByPage(start,size);
            for (Report_publish report_publish : report_publishes){
                lists.add(report_publish);
            }
            count = report_publishDaoImpl.getCount();
        }else if (type.equals("courseware")){
            List<CourseWare> courseWares = courseWareDaoImpl.findAllByPage(start,size);
            for (CourseWare courseWare : courseWares){
                lists.add(courseWare);
            }
            count = courseWareDaoImpl.getCount();
        }
        result.setOK(count.toString(),lists);
        return result;
    }

    @Override
    public Result makeDir(String type, Integer num,Long teacherID) {
        result.clear();
        String path = Constant.ROOT+teacherID+"/"+type;
        String dirname = path +"/"+ "第"+num+"次";
        File file = new File(dirname);
        if (file.exists()){
            result.setFail(116,"该文件夹已存在");
            return result;
        }else {
            file.mkdir();
            result.setOK("文件夹创建成功",dirname);
        }
        return result;
    }
}
