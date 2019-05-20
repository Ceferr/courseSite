package com.courseSite.dao;

import com.courseSite.pojo.Quiz;
import org.springframework.stereotype.Repository;

@Repository("QuizDaoImpl")
public class QuizDaoImpl extends BaseDaoImpl<Quiz> implements QuizDao{
}
