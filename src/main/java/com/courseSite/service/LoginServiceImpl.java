package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.AdminDao;
import com.courseSite.dao.StudentDao;
import com.courseSite.dao.TeacherDao;
import com.courseSite.pojo.Student;
import com.courseSite.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StudentDao studentDaoImpl;

    @Autowired
    private TeacherDao teacherDaoImpl;

    @Autowired
    private AdminDao adminDaoImpl;

    private Result result = new Result();

    @Override
    public Result login(String userType, Long ID, String password) {
        result.clear();
        boolean isSuccess = checkUser(userType, ID, password);
        if (isSuccess){
            if (userType.equals("student")){
                Student student = studentDaoImpl.getByStudentID(ID);
                if (student != null){
                    String token = JwtUtil.sign(ID);
                    if (token != null){
                        result.setOK("登陆成功",token);
                    }
                }
            }
        }else {
            result.setFail(400,"登陆失败");
        }

        return result;
    }

    private boolean checkUser(String userType,Long ID,String password){
        if (userType.equals("student")){
            String testPassword = studentDaoImpl.getByStudentID(ID).getPassword();
            if (password.equals(testPassword)){
                return true;
            }else {
                return false;
            }
        }else {
            System.out.println("...");
            return false;
        }
    }
}
