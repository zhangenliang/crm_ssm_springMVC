package com.jhuniversity.crm.workbench.dao;

import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemarkById(String id);

    int updateRemark(ActivityRemark activityRemark);

    int saveRemark(ActivityRemark ar);

}
