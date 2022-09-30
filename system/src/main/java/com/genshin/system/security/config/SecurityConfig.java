package com.genshin.system.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genshin.system.dao.UserProfile;
import com.genshin.system.filter.JWTAuthenticationFilter;
import com.genshin.system.mapper.UserMapper;
import com.genshin.system.service.impl.UserDetailServiceImpl;
import com.genshin.tools.utils.JWTUtils;
import dto.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Lazy
    @Autowired
    UserDetailsService userDetailsService;

    @Lazy
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(customLoginSuccessHandler());

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST).permitAll()
                .anyRequest().authenticated();

        http
                .csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder byPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailServiceImpl();
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService( userDetailsService)
                .passwordEncoder(byPasswordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setMaxAge(Duration.ofHours(1));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    AuthenticationSuccessHandler customLoginSuccessHandler() {
        return (request, response, authentication) -> {
            // run custom logics upon successful login
            UserProfile userProfile = (UserProfile) authentication.getPrincipal();
            List<String> list=new LinkedList<>();
            Collection<? extends GrantedAuthority> collection=  userProfile.getAuthorities();
            if (collection==null){
                list.add("normal");
            }else {
                for (GrantedAuthority grantedAuthority : collection) {
                    list.add(grantedAuthority.getAuthority());
                }
            }
            String token = null;
            try {
                token = JWTUtils.generateToken(userProfile.getUserEmail(),list);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String, String> responseBody = Collections.singletonMap("token", token);
            PrintWriter printWriter = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonOutput = new ObjectMapper().writeValueAsString(JsonResult.success(responseBody));
            printWriter.println(jsonOutput);
            printWriter.flush();
            printWriter.close();
        };
    }


}
