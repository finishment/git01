package com.xxx.crm.base;


import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BaseController {


    @ModelAttribute // 表示当前方法会在控制的其它方法执行之前执行
    public void preHandler(HttpServletRequest request){
        request.setAttribute("ctx", request.getContextPath());
    }


    public ResultInfo success(){
        return new ResultInfo();
    }

    public ResultInfo success(String msg){
        ResultInfo resultInfo= new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public ResultInfo success(String msg,Object result){
        ResultInfo resultInfo= new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }

}
