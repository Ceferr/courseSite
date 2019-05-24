package com.courseSite.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.courseSite.ResponseResult.Result;
import com.courseSite.util.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader("access_token");
        //token不存在
        if (token!=null){
            //验证token是否正确
            boolean cons = JwtUtil.verify(token);
            if (cons){
                return true;
            }
        }
        Result result = new Result();
        result.setFail(405,"认证失败");
        responseMessage(response,response.getWriter(),result);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /*
    *
    *发送信息给客户端
    */
    private void responseMessage(HttpServletResponse response, PrintWriter out,Result result){
        response.setContentType("application/json; charset=utf-8");
        out.print(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }
}
