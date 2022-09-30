package com.genshin.system.service.impl;

import com.genshin.system.dao.UserProfile;
import com.genshin.system.mapper.UserMapper;


import com.genshin.tools.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        if (userMapper.checkUserExist(userEmail)==1){

            String userPassword=userMapper.getUserPassword(userEmail);
            List<String> authorities=userMapper.getUserAuthorities(userEmail);
//            System.out.println(StringUtils.printStrings(userPassword));
//            System.out.println(authorities);
            if (authorities!=null&&authorities.size()!=0) {
                return new UserProfile(userEmail, userPassword, AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(authorities)));
            }else {
                return new UserProfile(userEmail, userPassword, AuthorityUtils.commaSeparatedStringToAuthorityList("normal"));
            }
        }
            throw new UsernameNotFoundException("The user isn't exist.");

    }
}
