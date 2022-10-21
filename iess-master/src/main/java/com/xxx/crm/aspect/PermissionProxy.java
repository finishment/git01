package com.xxx.crm.aspect;

import com.xxx.crm.annotation.RequiredPermission;
import com.xxx.crm.exceptions.AuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {
    //TODO 后端授权码需要设置
    @Autowired
    private HttpSession session;

    /**
     * 切面会拦截指定包下的指定注解
     *  这里我们配置的是拦截 com.xxx.crm.annotation 包下的 RequiredPermission 注解
     * @param pjp
     * @return
     */
    @Around("@annotation(com.xxx.crm.annotation.RequiredPermission)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;

        // 得到当前登录用户拥有的权限（登录成功进入主页面是已经被存入session作用域中了）
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        // 判断用户是否拥有权限
        if (null == permissions || permissions.size() < 1) {
            // 抛出认证异常
            throw new AuthException();
        }

        // 得到执行的目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 得到方法上的注解
        RequiredPermission requiredPermission = methodSignature.getMethod().getDeclaredAnnotation(RequiredPermission.class);
        // 得到目标方法上的 RequiredPermission 注解的 code 属性值
        String code = requiredPermission.code();
        // 判断注解上对应的状态码
        if (!(permissions.contains(code))) {
            // 无操作权限
            throw new AuthException();
        }


        return pjp.proceed();
    }

}
