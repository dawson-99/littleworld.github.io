package com.dawson.filter;


import com.alibaba.fastjson.JSON;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.LoginUser;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.utils.JwtUtil;
import com.dawson.utils.RedisCache;
import com.dawson.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 这个过滤层是用在security之前的，并且需要过滤的网站的。
 * 如果用户登录了的话，是可以取得用户的，接下来的security
 * 层才可以通过。
 */



@Configuration
public class JwtAuthenticatonFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //前端会发送token过来
        String token = httpServletRequest.getHeader("token");

        //如果没有携带token，那就说明是不需要登录就可以访问的网站
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String userId = null;

        try {
            userId = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            //token超时  token非法
            //响应告诉前端需要重新登录
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            //因为这是在controller外，所以返回不会是json的格式。所以这里要用工具格式化一下再返回去
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(responseResult));
            return;
        }

        //从redis中去取得
        LoginUser loginUser = redisCache.getCacheObject("adminlogin:" + userId);
        //如果获取不到,说明redis中已经没有了，重新去的登录
        if(Objects.isNull(loginUser)){
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }

        //从源码中可以看出来，如果是2个参数，会把flag设置成false；如果是3个参数，会把flag设置成true。这样后面的其它过滤层才可以通过.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);

        //将获取的认证信息给holder，让后面的过滤层可以得到
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
