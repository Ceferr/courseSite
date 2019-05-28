package com.courseSite.service;

import com.courseSite.ResponseResult.Result;

public interface PostService {

    Result askPost(Long studentID,String title,String content);

    Result replyPost(String title,Long teacherID,String content);

    Result deletePost(String title);

    Result getPostByPage(Integer start,Integer size);
}
