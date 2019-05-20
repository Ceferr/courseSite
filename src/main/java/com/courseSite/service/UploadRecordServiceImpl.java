package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.HomeWork_uploadDao;
import com.courseSite.dao.Report_uploadDao;
import com.courseSite.dao.StudentDao;
import com.courseSite.pojo.HomeWork_upload;
import com.courseSite.pojo.Report_upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UploadRecordServiceImpl implements UploadRecordService{

    @Autowired
    private HomeWork_uploadDao homeWork_uploadDaoImpl;
    @Autowired
    private Report_uploadDao report_uploadDaoImpl;
    @Autowired
    private StudentDao studentDaoImpl;

    private Result result = new Result();

    @Override
    public Result getAllRecord(Long studentID, String type) {
        result.clear();
        List records = new ArrayList();
        if (studentDaoImpl.getByStudentID(studentID)==null){
            result.setFail(400,"该学生不存在");
            return result;
        }else {
            switch (type){
                case "homework":{
                    List<HomeWork_upload> lists = homeWork_uploadDaoImpl.get(studentID,"studentID");
                    for (HomeWork_upload record: lists){
                        records.add(record.getPath());
                    }
                    break;
                }
                case "report":{
                    List<Report_upload> lists = report_uploadDaoImpl.get(studentID,"studentID");
                    for (Report_upload record: lists){
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
