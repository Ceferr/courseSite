package com.courseSite.controller;

import com.courseSite.ResponseResult.Result;
import com.courseSite.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/quiz")
public class QuizController {

    @Autowired
    private QuizService quizServiceImpl;

    @RequestMapping(method = RequestMethod.GET,value = "/getAllQuiz")
    @ResponseBody
    public Result getAllQuiz(){
        Result result = quizServiceImpl.getAllQuiz();
        return result;
    }
}
