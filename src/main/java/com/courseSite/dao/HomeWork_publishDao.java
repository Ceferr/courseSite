package com.courseSite.dao;

import com.courseSite.pojo.HomeWork_publish;

import java.io.Serializable;

public interface HomeWork_publishDao extends BaseDao<HomeWork_publish> {

    HomeWork_publish getByHomeWorkID(Long homeWorkID);

    HomeWork_publish getByStorePath(String storePath);

    void updateDownloadCount(Serializable id,Long downloadCount);
}
