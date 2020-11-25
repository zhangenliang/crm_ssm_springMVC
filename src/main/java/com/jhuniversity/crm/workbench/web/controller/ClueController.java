package com.jhuniversity.crm.workbench.web.controller;

import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.utils.DateTimeUtil;
import com.jhuniversity.crm.utils.UUIDUtil;
import com.jhuniversity.crm.vo.Vo;
import com.jhuniversity.crm.workbench.dao.ActivityDao;
import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.domain.Clue;
import com.jhuniversity.crm.workbench.domain.ClueRemark;
import com.jhuniversity.crm.workbench.service.ActivityService;
import com.jhuniversity.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/workbench/clue")
@Controller
public class ClueController {
    @Resource
    private ClueService clueService;
    @Resource
    private ActivityService activityService;
    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        System.out.println("进入获取用户列表部分");
        List<User>ulist =clueService.getUser();
        for (User list:ulist){
            System.out.println(list);
        }
        return ulist;
    }
    @RequestMapping(value = "/save.do")
    @ResponseBody
    public boolean save(Clue clue, HttpServletRequest request){
        System.out.println("进入保存线索controller部分");
        String id=UUIDUtil.getUUID();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String createTime=DateTimeUtil.getSysTime();
        clue.setId(id);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        boolean flag=clueService.save(clue);
        return flag;
    }
    @RequestMapping(value = "/pageClueList.do")
    @ResponseBody
    public Vo<Clue> pageClueList(Clue clue, String pageNo, String pageSize){
        System.out.println("进入线索分页查询模块");
        int no=Integer.valueOf(pageNo);
        int size=Integer.valueOf(pageSize);
        //略过的页数
        int skipCount=(no-1)*size;
        Map<String,Object>map=new HashMap<>();
        map.put("clue",clue);
        map.put("skipCount",skipCount);
        map.put("pageSize",size);
        Vo<Clue>vo=clueService.pageClueList(map);
        return vo;
    }
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public boolean delete(HttpServletRequest request){
        System.out.println("进入线索删除页面");
        String[]ids=request.getParameterValues("id");
        boolean flag=clueService.delete(ids);
        return flag;
    }
    @RequestMapping(value = "/getUserListAndActivity.do")
    @ResponseBody
    public Map<String,Object> getUserListAndClue(String id){
        System.out.println("进入修改线索页面"+id);
        Map<String,Object>map=clueService.getUserListAndClue(id);
        return map;
    }
    @RequestMapping(value = "/update.do")
    @ResponseBody
    public boolean update(Clue clue,HttpServletRequest request){
        System.out.println("进入修改页面操作"+clue);
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        String editTime=DateTimeUtil.getSysTime();
        clue.setEditBy(editBy);
        clue.setEditTime(editTime);
        boolean flag=clueService.update(clue);
        return flag;
    }
    @RequestMapping(value = "/detail.do")
    public String detail(String id,HttpServletRequest request){
        System.out.println("进入线索详细模块"+id);
        Clue c=clueService.detail(id);
        request.getSession().setAttribute("c",c);
        return "forward:/workbench/clue/detail.jsp";
    }
    @RequestMapping(value = "/showRemarkList.do")
    @ResponseBody
    public List<ClueRemark> showRemarkList(String clueId){
        System.out.println("进入线索详细备注获取部分"+clueId);
        List<ClueRemark> cr=clueService.showRemarkList(clueId);
        return cr;
    }
    @RequestMapping(value = "/deleteRemarkById.do")
    @ResponseBody
    public boolean deleteRemarkById(String id){
        System.out.println("进入线索备注信息删除模块"+id);
        boolean flag=clueService.deleteRemarkById(id);
        return flag;
    }
    @RequestMapping(value = "/updateRemark.do")
    @ResponseBody
    public Map<String,Object>updateRemark(ClueRemark cr,HttpServletRequest request){
        System.out.println("进入线索备注信息更新模块"+cr);
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        String editTime=DateTimeUtil.getSysTime();
        cr.setEditBy(editBy);
        cr.setEditTime(editTime);
        cr.setEditFlag("1");
        boolean flag=clueService.updateRemark(cr);
        Map<String,Object>map=new HashMap<>();
        if (flag==true){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        map.put("cr",cr);
        return map;
    }
    @RequestMapping(value = "/saveRemark.do")
    @ResponseBody
    public Map<String,Object>saveRemark(ClueRemark cr,HttpServletRequest request){
        System.out.println("进入备注保存页面");
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String createTime=DateTimeUtil.getSysTime();
        String id=UUIDUtil.getUUID();
        cr.setEditFlag("0");
        cr.setCreateBy(createBy);
        cr.setCreateTime(createTime);
        cr.setId(id);
        boolean flag=clueService.saveRemark(cr);
        Map<String,Object>map=new HashMap<>();
        if (flag==true){
            map.put("success",true);
            map.put("cr",cr);
        }else {
            map.put("success",false);
        }
        return map;
    }
    @RequestMapping(value = "/getActivityListById.do")
    @ResponseBody
    public List<Activity>getActivityListById(String clueId){
        System.out.println("进入线索与活动关联"+clueId);
        List<Activity>aList=activityService.getActivityListById(clueId);
        return aList;
    }
    @RequestMapping(value = "/unbund.do")
    @ResponseBody
    public boolean unbund(String id){
        System.out.println("进入解除线索与活动关联部分"+id);
        boolean flag=clueService.unbund(id);
        return flag;
    }
    @RequestMapping(value = "/showActivityRelation.do")
    @ResponseBody
    public List<Activity>showActivityRelation(){
        System.out.println("进入添加关联活动模态窗口");
        List<Activity>activities=clueService.showActivityRelation();
        return activities;
    }
    @RequestMapping(value = "/getActivityListByNameAndNoClueId.do")
    @ResponseBody
    public List<Activity>getActivityListByNameAndNoClueId(String aname,String clueId){
        System.out.println("进入展开关联活动模态窗口页面");
        Map<String,String >map=new HashMap<>();
        map.put("aname",aname);
        map.put("clueId",clueId);
        List<Activity>aList=activityService.getActivityListByNameAndNoClueId(map);
        return aList;
    }
    @RequestMapping(value = "/bund.do")
    @ResponseBody
    public boolean bund(String cid,String[]aid){
        System.out.println(cid+"进入活动关联添加操作"+aid);
        boolean flag=clueService.bund(cid,aid);
        return flag;
    }
}
