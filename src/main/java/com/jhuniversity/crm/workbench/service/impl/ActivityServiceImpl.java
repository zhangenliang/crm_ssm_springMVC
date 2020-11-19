package com.jhuniversity.crm.workbench.service.impl;

import com.jhuniversity.crm.workbench.dao.ActivityDao;
import com.jhuniversity.crm.workbench.domain.Activity;
import com.jhuniversity.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityDao activityDao;
    @Override
    public Integer save(Activity activity) {
        System.out.println("进入service部分");
        Integer count=activityDao.save(activity);
        return count;
    }
}
