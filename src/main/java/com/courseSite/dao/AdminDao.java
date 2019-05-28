package com.courseSite.dao;

import com.courseSite.pojo.Admin;

public interface AdminDao extends BaseDao<Admin> {

    Admin getByAdminID(Long adminID);

}
