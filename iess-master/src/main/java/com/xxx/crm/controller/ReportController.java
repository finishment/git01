package com.xxx.crm.controller;


import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.dao.LeaderMapper;
import com.xxx.crm.entity.ExperienceNum;
import com.xxx.crm.service.NoteService;
import com.xxx.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

    @Autowired
    private NoteService noteService;

    @Resource
    private LeaderMapper leaderMapper;
    /**
     * 查询用户发布云记时的坐标
     * @param request
     * @param response
     */
    @RequestMapping("/location")
    @ResponseBody
    private ResultInfo queryNoteLonAndLat(HttpServletRequest request, HttpServletResponse response)
    {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        ResultInfo resultInfo = noteService.queryNoteLonAndLat(userId);
        // 将ResultInfo对象转换成JSON格式的字符串，响应给AJAX的回调函数
        return resultInfo;
    }

    /**
     * 通过月份查询对应的云记数量
     * @param request
     * @param response
     */
    @RequestMapping("/month")
    @ResponseBody
    private ResultInfo queryNoteCountByMonth(HttpServletRequest request, HttpServletResponse response)
    {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        ResultInfo resultInfo = noteService.queryNoteCountByMonth(userId);
        // 将ResultInfo对象转换成JSON格式的字符串，响应给ajax的回调函数
        return resultInfo;
    }

    /**
     * 进入报表页面
     * @param request
     * @param response
     */
    @RequestMapping("/info")
    private String reportInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return "/report/info";    }


    /**
     * 进入报表页面
     * @param request
     * @param response
     */
    @RequestMapping("/leaderboard")
    private String companyreportInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(leaderMapper.selectTitle());
        // 将ResultInfo对象转换成JSON格式的字符串，响应给ajax的回调函数
        request.setAttribute("resultInfo",resultInfo);

        return "/report/leaderboard";
    }



    @RequestMapping("/name")
    @ResponseBody
    private List<List> selectNum(){
        List<ExperienceNum> list= leaderMapper.selectNum();
        List<Integer> num=new ArrayList<>(list.size());
        List<Integer> userId=new ArrayList<>(list.size());
        List<String> userName=new ArrayList<>(list.size());
        for (int i=0;i<list.size();i++){
            num.add(list.get(i).getNum());
            userId.add(list.get(i).getUserId());
        }
        userName=selectUserNameByUserId(userId);
        List<List> total=new ArrayList<>();
        total.add(num);
        total.add(userId);
        total.add(userName);
        return total;
    }

    private List<String> selectUserNameByUserId(List<Integer> list){
        List<String> userName=new ArrayList<>(list.size());
        for (int i=0;i<list.size();i++){
            userName.add(selectList(list.get(i)));
        }
        return userName;
    }

    private String selectList(Integer userId){
        return String.valueOf(leaderMapper.selectUserNameByUserId(userId));
    }
}
