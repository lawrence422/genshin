package com.genshin.system.service.impl;

import com.genshin.system.dao.UserProfile;
import com.genshin.system.mapper.UserMapper;
import com.genshin.system.service.UserService;
import dto.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("rawtypes")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JsonResult insertUser(UserProfile userProfile) {
        userProfile.setUserPassword(passwordEncoder.encode(userProfile.getUserPassword()));
        if (userMapper.insertUser(userProfile) == 1) {
            String email = userProfile.getUserEmail();
            List<GrantedAuthority> list = null;
            if (userProfile.getAuthorities() != null) {
                list = new ArrayList<>(userProfile.getAuthorities());
            }
            if (list == null || list.size() == 0) {
                userMapper.insertAuthorities(email, "normal");
            } else {
                for (GrantedAuthority grantedAuthority : list) {
                    userMapper.insertAuthorities(email, grantedAuthority.getAuthority());
                }
            }

            return JsonResult.success("Successfully Register.");
        } else {
            return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

    @Override
    public JsonResult getUserAuthorities(String userEmail) {
        return null;
    }

    @Override
    public JsonResult getUserPassword(String userEmail) {
        return null;
    }

    @Override
    public JsonResult deleteAccount(String userEmail) {
        return null;
    }
}
