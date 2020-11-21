package com.jhuniversity.crm.workbench.service;

import com.jhuniversity.crm.vo.Vo;
import com.jhuniversity.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    Vo<Activity> pageList(Map<String,Object> map);

    boolean delete(String[] ids);

    Map<String,Object> getUserListAndActivity(String id);

    boolean update(Activity activity);
}
