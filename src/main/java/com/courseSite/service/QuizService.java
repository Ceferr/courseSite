package com.courseSite.service;

import com.courseSite.ResponseResult.Result;

public interface QuizService {

    Result getAllQuiz();

    Result getAllQuizByPage(Integer start,Integer size);
}
