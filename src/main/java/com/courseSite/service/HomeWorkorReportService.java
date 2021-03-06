package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface HomeWorkorReportService {

    Result uploadHomeWorkorReport(MultipartFile[] files,String type,Long fileID,Long studentID);

    Result removefile(String filename,String path,String type);

    Result getAllHomeWorkOrReportByPage(Long fileID,String type,Integer start, Integer size);

    Result download(Long fileID,String filename, String type,Long teacherID, OutputStream outputStream);
}
