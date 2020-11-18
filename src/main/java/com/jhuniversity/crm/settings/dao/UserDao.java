package com.jhuniversity.crm.settings.dao;

import com.jhuniversity.crm.settings.domian.User;

import java.util.Map;

public interface UserDao {
    User login(Map<String,String> map);
}
