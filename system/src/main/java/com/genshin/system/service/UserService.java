package com.genshin.system.service;

import com.genshin.system.dao.UserProfile;
import dto.response.JsonResult;

@SuppressWarnings("rawtypes")
public interface UserService {

    JsonResult insertUser(UserProfile userProfile);


    JsonResult getUserAuthorities(String userEmail);


    JsonResult getUserPassword(String userEmail);


    JsonResult deleteAccount(String userEmail);


}
