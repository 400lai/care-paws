package com.carepaws.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.carepaws.constant.MessageConstant;
import com.carepaws.dto.admin.UserLoginDTO;
import com.carepaws.entity.User;
import com.carepaws.exception.LoginFailedException;
import com.carepaws.mapper.UserMapper;
import com.carepaws.properties.WeChatProperties;
import com.carepaws.service.UserService;
import com.carepaws.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    /**
     * 微信登录
     */
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        //判断openid是否为空，如果为空表示登录失败，抛出业务异常
        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //判断当前用户是否为新用户
        User user = userMapper.getByOpenid(openid);

        //如果是新用户，自动完成注册
        if(user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        //返回这个用户对象
        return user;
    }

    /**
     * 调用微信接口服务，获取微信用户的openid
     */
    private String getOpenid(String code) {
        try {
            //调用微信接口服务，获得当前微信用户的openid
            Map<String, String> map = new HashMap<>();
            map.put("appid", weChatProperties.getAppid());
            map.put("secret", weChatProperties.getSecret());
            map.put("js_code", code);
            map.put("grant_type", "authorization_code");
            String json = HttpClientUtil.doGet(WX_LOGIN, map);

            JSONObject jsonObject = JSON.parseObject(json);

            // 检查是否包含错误信息
            if (jsonObject.containsKey("errcode")) {
                log.error("调用微信API失败: {}", json);
                return null;
            }

            String openid = jsonObject.getString("openid");
            return openid;
        } catch (Exception e) {
            log.error("获取微信openid异常", e);
            return null;
        }
    }
}
