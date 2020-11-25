package com.jhuniversity.crm.workbench.web.controller;

import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.settings.service.UserService;
import com.jhuniversity.crm.utils.DateTimeUtil;
import com.jhuniversity.crm.utils.UUIDUtil;
import com.jhuniversity.crm.vo.Vo;
import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.domain.ActivityRemark;
import com.jhuniversity.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        for (User list:ulist){
            System.out.println(list);
        }
        return ulist;
    }
    @RequestMapping(value = "/save.do")
    @ResponseBody
    public boolean save( Activity activity){
        System.out.println("进入市场活动添加部分");
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        System.out.println(activity);
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
    public boolean update(Activity activity,HttpServletRequest request){
        System.out.println("进入修改更新页面");
        //修改时间，当前的系统时间
        String editTime=DateTimeUtil.getSysTime();
        //修改人，当前登陆的用户
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);
        System.out.println(activity+"--------------");
        boolean flag=activityService.update(activity);
        return flag;
    }
    @RequestMapping(value = "/detail.do")
    public String detail(String id,HttpServletRequest request){
        System.out.println("进入到详细页面controller部分");
        Activity a=activityService.detail(id);
//        ModelAndView mv=new ModelAndView();
        //在springmvc配置文件中配置了视图解析器，在这里可以直接使用地址。
//        mv.setViewName("forward:/workbench/activity/detail.jsp");
        request.getSession().setAttribute("a",a);
//        mv.addObject("a",a);
        return "forward:/workbench/activity/detail.jsp";
    }
    @RequestMapping(value = "/getRemarkListByAid.do")
    @ResponseBody
    public List< ActivityRemark> getRemarkListByAid(String activityId){
        System.out.println("进入备注信息获取页面controller");
        List< ActivityRemark> list=activityService.getRemarkListByAid(activityId);
        return list;
    }
    @RequestMapping(value = "/deleteRemark.do")
    @ResponseBody
    public boolean deleteRemark(String id){
        System.out.println("进入活动页面备注删除操作");
        boolean flag=activityService.deleteRemarkById(id);
        return flag;
    }
    @RequestMapping(value = "/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(ActivityRemark ar,HttpServletRequest request){
        System.out.println("进入修改备注内容controller");
        String editTime=DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="1";
        ar.setEditTime(editTime);
        ar.setEditBy(editBy);
        ar.setEditFlag(editFlag);
        System.out.println(ar);
        boolean flag=activityService.updateRemark(ar);
        Map<String,Object>map=new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);
        return map;
    }
    @RequestMapping(value = "/saveRemark.do")
    @ResponseBody
    public Map<String,Object> saveRemark(ActivityRemark ar,HttpServletRequest request){
        System.out.println("进入修改备注内容controller");
        String createTime=DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="0";
        String id=UUIDUtil.getUUID();
        ar.setId(id);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);
        System.out.println(ar);
        boolean flag=activityService.saveRemark(ar);
        Map<String,Object>map=new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);
        return map;
    }
}
