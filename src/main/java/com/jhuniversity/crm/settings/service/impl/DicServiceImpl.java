package com.jhuniversity.crm.settings.service.impl;

import com.jhuniversity.crm.settings.dao.DicTypeDao;
import com.jhuniversity.crm.settings.dao.DicValueDao;
import com.jhuniversity.crm.settings.domian.DicType;
import com.jhuniversity.crm.settings.domian.DicValue;
import com.jhuniversity.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DicServiceImpl implements DicService {
    @Resource
    private DicValueDao dicValueDao;
    @Resource
    private DicTypeDao dicTypeDao;
    @Override
    public Map<String, List<DicValue>> getAll() {
        //将字典类型取出
        List<DicType>dtList=dicTypeDao.getTypeList();
        System.out.println("service部分list"+dtList);
        Map<String,List<DicValue>>map=new HashMap<>();
        for (DicType list:dtList){
            String code=list.getCode();
            //通过类型在value中找出对应的职业放入List集合中。然后以类型作为键，集合作为值放入map
            List<DicValue>dcList=dicValueDao.getValueList(code);
            map.put(code,dcList);
        }
        return map;
    }
}
