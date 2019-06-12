package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.NoticeDao;
import com.courseSite.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    private NoticeDao noticeDaoImpl;

    private Result result = new Result();

    @Override
    public Result addNotice(Notice notice) {
        result.clear();
        Notice testNotice = noticeDaoImpl.getOne(notice.getTitle(),"title");
        if (testNotice != null){
            result.setFail(161,"该通知已存在");
            return result;
        }else if (notice.getTitle().equals("")){
            result.setFail(162,"标题为空");
            return result;
        } else {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = simpleDateFormat.format(date);
            Date time = null;
            try {
                time = simpleDateFormat.parse(nowTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            notice.setTime(time);
            noticeDaoImpl.save(notice);
            result.setOK("发布成功",notice);
        }
        return result;
    }

    @Override
    public Result getNoticeByPage(Integer start, Integer size) {
        result.clear();
        List<Notice> notices = noticeDaoImpl.findAllByPage("time",start,size);
        Long count = noticeDaoImpl.getCount();
        result.setOK(count.toString(),notices);
        return result;
    }

    @Override
    public Result deleteNotice(String title) {
        result.clear();
        Notice notice = noticeDaoImpl.getOne(title,"title");
        noticeDaoImpl.delete(notice.getId());
        result.setOK("删除成功",notice);
        return result;
    }
}
