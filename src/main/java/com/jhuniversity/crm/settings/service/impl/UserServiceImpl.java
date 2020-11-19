package com.jhuniversity.crm.settings.service.impl;

import com.jhuniversity.crm.exception.LoginException;
import com.jhuniversity.crm.settings.dao.UserDao;
import com.jhuniversity.crm.settings.domian.User;
import com.jhuniversity.crm.settings.service.UserService;
import com.jhuniversity.crm.utils.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Override
    public User login(String loginAct, String loginPwd,String ip) throws LoginException{
        System.out.println("进入serviceImpl验证");
        Map<String,String>map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user=userDao.login(map);
        //验证账号密码
        if (user==null){
            throw new LoginException("账号与密码不符");
        }
        //验证失效时间
        String expireTime=user.getExpireTime();
        String currentTime=DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime)<0){
            throw new LoginException("该账号已失效");
        }
        //验证锁定状态
        String lockState=user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("该账号已锁定");
        }
        //验证ip地址
        String allowIps=user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginException("ip地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUser() {
        List<User>ulist=userDao.getUser();
        return ulist;
    }


}
