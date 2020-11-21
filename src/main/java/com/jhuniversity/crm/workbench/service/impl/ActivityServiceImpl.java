package com.jhuniversity.crm.workbench.service.impl;

import com.jhuniversity.crm.settings.dao.UserDao;
import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.vo.Vo;
import com.jhuniversity.crm.workbench.dao.ActivityDao;
import com.jhuniversity.crm.workbench.dao.ActivityRemarkDao;
import com.jhuniversity.crm.workbench.domain.Activity;
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
}
