package com.courseSite.dao;

import com.courseSite.pojo.Post;

import java.util.Date;
import java.util.List;

public interface PostDao extends BaseDao<Post> {

    void updatePost(String title, Long teacherID, String content, Date reley_date);
    List<Post> findPostByPage(Integer start, Integer size);
}
