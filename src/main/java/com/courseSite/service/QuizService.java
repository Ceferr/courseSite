package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.pojo.Quiz;

public interface QuizService {

    Result getAllQuiz();

    Result getAllQuizByPage(Integer start,Integer size);

    Result addQuiz(Quiz quiz);

    Result deleteQuiz(Long quizID);

    Result updateQuiz(Quiz quiz);
}
