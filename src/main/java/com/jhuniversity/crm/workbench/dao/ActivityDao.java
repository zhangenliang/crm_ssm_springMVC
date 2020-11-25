package com.jhuniversity.crm.workbench.dao;

import com.jhuniversity.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    Integer save(Activity activity);

    int getTotalByCondition(Map<String,Object> map);

    List<Activity> getActivityByCondition(Map<String,Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity getByIdForDetail(String id);

    List<Activity> getActivityListById(String clueId);

    List<Activity> showActivityRelation();

    List<Activity> getActivityListByNameAndNoClueId(Map<String,String> map);
}
