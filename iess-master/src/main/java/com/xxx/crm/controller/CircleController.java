package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.entity.Note;
import com.xxx.crm.entity.User;
import com.xxx.crm.service.*;
import com.xxx.crm.utils.Page;
import com.xxx.crm.vo.NoteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("circle")
@Slf4j
public class CircleController extends BaseController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private TeaseService teaseService;
    @Autowired
    private XuYuanService xuYuanService;
    @Autowired
    private ZuFangService zuFangService;

    /**
     * 进入摸鱼圈
     * @return
     */
    @RequestMapping("moYu")
    public String moYu(){
        return "circle/moYu";
    }

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
        Page<Note> page = indexService.teaseListNotByUserId(user.getId(), title, date, typeId,pageNum, pageSize);
        // 4. 将page对象设置到request作用域中
        request.setAttribute("page", page);
        // 通过日期分组查询当前登录用户下的云记数量
        List<NoteVo> dateInfo = teaseService.findNoteCountByDate(user.getId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("dateInfo", dateInfo);
        // 通过类型分组查询当前登录用户下的云记数量
        List<NoteVo> typeInfo = teaseService.findNoteCountByType(user.getId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("typeInfo", typeInfo);
        return "/tease/index";
    }



    @RequestMapping("/xuYuan")
    public String xuYuanList(HttpServletRequest request, HttpServletResponse response,
                           String title, String date, String typeId) {
//        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring.xml");
//        NoteService noteService=(NoteService) ctx.getBean("NoteService");
        // 1. 接收参数 （当前页、每页显示的数量）
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        // 2. 获取Session作用域中的user对象
        User user = (User) request.getSession().getAttribute("loginUser");
        // 3. 调用Service层查询方法，返回Page对象
        Page<Note> page = indexService.xuYuanListNotByUserId(user.getId(), title, date, typeId,pageNum, pageSize);
        // 4. 将page对象设置到request作用域中
        request.setAttribute("page", page);
        // 通过日期分组查询当前登录用户下的云记数量
        List<NoteVo> dateInfo = xuYuanService.findNoteCountByDate(user.getId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("dateInfo", dateInfo);
        // 通过类型分组查询当前登录用户下的云记数量
        List<NoteVo> typeInfo = xuYuanService.findNoteCountByType(user.getId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("typeInfo", typeInfo);
        return "/xuYuan/index";
    }


    @RequestMapping("/zuFang")
    public String zuFangList(HttpServletRequest request, HttpServletResponse response,
                             String title, String date, String typeId) {
//        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring.xml");
//        NoteService noteService=(NoteService) ctx.getBean("NoteService");
        // 1. 接收参数 （当前页、每页显示的数量）
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        // 2. 获取Session作用域中的user对象
        User user = (User) request.getSession().getAttribute("loginUser");
        // 3. 调用Service层查询方法，返回Page对象
        Page<Note> page = indexService.zuFangListNotByUserId(user.getId(), title, date, typeId,pageNum, pageSize);
        // 4. 将page对象设置到request作用域中
        request.setAttribute("page", page);
        // 通过日期分组查询当前登录用户下的云记数量
        List<NoteVo> dateInfo = zuFangService.findNoteCountByDate(user.getId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("dateInfo", dateInfo);
        // 通过类型分组查询当前登录用户下的云记数量
        List<NoteVo> typeInfo = zuFangService.findNoteCountByType(user.getId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("typeInfo", typeInfo);
        return "/zuFang/index";
    }


}
