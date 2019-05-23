package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface PublishService {

    Result uploadPublish(MultipartFile[] files, String type, Long fileID, Long teacherID);

    Result downloadPublish(Long fileID, String filename,OutputStream outputStream,String type,Long studentID);

    Result rmPublish(Long fileID,String filename,String type,Long teacherID);

    Result getAllPublishByPage(String type,Integer start, Integer size);

    Result makeDir(String type,Integer num,Long teacherID);
}
