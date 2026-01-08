package com.carepaws.service;

import com.carepaws.dto.admin.UserLoginDTO;
import com.carepaws.entity.User;

public interface UserService {

    /**
     * 微信登录
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}

