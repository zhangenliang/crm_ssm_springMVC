package com.jhuniversity.crm.workbench.web.controller;

import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.settings.service.UserService;
import com.jhuniversity.crm.utils.DateTimeUtil;
import com.jhuniversity.crm.utils.UUIDUtil;
import com.jhuniversity.crm.vo.Vo;
import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.desktop.SystemSleepEvent;
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
    public boolean save( Activity activity){
        System.out.println("进入市场活动添加部分");
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        System.out.println(activity);
//        int count=activityService.save(activity);
//        Map<String,Object>map=new HashMap<>();
//        if (count!=1){
//            map.put("success",false);
//        }else {
//            map.put("success",true);
//        }
//        return map;
        boolean flag=activityService.save(activity);
        return flag;
    }
    @RequestMapping(value = "/pageList.do")
    @ResponseBody
    public Vo pageList( Activity activity,HttpServletRequest request){
        System.out.println("进入分页查询模块");
        System.out.println(activity);
        String pageNoStr=request.getParameter("pageNo");
        int pageNo=Integer.valueOf(pageNoStr);
        String pageSizeStr=request.getParameter("pageSize");
        int pageSize=Integer.valueOf(pageSizeStr);
        System.out.println(pageSize);
        //略过的页数
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object>map=new HashMap<>();
        map.put("activity",activity);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        Vo<Activity> vo=activityService.pageList(map);
        System.out.println(vo);
        return vo;

//        System.out.println("进入分页查询模块");
//
//        int No=Integer.valueOf(pageNo);
//        int Size=Integer.valueOf(pageSize);
//        System.out.println(pageSize);
//        //略过的页数
//        int skipCount=(No-1)*Size;
//        Map<String,Object>map=new HashMap<>();
//        map.put("activity",activity);
//        map.put("skipCount",skipCount);
//        map.put("pageSize",pageSize);
//        Vo<Activity> vo=activityService.pageList(map);
//        System.out.println(vo);
//        return vo;

    }
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public boolean delete(HttpServletRequest request){
        System.out.println("进入活动删除页面");
        String []ids=request.getParameterValues("id");
        System.out.println(ids);
        boolean flag=activityService.delete(ids);
        return flag;
    }
    @RequestMapping(value = "/getUserListAndActivity.do")
    @ResponseBody
    public Map<String,Object> getUserListAndActivity(String id){
        System.out.println("进入修改操作页面");
        Map<String,Object>map=activityService.getUserListAndActivity(id);
        System.out.println(map);
        return map;
    }
    @RequestMapping("/update.do")
    @ResponseBody
    public boolean update(Activity activity){
        System.out.println("进入修改更新页面");
        boolean flag=activityService.update(activity);
        return flag;
    }
}
