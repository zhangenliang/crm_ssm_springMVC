package com.jhuniversity.crm.settings.service;

import com.jhuniversity.crm.exception.LoginException;
import com.jhuniversity.crm.settings.domian.User;

public interface UserService {
    User login(String loginAct, String loginPwd,String ip) throws LoginException;
}
