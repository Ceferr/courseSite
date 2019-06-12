package com.courseSite.dao;

import com.courseSite.pojo.Post;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("PostDaoImpl")
public class PostDaoImpl extends BaseDaoImpl<Post> implements PostDao{


    @Override
    public void updatePost(String title, Long teacherID, String content, Date reley_time) {
        String hql="update Post p set p.reply_content=:reply_content, p.teacherID =:teacherID, p.reply_time=:reply_time where p.title=:title";
        Map map = new HashMap();
        map.put("reply_content",content);
        map.put("teacherID",teacherID);
        map.put("reply_time",reley_time);
        map.put("title",title);
        Query query = getQuery(hql,map);
        query.executeUpdate();
    }

    @Override
    public List<Post> findPostByPage(Integer start, Integer size) {
        String hql = "From Post x order by x.ask_time desc";
        Query query = this.getCurrentSession().createQuery(hql);
        query.setFirstResult(start).setMaxResults(size);
        return query.list();
    }
}
