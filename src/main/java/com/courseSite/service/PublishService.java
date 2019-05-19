package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface PublishService {

    Result uploadPublish(MultipartFile[] files, String path, String type, Long fileID, Long teacherID);

    Result downloadPublish(String filename, String path, OutputStream outputStream,String type);

    Result rmPublish(String filename,String path,String type);
}
