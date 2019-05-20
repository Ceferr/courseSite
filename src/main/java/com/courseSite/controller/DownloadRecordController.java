package com.courseSite.controller;

import com.courseSite.ResponseResult.Result;
import com.courseSite.service.DownloadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/downloadRecord")
public class DownloadRecordController {

    @Autowired
    private DownloadRecordService downloadRecordServiceImpl;

    @RequestMapping(method = RequestMethod.POST,value = "/getRecord")
    @ResponseBody
    public Result getAllRecord(@RequestParam(value = "studentID") Long studentID,
                               @RequestParam(value = "type")String type){
        Result result = downloadRecordServiceImpl.getAllRecord(studentID,type);
        return result;
    }
}
