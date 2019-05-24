package com.courseSite.controller;

import com.courseSite.ResponseResult.Result;
import com.courseSite.pojo.Quiz;
import com.courseSite.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET,value = "/getAllQuizByPage")
    @ResponseBody
    public Result getAllQuizByPage(@RequestParam(value = "start")Integer start,
                                   @RequestParam(value = "size")Integer size){
        Result result = quizServiceImpl.getAllQuizByPage(start, size);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/deleteQuiz")
    @ResponseBody
    public Result deleteQuiz(@RequestParam(value = "quizID")Long quizID){
        Result result = quizServiceImpl.deleteQuiz(quizID);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/addQuiz")
    @ResponseBody
    public Result addQuiz(@RequestParam(value = "quizID")Long quizID,
                          @RequestParam(value = "title")String title,
                          @RequestParam(value = "optionA")String optionA,
                          @RequestParam(value = "optionB")String optionB,
                          @RequestParam(value = "optionC")String optionC,
                          @RequestParam(value = "optionD")String optionD,
                          @RequestParam(value = "answer")String answer){
        Quiz quiz = new Quiz(quizID,title,optionA,optionB,optionC,optionD,answer);
        Result result = quizServiceImpl.addQuiz(quiz);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/updateQuiz")
    @ResponseBody
    public Result updateQuiz(@RequestParam(value = "quizID")Long quizID,
                          @RequestParam(value = "title")String title,
                          @RequestParam(value = "optionA")String optionA,
                          @RequestParam(value = "optionB")String optionB,
                          @RequestParam(value = "optionC")String optionC,
                          @RequestParam(value = "optionD")String optionD,
                          @RequestParam(value = "answer")String answer){
        Quiz quiz = new Quiz(quizID,title,optionA,optionB,optionC,optionD,answer);
        Result result = quizServiceImpl.updateQuiz(quiz);
        return result;
    }
}
