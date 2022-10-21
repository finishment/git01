package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultBean;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.entity.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {

    @RequestMapping("/upload")
    public ResultInfo add(){
        ResultInfo resultInfo =new ResultInfo();
        return resultInfo;
    }
}
