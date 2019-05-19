package com.courseSite.dao;

import com.courseSite.pojo.HomeWork;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("HomeWorkDaoImpl")
public class HomeWorkDaoImpl extends BaseDaoImpl<HomeWork> implements HomeWorkDao{
    @Override
    public HomeWork getByHomeWorkID(Long homeworkID) {
        String hql = "From HomeWork where homeworkID = :homeworkID";
        Map map = new HashMap();
        map.put("homeworkID",homeworkID);
        Query query = getQuery(hql,map);
        HomeWork homeWork = (HomeWork) query.uniqueResult();
        return homeWork;
    }
}
