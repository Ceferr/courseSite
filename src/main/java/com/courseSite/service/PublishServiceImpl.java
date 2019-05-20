package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.*;
import com.courseSite.pojo.*;
import com.courseSite.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private TeacherDao teacherDaoImpl;
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
    public Result uploadPublish(MultipartFile[] files, String path,String type, Long fileID, Long teacherID) {
        result.clear();
        Teacher teacher = teacherDaoImpl.getByTeacherID(teacherID);
        if (teacher == null){
            result.setFail(102,"该教师不存在");
            return result;
        }
        try {
            for (MultipartFile file:files){
                String name = file.getOriginalFilename();
                File dir = new File(path);
                String storePath = path+"/"+name;
                if (!dir.exists()){
                    result.setFail(111,"文件夹不存在");
                    return result;
                }else {

                    if (type.equals("homework")){
                        if(homeWork_publishDaoImpl.get(fileID,"homeWorkID")!=null){
                            result.setFail(112,"文件号重复");
                            return result;
                        }
                        HomeWork_publish homeWork_publish = new HomeWork_publish(fileID,name,storePath,0L,teacherID);
                        file.transferTo(new File(storePath));
                        homeWork_publishDaoImpl.save(homeWork_publish);
                        result.setOK("上传成功",storePath);
                    }else if (type.equals("report")){
                        if(report_publishDaoImpl.get(fileID,"reportID")!=null){
                            result.setFail(112,"文件号重复");
                            return result;
                        }
                        Report_publish report_publish = new Report_publish(fileID,name,storePath,0L,teacherID);
                        file.transferTo(new File(storePath));
                        report_publishDaoImpl.save(report_publish);
                        result.setOK("上传成功",storePath);
                    }else if (type.equals("courseware")){
                        if(courseWareDaoImpl.get(fileID,"coureseWareID")!=null){
                            result.setFail(112,"文件号重复");
                            return result;
                        }
                        CourseWare courseWare = new CourseWare(fileID,name,storePath,0L,teacherID);
                        file.transferTo(new File(storePath));
                        courseWareDaoImpl.save(courseWare);
                        result.setOK("上传成功",storePath);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result downloadPublish(String filename, String path, OutputStream outputStream,String type,Long studentID) {
        result.clear();
        String storePath = path+"/"+filename;
        File tempFile = new File(storePath);
        if (!tempFile.exists()){
            result.setFail(400,"文件不存在");
        }else{
            if (type.equals("homework")){
                HomeWork_publish homeWork_publish = homeWork_publishDaoImpl.getByStorePath(storePath);
                Long downloadCount = homeWork_publish.getDownloadCount();
                downloadCount++;
                homeWork_publish.setDownloadCount(downloadCount);
                System.out.println(downloadCount);
                homeWork_publishDaoImpl.updateDownloadCount(homeWork_publish.getId(),downloadCount);
                HomeWork_publish_download homeWork_publish_download = new HomeWork_publish_download(storePath,studentID);
                homeWork_publish_downloadDaoImpl.save(homeWork_publish_download);
            }else if (type.equals("report")){
                Report_publish report_publish = report_publishDaoImpl.getByStorePath(storePath);
                Long downloadCount = report_publish.getDownloadCount();
                downloadCount++;
                report_publish.setDownloadCount(downloadCount);
                System.out.println(downloadCount);
                report_publishDaoImpl.updateDownloadCount(report_publish.getId(),downloadCount);
                Report_publish_download report_publish_download = new Report_publish_download(storePath,studentID);
                report_publish_downloadDaoImpl.save(report_publish_download);
            }else if (type.equals("courseware")){
                CourseWare courseWare = courseWareDaoImpl.getByStorePath(storePath);
                Long downloadCount = courseWare.getDownloadCount();
                downloadCount++;
                courseWare.setDownloadCount(downloadCount);
                System.out.println(downloadCount);
                courseWareDaoImpl.updateDownloadCount(courseWare.getId(),downloadCount);
                CourseWare_download courseWare_download = new CourseWare_download(storePath,studentID);
                courseWare_downloadDaoImpl.save(courseWare_download);
            }
            result = Util.download(filename,path,outputStream);
        }
        return result;
    }

    @Override
    public Result rmPublish(String filename, String path, String type) {
        result.clear();
        String storePath = path +"/"+ filename;
        File targetFile = new File(storePath);
        if (!targetFile.exists()) {
            result.setFail(400,"文件不存在");
            return result;
        }else {
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
        }
        return result;
    }
}
