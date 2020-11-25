package com.jhuniversity.crm.workbench.service.impl;

import com.jhuniversity.crm.settings.dao.UserDao;
import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.utils.UUIDUtil;
import com.jhuniversity.crm.vo.Vo;
import com.jhuniversity.crm.workbench.dao.*;
import com.jhuniversity.crm.workbench.domain.*;
import com.jhuniversity.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private ClueDao clueDao;
    @Resource
    private UserDao userDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;
    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Resource
    private ActivityDao activityDao;


    @Override
    public List<User> getUser() {
        List<User>uList=userDao.getUser();
        return uList;
    }

    @Override
    public boolean save(Clue clue) {
        System.out.println("进入保存线索service部分");
        boolean flag=true;
        int count=clueDao.save(clue);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Vo<Clue> pageClueList(Map<String, Object> map) {
        int total=clueDao.getTotal(map);
        List<Clue>clues=clueDao.getClue(map);
        Vo<Clue>vo=new Vo<>();
        vo.setClues(clues);
        vo.setTotal(total);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        System.out.println("进入线索删除service");
        boolean flag=true;
        //查询出要删除的数量
        int count1=clueRemarkDao.getCountByAids(ids);
        //返回删除后受影响的数量
        int count2=clueRemarkDao.deleteByAids(ids);
        if (count1!=count2){
            flag=false;
        }
        //删除市场活动
        int count3=clueDao.delete(ids);
        if (count3!=ids.length){
            flag=false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndClue(String id) {
        System.out.println("进入修改操作页面service");
        //取出uList
        List<User>uList=userDao.getUser();
        //取clue
        Clue clue=clueDao.getById(id);
        Map<String,Object>map=new HashMap<>();
        map.put("uList",uList);
        map.put("c",clue);
        return map;
    }

    @Override
    public boolean update(Clue clue) {
        System.out.println("进入修改页面service部分");
        boolean flag=true;
        int count=clueDao.update(clue);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Clue detail(String id) {
        System.out.println("进入线索详细信息模块service部分");
        Clue clue=clueDao.detail(id);
        return clue;
    }

    @Override
    public List<ClueRemark> showRemarkList(String clueId) {
        System.out.println("进入线索备注展示service部分");
        List<ClueRemark>clueRemarks=clueRemarkDao.showRemarkList(clueId);
        return clueRemarks;
    }

    @Override
    public boolean deleteRemarkById(String id) {
        System.out.println("进入备注删除service");
        boolean flag=true;
        int count=clueRemarkDao.deleteRemarkById(id);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ClueRemark cr) {
        System.out.println("进入线索备注修改service");
        boolean flag=true;
        int count =clueRemarkDao.updateRemark(cr);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ClueRemark cr) {
        System.out.println("进入线索备注添加service");
        boolean flag=true;
        int count =clueRemarkDao.saveRemark(cr);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean unbund(String id) {
        System.out.println("进入解除关联service");
        boolean flag=true;
        int count=clueActivityRelationDao.unbund(id);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public List<Activity> showActivityRelation() {
        System.out.println("进入关联市场活动service部分");
        List<Activity>activities=activityDao.showActivityRelation();
        return activities;
    }

    @Override
    public boolean bund(String cid, String[] aids) {
        System.out.println("进入添加关联service");
        boolean flag=true;
        for (String aid:aids){
            ClueActivityRelation car=new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(aid);
            int count=clueActivityRelationDao.bund(car);
            if (count!=1){
                flag=false;
            }
        }
        return flag;
    }


}
