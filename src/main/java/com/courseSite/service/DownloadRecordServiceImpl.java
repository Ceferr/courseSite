package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.CourseWare_downloadDao;
import com.courseSite.dao.HomeWork_publish_downloadDao;
import com.courseSite.dao.Report_publish_downloadDao;
import com.courseSite.dao.StudentDao;
import com.courseSite.pojo.CourseWare_download;
import com.courseSite.pojo.HomeWork_publish_download;
import com.courseSite.pojo.Report_publish_download;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadRecordServiceImpl implements DownloadRecordService{

    @Autowired
    private HomeWork_publish_downloadDao homeWork_publish_downloadDaoImpl;
    @Autowired
    private Report_publish_downloadDao report_publish_downloadDaoImpl;
    @Autowired
    private CourseWare_downloadDao courseWare_downloadDaoImpl;
    @Autowired
    private StudentDao studentDaoImpl;

    private Result result = new Result();

    @Override
    public Result getAllRecord(Long studentID,String type) {
        result.clear();
        List records = new ArrayList();
        if (studentDaoImpl.getByStudentID(studentID)==null){
            result.setFail(400,"该学生不存在");
            return result;
        }else {
            switch (type){
                case "homework":{
                    System.out.println("开始查询");
                    List<HomeWork_publish_download> lists = homeWork_publish_downloadDaoImpl.get(studentID,"studentID");
                    for (HomeWork_publish_download record : lists){
                        System.out.println(record.getPath());
                        records.add(record.getPath());
                    }
                    break;
                }
                case "report":{
                    List<Report_publish_download> lists = report_publish_downloadDaoImpl.get(studentID,"studentID");
                    for (Report_publish_download record: lists){
                        records.add(record.getPath());
                    }
                    break;
                }
                case "courseware":{
                    List<CourseWare_download> lists = courseWare_downloadDaoImpl.get(studentID,"studentID");
                    for (CourseWare_download record: lists){
                        records.add(record.getPath());
                    }
                    break;
                }
            }
            result.setOK("查询成功",records);
        }
        return result;
    }
}
