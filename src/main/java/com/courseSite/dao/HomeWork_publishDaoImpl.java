package com.courseSite.dao;

import com.courseSite.pojo.HomeWork_publish;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Repository("HomeWork_publishDaoImpl")
public class HomeWork_publishDaoImpl extends BaseDaoImpl<HomeWork_publish> implements HomeWork_publishDao {

    @Override
    public HomeWork_publish getByHomeWorkID(Long homeWorkID) {
        String hql = "From HomeWork_publish where homeWorkID = :homeWorkID";
        Map map = new HashMap();
        map.put("homeWorkID",homeWorkID);
        Query query = getQuery(hql,map);
        HomeWork_publish homeWork_publish = (HomeWork_publish) query.uniqueResult();
        return homeWork_publish;
    }

    @Override
    public HomeWork_publish getByStorePath(String storePath) {
        String hql = "From HomeWork_publish where path = :path";
        Map map = new HashMap();
        map.put("path",storePath);
        Query query = getQuery(hql,map);
        HomeWork_publish homeWork_publish = (HomeWork_publish) query.uniqueResult();
        return homeWork_publish;
    }

    @Override
    public void updateDownloadCount(Serializable id,Long downloadCount) {
        String hql="update HomeWork_publish h set h.downloadCount=:downloadCount where h.id=:id";
        Map map = new HashMap();
        map.put("downloadCount",downloadCount);
        map.put("id",id);
        Query query = getQuery(hql,map);
        query.executeUpdate();
    }
}
