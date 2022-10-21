package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.dao.WelcomeMapper;
import com.xxx.crm.entity.Announcement;
import com.xxx.crm.entity.Welcome;
import com.xxx.crm.entity.Note;
import com.xxx.crm.entity.User;

import com.xxx.crm.service.NoteService;
import com.xxx.crm.service.PermissionService;
import com.xxx.crm.service.UserService;
import com.xxx.crm.utils.LoginUserUtil;
import com.xxx.crm.utils.Page;
import com.xxx.crm.vo.NoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



@Controller
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Resource
    private WelcomeMapper welcomeMapper;

    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("/")
    public String indexx(){
        return "index";
    }

    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 系统界面欢迎页
     * @return
     */
    @RequestMapping("/welcome")
    public String welcome(HttpServletRequest request){
        Welcome announce= new Welcome();
        announce.setAnnounce(welcomeMapper.getAnnounce());
        request.setAttribute("announceInfo",announce);
        return "welcome";
    }
//    @RequestMapping("/welcome01")
//    public String welcome01(HttpServletRequest request){
//        String announce= welcomeMapper.getAnnounce();
//        request.setAttribute("announceInfo",announce);
//        return "redirect:welcome";
//    }
    /**
     * 后端管理主页面
     * @return
     */
    @RequestMapping("/main")
    public String main(HttpServletRequest request){
        // 获取前台cookie中的名为 id 地址
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.selectByPrimaryKey(userId);
        request.getSession().setAttribute("loginUser", user);

        // 通过当前登录用户的ID查询该用户拥有的权限资源列表（查询对应资源的授权码即可）
        List<String> permissions = permissionService.queryUserHasPermissionsByUserId(userId);
        // 将集合设置到session作用域中
        request.getSession().setAttribute("permissions", permissions);

        return "main";
    }

}
