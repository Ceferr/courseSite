package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.QuizDao;
import com.courseSite.pojo.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDao quizDaoImpl;

    private Result result = new Result();

    @Override
    public Result getAllQuiz() {
        result.clear();
        List<Quiz> quizzes = quizDaoImpl.findAll();
        result.setOK("查询成功",quizzes);
        return result;
    }

    @Override
    public Result getAllQuizByPage(Integer start, Integer size) {
        result.clear();
        List<Quiz> quizzes = quizDaoImpl.findAllByPage(start,size);
        result.setOK("查询成功",quizzes);
        return result;
    }
}
