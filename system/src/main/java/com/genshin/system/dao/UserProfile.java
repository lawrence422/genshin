package com.genshin.system.dao;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public class UserProfile implements UserDetails, Serializable {
    private String userEmail;
    private String userPassword;
    private String username;
    private List<GrantedAuthority> authorities;

    public UserProfile(){}

    public UserProfile(String userEmail,String userPassword,List<GrantedAuthority>authorities){
        this.userEmail=userEmail;
        this.userPassword=userPassword;
        this.authorities=authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
