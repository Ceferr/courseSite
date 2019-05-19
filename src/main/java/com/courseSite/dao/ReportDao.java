package com.courseSite.dao;

import com.courseSite.pojo.Report;

public interface ReportDao extends BaseDao<Report>{

    Report getByReportID(Long reportID);
}
