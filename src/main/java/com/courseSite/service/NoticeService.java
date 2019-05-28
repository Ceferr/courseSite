package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.pojo.Notice;

public interface NoticeService {

    Result addNotice(Notice notice);

    Result getNoticeByPage(Integer start,Integer size);

    Result deleteNotice(String title);

}
