package com.courseSite.controller;

import com.courseSite.ResponseResult.Result;
import com.courseSite.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private LoginService loginServiceImpl;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestParam(value = "userType")String userType,
                        @RequestParam(value = "ID")Long ID,
                        @RequestParam(value = "password")String password){
        Result result = loginServiceImpl.login(userType,ID,password);
        return result;
    }
}
