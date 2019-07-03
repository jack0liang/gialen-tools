package com.gialen.tools.api.interceptor;

import com.alibaba.fastjson.JSON;
import com.gialen.common.model.GLResponse;
import com.gialen.common.model.ResponseStatus;
import com.gialen.tools.api.annotation.RequireLogin;
import com.gialen.tools.common.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录拦截器
 * @author lupeibo
 * @date 2019-06-28
 */
@Component
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 处理handler;
        if (handler instanceof HandlerMethod) {
            // 判断当前method上是否有标签;
            HandlerMethod hm = (HandlerMethod) handler;
            // 若需要登录，判断用户是否已登录
            if (hm.getMethodAnnotation(RequireLogin.class) != null) {
                String token = request.getHeader("token");
                //token为空或无此token，返回登录
                if(StringUtils.isBlank(token) || null == TokenUtil.tokenUserIdCache.getIfPresent(token)) {
                    return responseNeedLogin(response);
                }
                String userId = request.getParameter("userId");
                if(StringUtils.isNotBlank(userId)
                        && TokenUtil.tokenUserIdCache.getIfPresent(token) != Long.parseLong(userId)) {
                    return responseNeedLogin(response);
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    private boolean responseNeedLogin(HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter pw = response.getWriter();
        String responseContent = JSON.toJSONString(GLResponse.fail(ResponseStatus.LOGIN_INVALID.getCode(), ResponseStatus.LOGIN_INVALID.getMsg()));
        pw.write(responseContent);
        pw.flush();
        pw.close();
        return false;
    }
}
