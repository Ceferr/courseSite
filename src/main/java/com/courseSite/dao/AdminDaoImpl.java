package com.courseSite.dao;

import com.courseSite.pojo.Admin;
import org.springframework.stereotype.Repository;

@Repository("AdminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao {
}
