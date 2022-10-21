package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.entity.Announcement;
import com.xxx.crm.query.AnnouncementQuery;
import com.xxx.crm.query.UserQuery;
import com.xxx.crm.service.AnnounceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("announcement")
public class AnnouncementController extends BaseController {

    @Resource
    private AnnounceService announceService;

    @RequestMapping("index")
    public String index(){
        return "announcement/announcement";
    }

    @ResponseBody
    @RequestMapping("list")
    public Map<String, Object> selectByParams(AnnouncementQuery announcementQuery) {
        return announceService.queryByParamsForTable(announcementQuery);
    }

    @ResponseBody
    @PostMapping("add")
    public ResultInfo addAnnouncement(Announcement announcement){
        announceService.addAnnouncement(announcement);
        return success("通知添加成功！");
    }
    @ResponseBody
    @PostMapping("update")
    public ResultInfo updateAnnouncement(Announcement announcement){
        announceService.updateAnnouncement(announcement);
        return success("通知更新成功！");
    }

    @RequestMapping("toAddOrUpdateAnnouncementPage")
    public String toAddOrUpdateAnnouncementPage(Integer id, HttpServletRequest request){
        if(id!=null){
            Announcement announcement=announceService.selectByPrimaryKey(id);
            request.setAttribute("announcementInfo",announcement);
        }
        return "announcement/add_update";
    }

    @ResponseBody
    @PostMapping("/delete")
    public ResultInfo deleteAnnouncement(Integer[] ids) {
        announceService.deleteAnnouncementByIds(ids);
        return success("通知记录删除成功!");
    }
}
