package com.dawson.handler.security;

import com.alibaba.fastjson.JSON;
import com.dawson.domain.ResponseResult;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.utils.WebUtils;
import com.dawson.domain.ResponseResult;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 这个类是用来封装授权失败返回类型的样子的
 */


@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
