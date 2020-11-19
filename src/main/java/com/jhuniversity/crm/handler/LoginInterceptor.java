package com.jhuniversity.crm.handler;

import com.jhuniversity.crm.settings.domian.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("进入拦截器");
        //逻辑：判断用户是否登录  本质：判断session中有没有user
        String path=request.getServletPath();
        System.out.println(path);
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            return true;
            //其他资源进行验证
        }else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if(user==null){
                //没有登录
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                return false;
            }
            //放行  访问目标资源
            return true;
        }
    }
}
