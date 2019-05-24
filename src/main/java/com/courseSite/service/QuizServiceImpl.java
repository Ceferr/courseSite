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
        List<Quiz> quizzes = quizDaoImpl.findAll("quizID");
        result.setOK("查询成功",quizzes);
        return result;
    }

    @Override
    public Result getAllQuizByPage(Integer start, Integer size) {
        result.clear();
        List<Quiz> quizzes = quizDaoImpl.findAllByPage("quizID",start,size);
        result.setOK("查询成功",quizzes);
        return result;
    }

    @Override
    public Result addQuiz(Quiz quiz) {
        result.clear();
        Quiz test = quizDaoImpl.getOne(quiz.getQuizID(),"quizID");
        if (test!=null){
            result.setFail(131,"题号已存在");
            return result;
        }else {
            result.setOK("添加成功",quiz);
            quizDaoImpl.save(quiz);
        }
        return result;
    }

    @Override
    public Result deleteQuiz(Long quizID) {
        result.clear();
        Quiz quiz = quizDaoImpl.getOne(quizID,"quizID");
        quizDaoImpl.delete(quiz.getId());
        result.setOK("删除成功",quiz);
        return result;
    }

    @Override
    public Result updateQuiz(Quiz quiz) {
        result.clear();
        Quiz test = quizDaoImpl.getOne(quiz.getQuizID(),"quizID");
        if (test!=null){
            quizDaoImpl.delete(test.getId());
        }
        quizDaoImpl.save(quiz);
        result.setOK("修改成功",quiz);
        return result;
    }
}
