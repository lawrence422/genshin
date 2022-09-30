package com.genshin.system.mapper;

import com.genshin.system.dao.UserProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user_profile(user_email,user_password)VALUES(#{userEmail},#{userPassword})")
    int insertUser(UserProfile userProfile);

    @Select("SELECT authorities FROM user_authorities WHERE user_email=#{userEmail}")
    List<String> getUserAuthorities(String userEmail);

    @Select("SELECT count(*) FROM user_profile WHERE user_email=#{userEmail} limit 1")
    int checkUserExist(String userEmail);

    @Select("SELECT user_password FROM user_profile WHERE user_email=#{userEmail}")
    String getUserPassword(String userEmail);

    @Insert("INSERT INTO user_authorities(user_email,authorities)VALUES(#{userEmail},#(authorities))")
    String insertAuthorities(String userEmail,String authorities);

    @Delete("Delete from user_profile WHERE user_email=#{userEmail}")
    int deleteAccount(String userEmail);

}
