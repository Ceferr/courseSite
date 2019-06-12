package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.PostDao;
import com.courseSite.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDaoImpl;

    private Result result = new Result();

    @Override
    public Result askPost(Long studentID, String title, String content) {
        result.clear();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = simpleDateFormat.format(date);
        Date time = null;
        try {
            time = simpleDateFormat.parse(nowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (title==null){
            result.setFail(151,"标题为空");
            return result;
        }
        if (postDaoImpl.getOne(title,"title")!=null){
            result.setFail(152,"该帖子已存在");
            return result;
        }
        Post post = new Post(title,content,time,studentID);
        postDaoImpl.save(post);
        result.setOK("发布成功",post);
        return result;
    }

    @Override
    public Result replyPost(String title,Long teacherID, String content) {
        result.clear();
        Post post = postDaoImpl.getOne(title,"title");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = simpleDateFormat.format(date);
        System.out.println(nowTime);
        Date time = null;
        try {
            time = simpleDateFormat.parse(nowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        postDaoImpl.updatePost(title,teacherID,content,time);
        result.setOK("回复成功",postDaoImpl.getOne(title,"title"));
        return result;
    }

    @Override
    public Result deletePost(String title) {
        result.clear();
        Post post = postDaoImpl.getOne(title,"title");
        postDaoImpl.delete(post.getId());
        result.setOK("删除成功",post);
        return result;
    }

    @Override
    public Result getPostByPage(Integer start, Integer size) {
        result.clear();
        List<Post> posts = postDaoImpl.findPostByPage(start,size);
        Long count = postDaoImpl.getCount();
        result.setOK(count.toString(),posts);
        return result;
    }
}
