package com.dawson.config;


import com.dawson.filter.JwtAuthenticatonFilter;
import com.dawson.handler.security.AccessDeniedHandlerImpl;
import com.dawson.handler.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 关于SpringSecurity的配置在这里。主要是配置了传进来的密码加密方式、
 * 哪些地方不需要登录也可以访问，哪些地方需要认证授权
 */



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    JwtAuthenticatonFilter jwtAuthenticatonFilter;

    @Autowired
    AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf。跨站请求伪造。因为这里我们前后端分离了，所以这里关闭了。其实就是security会生成一个token，如果是其他网站跳转就不能够携带这个token。
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问,只有未登录的情况下才可以访问
                .antMatchers("/user/login").anonymous()
//                .antMatchers("/logout").authenticated()
//                .antMatchers("/user/userInfo").authenticated()
//                .antMatchers("/upload").authenticated()
//                .antMatchers("/user/updateUserInfoUpdate").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().authenticated();

        //关闭默认的注销功能
        http.logout().disable();

        //这里添加之前写好的自己的过滤层。放在security的第一层之前
        http.addFilterBefore(jwtAuthenticatonFilter, UsernamePasswordAuthenticationFilter.class);

        //认证失败处理器，授权失败处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        //这是security的默认注销方法，但是要用自己写的，所以就把它给失效了
        http.logout().disable();
        //允许跨域
        http.cors();
    }

    //设置加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
