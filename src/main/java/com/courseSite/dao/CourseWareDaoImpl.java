package com.courseSite.dao;

import com.courseSite.pojo.CourseWare;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Repository("CourseWareDaoImpl")
public class CourseWareDaoImpl extends BaseDaoImpl<CourseWare> implements CourseWareDao {
    @Override
    public CourseWare getByCoureseWareID(Long coureseWareID) {
        String hql = "From CourseWare where coureseWareID = :courseWareID";
        Map map = new HashMap();
        map.put("CourseWareID",coureseWareID);
        Query query = getQuery(hql,map);
        CourseWare courseWare = (CourseWare) query.uniqueResult();
        return courseWare;
    }

    @Override
    public CourseWare getByStorePath(String storePath) {
        String hql = "From CourseWare where path = :path";
        Map map = new HashMap();
        map.put("path",storePath);
        Query query = getQuery(hql,map);
        CourseWare courseWare = (CourseWare) query.uniqueResult();
        return courseWare;
    }

    @Override
    public void updateDownloadCount(Serializable id, Long downloadCount) {
        String hql="update CourseWare c set c.downloadCount=:downloadCount where c.id=:id";
        Map map = new HashMap();
        map.put("downloadCount",downloadCount);
        map.put("id",id);
        Query query = getQuery(hql,map);
        query.executeUpdate();
    }
}
