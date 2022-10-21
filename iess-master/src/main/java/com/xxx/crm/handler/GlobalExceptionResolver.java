package com.xxx.crm.handler;

import com.alibaba.fastjson.JSON;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.exceptions.AuthException;
import com.xxx.crm.exceptions.NoLoginException;
import com.xxx.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;



@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    /**
     * 异常处理方法
     * 思路：
     *  控制器方法的返回值：
     *      1.返回视图
     *      2.返回JSON数据
     *  如何判断方法的返回值是视图还是JSON数据？
     *      通过方法上是否声明 @ResponseBody 注解
     *          如果未声明此注解，则表示返回视图
     *          如果声明了此注解，则表示返回JSON数据
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 方法对象
     * @param e 异常对象
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

        /**
         * 方法请求拦截
         *    判断是否抛出未登录异常
         *      如果抛出该异常，则要求用户登录，重定向跳转到登录页面
         */
        if (e instanceof NoLoginException) {
            // 重定向到登陆页面
            return  new ModelAndView("redirect:/index");
        }


        /**
         * 设置默认的异常处理（返回视图）
         */
        ModelAndView modelAndView = new ModelAndView("error");
        // 设置异常信息
        modelAndView.addObject("code", 500);
        modelAndView.addObject("msg", "系统异常，请重试...");

        // 判断 HandlerMethod
        if (handler instanceof HandlerMethod) {
            // 类型转换
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上声明的 @ResponseBody 注解，如果没有则返回空
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            // 判断 ResponseBody 对象是否为空（为空则表示返回的是视图；不为空表示返回的是JSON数据）
            if (responseBody == null) {
                /*
                 * 方法返回视图
                 */
                // 判断异常类型
                if (e instanceof ParamsException) {
                    // 参数异常
                    ParamsException p = (ParamsException) e;
                    // 设置异常现象
                    modelAndView.addObject("code", p.getCode());
                    modelAndView.addObject("msg", p.getMsg());
                } else if (e instanceof AuthException) {
                    // 参数异常
                    AuthException a = (AuthException) e;
                    // 设置异常现象
                    modelAndView.addObject("code", a.getCode());
                    modelAndView.addObject("msg", a.getMsg());
                }
                return modelAndView;
            } else {
                /*
                 * 方法返回数据
                 */
                // 设置默认的异常处理
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500).setMsg("出现异常，请重试...");

                // 判断异常类型是否是自定义异常
                if (e instanceof ParamsException) {
                    // 自定义参数异常
                    ParamsException p = (ParamsException) e;
                    // 设置异常现象
                    resultInfo.setCode(p.getCode());
                    resultInfo.setMsg(p.getMsg());
                } else if (e instanceof AuthException) {
                    // 自定义权限认证异常
                    AuthException a = (AuthException) e;
                    // 设置异常现象
                    resultInfo.setCode(a.getCode());
                    resultInfo.setMsg(a.getMsg());
                }
                // 设置响应类型及编码格式(响应JSON格式的数据)
                response.setContentType("application/json;charset=utf-8");
                BufferedWriter bw = null;
                try {
                    // 得到字符输出流
                    bw = new BufferedWriter(response.getWriter());
                    // 将需要返回的对象转成JSON格式的字符
                    String json = JSON.toJSONString(resultInfo);
                    // 输出数据
                    bw.write(json);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    // 释放资源
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                return null;
            }
        }
        return modelAndView;
    }
}
