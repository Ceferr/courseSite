package com.courseSite.dao;

import com.courseSite.pojo.HomeWork_publish;
import com.courseSite.pojo.Report_publish;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Repository("Report_publishDaoImpl")
public class Report_publishDaoImpl extends BaseDaoImpl<Report_publish> implements Report_publishDao {
    @Override
    public Report_publish getByReportID(Long reportID) {
        String hql = "From Report_publish where reportID = :reportID";
        Map map = new HashMap();
        map.put("reportID",reportID);
        Query query = getQuery(hql,map);
        Report_publish report_publish = (Report_publish) query.uniqueResult();
        return report_publish;
    }

    @Override
    public Report_publish getByStorePath(String storePath) {
        String hql = "From Report_publish where path = :path";
        Map map = new HashMap();
        map.put("path",storePath);
        Query query = getQuery(hql,map);
        Report_publish report_publish = (Report_publish) query.uniqueResult();
        return report_publish;
    }

    @Override
    public void updateDownloadCount(Serializable id, Long downloadCount) {
        String hql="update Report_publish r set r.downloadCount=:downloadCount where r.id=:id";
        Map map = new HashMap();
        map.put("downloadCount",downloadCount);
        map.put("id",id);
        Query query = getQuery(hql,map);
        query.executeUpdate();
    }
}
