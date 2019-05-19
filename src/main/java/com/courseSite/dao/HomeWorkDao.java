package com.courseSite.dao;

import com.courseSite.pojo.HomeWork;

public interface HomeWorkDao extends BaseDao<HomeWork> {

    HomeWork getByHomeWorkID(Long homeworkID);

}
