package com.genshin.system.filter;

import com.genshin.system.dao.UserProfile;
import com.genshin.system.mapper.UserMapper;
import com.genshin.system.service.impl.UserDetailServiceImpl;
import com.genshin.tools.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader!=null){
            String accessToken=authHeader.replace("Bearer","");
            if (JWTUtils.validateToken(accessToken)) {
                String userEmail = JWTUtils.getUserEmail(accessToken);
                if (userEmail != null && userMapper.checkUserExist(userEmail) == 1) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(userEmail);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
