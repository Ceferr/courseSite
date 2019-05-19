package com.courseSite.dao;

import com.courseSite.pojo.Report_publish;

import java.io.Serializable;

public interface Report_publishDao extends BaseDao<Report_publish> {
    Report_publish getByReportID(Long reportID);

    Report_publish getByStorePath(String storePath);

    void updateDownloadCount(Serializable id, Long downloadCount);
}
