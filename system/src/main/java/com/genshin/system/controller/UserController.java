package com.genshin.system.controller;

import com.genshin.system.dao.UserProfile;
import com.genshin.system.service.UserService;
import dto.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@SuppressWarnings("rawtypes")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public JsonResult registerUser(@RequestBody UserProfile userProfile){
        return  userService.insertUser(userProfile);
    }

    @GetMapping("/adviceTest")
    public String adviceTest(){
        return "test";
    }
//
//    @GetMapping("/exceptionTest")
//    public void exceptionTest() throws Exception {
//        throw new Exception("test");
//    }

    @PreAuthorize("hasAnyAuthority('normal','admin')")
    @GetMapping("/loginTest")
    public String loginTest(){
        return "success";
    }

}
