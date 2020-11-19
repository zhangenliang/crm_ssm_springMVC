package com.jhuniversity.crm.workbench.web.controller;

import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.settings.service.UserService;
import com.jhuniversity.crm.utils.DateTimeUtil;
import com.jhuniversity.crm.utils.UUIDUtil;
import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/workbench/activity")
public class ActivityController {
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;
    //取得用户列表
    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        System.out.println("进入获取用户列表部分");
        List<User>ulist =userService.getUser();
        return ulist;
    }
    @RequestMapping(value = "/save.do")
    @ResponseBody
    public Map<String,Object> save( Activity activity){
        System.out.println("进入市场活动添加部分");
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        System.out.println(activity);
        int count=activityService.save(activity);
        Map<String,Object>map=new HashMap<>();
        if (count!=1){
            map.put("success",false);
        }else {
            map.put("success",true);
        }
        return map;
    }
}
