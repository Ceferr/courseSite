package com.courseSite.service;

import com.courseSite.ResponseResult.Result;

public interface UploadRecordService {

    Result getAllRecord(Long studentID,String type);
}
