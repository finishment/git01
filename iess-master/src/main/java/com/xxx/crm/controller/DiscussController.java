package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.dao.LeaderMapper;
import com.xxx.crm.entity.Note;
import com.xxx.crm.entity.User;
import com.xxx.crm.service.IndexService;
import com.xxx.crm.service.NoteService;
import com.xxx.crm.utils.Page;
import com.xxx.crm.vo.NoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/discuss")
public class DiscussController extends BaseController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private NoteService noteService;
    @Resource
    private LeaderMapper leaderMapper;
    /**
     * 进入交流讨论页面
     * @param request
     * @param response
     */
//    @RequestMapping("/index")
//    private String reportInfo(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        return "/discuss/index";
//    }
    /**
     * 分页查询云记列表
     1. 接收参数 （当前页、每页显示的数量）
     2. 获取Session作用域中的user对象
     3. 调用Service层查询方法，返回Page对象
     4. 将page对象设置到request作用域中
     * @param request
     * @param response
     * @param title 标题
     */
    @RequestMapping("/index")
    public String noteList(HttpServletRequest request, HttpServletResponse response,
                            String title, String date, String typeId) {
//        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring.xml");
//        NoteService noteService=(NoteService) ctx.getBean("NoteService");
        // 1. 接收参数 （当前页、每页显示的数量）
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        // 2. 获取Session作用域中的user对象
        User user = (User) request.getSession().getAttribute("loginUser");
        // 3. 调用Service层查询方法，返回Page对象
        Page<Note> page = indexService.noteListNotByUserId(user.getId(), title, date, typeId,pageNum, pageSize);
        // 4. 将page对象设置到request作用域中
        request.setAttribute("page", page);
        // 通过日期分组查询当前登录用户下的云记数量
        List<NoteVo> dateInfo = noteService.findNoteCountByDate(user.getId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("dateInfo", dateInfo);
        // 通过类型分组查询当前登录用户下的云记数量
        List<NoteVo> typeInfo = noteService.findNoteCountByType(user.getId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("typeInfo", typeInfo);

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(leaderMapper.selectTitle());
        // 将ResultInfo对象转换成JSON格式的字符串，响应给ajax的回调函数
        request.setAttribute("resultInfo",resultInfo);

        return "/discuss/index";
    }
}
