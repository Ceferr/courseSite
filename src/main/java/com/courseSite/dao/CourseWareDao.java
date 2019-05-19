package com.courseSite.dao;

import com.courseSite.pojo.CourseWare;

import java.io.Serializable;

public interface CourseWareDao extends BaseDao<CourseWare> {
    CourseWare getByCoureseWareID(Long courseWareID);

    CourseWare getByStorePath(String storePath);

    void updateDownloadCount(Serializable id, Long downloadCount);
}
