package com.jhuniversity.crm.settings.web.controller;
import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.settings.service.UserService;
import com.jhuniversity.crm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/settings/user")
public class UserController {
    @Resource
    private UserService userService;
    //登陆验证
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {

                System.out.println(" 进入到验证的操作");
                String loginAct=request.getParameter("loginAct");
                String loginPwd=request.getParameter("loginPwd");
                //将密码转化
                loginPwd=MD5Util.getMD5(loginPwd);
                //接收ip地址
                String ip=request.getRemoteAddr();
                Map<String,Object>map=new HashMap<>();
        try {
            User user=userService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("user",user);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            String msg=e.getMessage();
            map.put("success",false);
            map.put("msg",msg);
        }
        return map;
    }
}
