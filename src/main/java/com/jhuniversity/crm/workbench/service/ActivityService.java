package com.jhuniversity.crm.workbench.service;

import com.jhuniversity.crm.vo.Vo;
import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    Vo<Activity> pageList(Map<String,Object> map);

    boolean delete(String[] ids);

    Map<String,Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List< ActivityRemark>  getRemarkListByAid(String activityId);

    boolean deleteRemarkById(String id);

    boolean updateRemark(ActivityRemark activityRemark);

    boolean saveRemark(ActivityRemark ar);

    List<Activity> getActivityListById(String clueId);

    List<Activity> getActivityListByNameAndNoClueId(Map<String,String> map);
}
