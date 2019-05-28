package com.courseSite.dao;

import com.courseSite.pojo.Admin;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("AdminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao {

    @Override
    public Admin getByAdminID(Long adminID) {
        String hql = "From Teacher where adminID=:id";
        Map map = new HashMap();
        map.put("id",adminID);
        Query query = getQuery(hql,map);
        Admin admin = (Admin)query.uniqueResult();
        if (admin == null) return null;
        else return admin;
    }
}
