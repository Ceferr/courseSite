package com.courseSite.service;

import com.courseSite.ResponseResult.Result;

public interface DownloadRecordService {

    Result getAllRecord(Long studentID,String type);
}
