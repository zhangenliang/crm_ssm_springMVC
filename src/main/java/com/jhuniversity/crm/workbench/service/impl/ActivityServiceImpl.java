package com.jhuniversity.crm.workbench.service.impl;

import com.jhuniversity.crm.settings.dao.UserDao;
import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.vo.Vo;
import com.jhuniversity.crm.workbench.dao.ActivityDao;
import com.jhuniversity.crm.workbench.dao.ActivityRemarkDao;
import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.domain.ActivityRemark;
import com.jhuniversity.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.awt.desktop.SystemSleepEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private UserDao userDao;
    @Resource
    private ActivityDao activityDao;
    @Resource
    private ActivityRemarkDao activityRemarkDao;
    @Override
    public boolean save(Activity activity) {
        System.out.println("进入service部分");
        boolean flag=true;
        Integer count=activityDao.save(activity);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Vo<Activity> pageList(Map<String, Object> map) {
        System.out.println("进入到分页查询service部分");
        int total=activityDao.getTotalByCondition(map);
        List<Activity>activityList=activityDao.getActivityByCondition(map);
        Vo<Activity>vo=new Vo<>();
        vo.setTotal(total);
        vo.setActivities(activityList);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        System.out.println("进入删除操作service部分");
        boolean flag=true;
        //查看 活动详情表中有与要删除的活动信息关联的有几条信息
        int count1=activityRemarkDao.getCountByAids(ids);
        //返回删除后受影响的数量
        int count2=activityRemarkDao.deleteByAids(ids);
        if (count1!=count2){
            flag=false;
        }
        //删除市场活动
        int count3=activityDao.delete(ids);
        if (count3!=ids.length){
            flag=false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        System.out.println("进入修改操作页面service");
        //取出uList
        List<User>uList=userDao.getUser();
        //取activity
        Activity activity=activityDao.getById(id);
        Map<String,Object>map=new HashMap<>();
        map.put("uList",uList);
        map.put("activity",activity);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        System.out.println("进入更新页面的service操作");
        boolean flag=true;
        int count=activityDao.update(activity);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        System.out.println("进入到活动查询详细信息service部分");
        Activity activity=activityDao.getByIdForDetail(id);
        return activity;
    }

    @Override
    public  List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> list=activityRemarkDao.getRemarkListByAid(activityId);
        return list;
    }

    @Override
    public boolean deleteRemarkById(String id) {
        System.out.println("进入到活动备注删除service部分");
        boolean flag=true;
        int count=activityRemarkDao.deleteRemarkById(id);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        System.out.println("进入到活动备注更新service部分");
        boolean flag=true;
        int count=activityRemarkDao.updateRemark(activityRemark);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        System.out.println("进入到活动备注添加service部分");
        boolean flag=true;
        int count=activityRemarkDao.saveRemark(ar);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityListById(String clueId) {
        System.out.println("进入线索与活动关联service");
        List<Activity> aList=activityDao.getActivityListById(clueId);
        return aList;
    }

    @Override
    public List<Activity> getActivityListByNameAndNoClueId(Map<String, String> map) {
        System.out.println("进入打开关联活动窗口service");
        List<Activity>aList=activityDao.getActivityListByNameAndNoClueId(map);
        return aList;
    }
}
