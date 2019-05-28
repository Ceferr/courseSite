package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface StudentService {
    Result addStudent(Long studentID,String name,String password,String sex,Long teacherID);

    Result getByStudentID(Long studentID);

    Result deleteByStudentID(Long studentID);

    Result getAllStudent();

    Result getAllStudentByPage(Integer start,Integer size);

    Result updatePassword(Long studentID,String originalPassword,String password);

    Result updateInfo(Long studentID,String name,String sex);

    Result importFromExcel(MultipartFile file);

    Result exportToExcel(Long teacherID, OutputStream outputStream);
}
