package com.courseSite.dao;

import com.courseSite.pojo.Report;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("ReportDaoImpl")
public class ReportDaoImpl extends BaseDaoImpl<Report> implements ReportDao{
    @Override
    public Report getByReportID(Long reportID) {
        String hql = "From Report where reportID = :reportID";
        Map map = new HashMap();
        map.put("reportID",reportID);
        Query query = getQuery(hql,map);
        Report report = (Report) query.uniqueResult();
        return report;
    }
}
