package com.xxx.crm.interceptor;

import com.xxx.crm.dao.UserMapper;
import com.xxx.crm.exceptions.NoLoginException;
import com.xxx.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserMapper userMapper;

    /**
     * 拦截用户判断用户是否处于登录状态
     *  预先处理方法：在目标方法（目标资源）执行前，执行的方法
     *
     *  方法返回值
     *      true：表示目标方法可以被执行
     *      false：表示阻止目标方法的执行
     *  判断用户是否处于登录状态
     *      1.判断cookie中是否存在用户信息（获取用户ID）
     *      2.数据库中是否存在指定用户的ID
     *  如果用户处于登录状态，则允许目标方法执行；如果用户没有登录，则抛出异常（在全局异常中做判断，如果是未登录异常，则跳转到登陆页面）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取cookie中的用户ID
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 判断用户ID是否为空，且数据库中存在该ID的记录
        if (null == userId || userId < 1 || userMapper.selectByPrimaryKey(userId) == null) {
            // 抛出未登录异常
            throw new NoLoginException();
        }
        return true;
    }
}
