package com.courseSite.controller;

import com.courseSite.ResponseResult.Result;
import com.courseSite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postServiceImpl;

    @RequestMapping(method = RequestMethod.GET,value = "/getPostByPage")
    @ResponseBody
    public Result getPostByPage(@RequestParam(value = "start")Integer start,
                                @RequestParam(value = "size")Integer size){
        Result result = postServiceImpl.getPostByPage(start, size);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/askPost")
    @ResponseBody
    public Result askPost(@RequestParam(value = "studentID")Long studentID,
                        @RequestParam(value = "title")String title,
                        @RequestParam(value = "content")String content){
        Result result = postServiceImpl.askPost(studentID, title, content);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/replyPost")
    @ResponseBody
    public Result replyPost(@RequestParam(value = "title")String title,
                            @RequestParam(value = "teacherID")Long teacherID,
                            @RequestParam(value = "content")String content){
        Result result = postServiceImpl.replyPost(title, teacherID, content);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/deletePost")
    @ResponseBody
    public Result deletePost(@RequestParam(value = "title")String title){
        Result result = postServiceImpl.deletePost(title);
        return result;
    }
}
