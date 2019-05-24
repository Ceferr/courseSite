package com.courseSite.service;

import com.courseSite.ResponseResult.Result;

public interface LoginService {

    Result login(String userType,Long ID,String password);
}
