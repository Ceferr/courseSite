package com.courseSite.controller;

import com.courseSite.ResponseResult.Result;
import com.courseSite.pojo.Notice;
import com.courseSite.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeServiceImpl;

    @RequestMapping(method = RequestMethod.GET,value = "/getNoticeByPage")
    @ResponseBody
    public Result getPostByPage(@RequestParam(value = "start")Integer start,
                                @RequestParam(value = "size")Integer size){
        Result result = noticeServiceImpl.getNoticeByPage(start, size);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/publishNotice")
    @ResponseBody
    public Result askPost(@RequestParam(value = "teacherID")Long teacherID,
                          @RequestParam(value = "title")String title,
                          @RequestParam(value = "content")String content){
        Notice notice = new Notice(title,content,teacherID);
        Result result = noticeServiceImpl.addNotice(notice);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/deleteNotice")
    @ResponseBody
    public Result deletePost(@RequestParam(value = "title")String title){
        Result result = noticeServiceImpl.deleteNotice(title);
        return result;
    }
}
