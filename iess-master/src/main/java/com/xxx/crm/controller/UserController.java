package com.xxx.crm.controller;

import com.xxx.crm.annotation.RequiredPermission;
import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.dao.LeaderMapper;
import com.xxx.crm.entity.Note;
import com.xxx.crm.entity.StudentGroup;
import com.xxx.crm.entity.User;
import com.xxx.crm.query.CommentQuery;
import com.xxx.crm.model.UserModel;
import com.xxx.crm.query.UserQuery;

import com.xxx.crm.service.CommentService;
import com.xxx.crm.service.IndexService;
import com.xxx.crm.service.NoteService;
import com.xxx.crm.service.UserService;
import com.xxx.crm.utils.LoginUserUtil;
import com.xxx.crm.utils.Page;
import com.xxx.crm.vo.NoteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
@Slf4j
@MultipartConfig
public class UserController extends BaseController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private CommentService commentService;
    @Resource
    private LeaderMapper leaderMapper;





    @GetMapping("/testSessionCookie")
    public void test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println("session = " + session.getId());
        System.out.println("sessionUser = " + session.getAttribute("user"));
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                System.out.println(cookies[i].getName() + " --- " + cookies[i].getValue());
            }
        }
    }

    /**
     * 登录功能
     * @param userName 前台传递的用户名
     * @param userPwd 前台传递的密码
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd) {
        // 通过try catch 捕获service层的异常，如果service抛出异常，则表示登录失败，否则登录成功
        ResultInfo resultInfo = new ResultInfo();
        // 调用service的登录方法
        UserModel userModel = userService.userLogin(userName, userPwd);
        // 设置ResultInfo的result值（返回的数据）
        resultInfo.setResult(userModel);

        return resultInfo;
    }

    /**
     * 用户修改密码
     * @param request
     * @param oldPwd 原始密码
     * @param newPwd 新密码
     * @param repeatPwd 确认密码
     * @return
     */
    @ResponseBody
    @PostMapping("/updatePwd")
    public ResultInfo updateUserPassword(HttpServletRequest request,
             String oldPwd, String newPwd, String repeatPwd) {
        ResultInfo resultInfo = new ResultInfo();
        // 获取cookie中的userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 调用 Service 层修改密码
        userService.updatePassword(userId, oldPwd, newPwd, repeatPwd);

        return resultInfo;
    }

    /**
     * 进入修改用户信息的页面
     * @return
     */
    @RequestMapping("/toSettingPage")
    public String toSetting() {
        return "user/setting";
    }
    /**
     * 进入修改密码的页面
     * @return
     */
    @RequestMapping("/toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }


    /**
     * 查询所有的销售人员
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryAllSalesman")
    public List<Map<String, Object>> queryAllSalesman() {
        return userService.queryAllSalesman();
    }


    /**
     * 查询所有的客户经理
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryAllCustomerManagers")
    public List<Map<String, Object>> queryAllCustomerManagers() {
        return userService.queryAllCustomerManagers();
    }


    /**
     * 分页多条件查询用户列表
     * @param userQuery
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> selectByParams(UserQuery userQuery) {
        return userService.queryByParamsForTable(userQuery);
    }

    /**
     * 进入用户列表页面
      * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "user/user";
    }
    /**
     * 进入用户内容页面
     * @return
     */
    @RequestMapping("/indexx")
    public String indexx(HttpServletRequest request,
                         String title, String date, String typeId,String menu) {
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        // 2. 获取Session作用域中的user对象
        User user = (User) request.getSession().getAttribute("loginUser");
        // 3. 调用Service层查询方法，返回Page对象
        Page<Note> page = indexService.noteList(user.getId(), title, date, typeId,pageNum, pageSize);
        // 4. 将page对象设置到request作用域中
        if (page!=null){
            page.setPageSize(5);
            request.setAttribute("page", page);
        }
        // 通过日期分组查询当前登录用户下的云记数量
        List<NoteVo> dateInfo = noteService.findNoteCountByDate(user.getId());
        // 设置集合存放在request作用域中
        request.getSession().setAttribute("dateInfo", dateInfo);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(leaderMapper.selectTitle());
        // 将ResultInfo对象转换成JSON格式的字符串，响应给ajax的回调函数
        request.setAttribute("resultInfo",resultInfo);

        request.setAttribute("menu",menu);
        return "user/index";
    }
    /**
     * 进入用户评论页面
     * @return
     */
    @RequestMapping("/comment")
    public String comment(HttpServletRequest request,String menu,String content) {
        // 2. 获取Session作用域中的user对象
        User user = (User) request.getSession().getAttribute("loginUser");
        List<CommentQuery> list=commentService.commentList(user.getId(),content);
        request.setAttribute("comments",list);
        request.setAttribute("menu",menu);
        return "user/comment";
    }
    /**
     * 进入添加/更新用户表单页面
     * @param id 用户主键ID，id有值表示修改，无值表示添加
     * @return
     */
    @RequestMapping("/toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id, HttpServletRequest request) {
        if (id != null && id > 0) {
            User user = userService.selectByPrimaryKey(id);
            request.setAttribute("user", user);
        }
        return "user/add_update";
    }

    /**
     * 添加用户记录
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public ResultInfo addUser(User user) {
        userService.addUser(user);
        return success("用户记录添加成功!");
    }


    /**
     * 更新用户记录
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        return success("用户记录更新成功!");
    }


    /**
     * 批量逻辑删除用户记录
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public ResultInfo deleteUser(Integer[] ids) {
        log.info("ids [{}] "+ Arrays.toString(ids));
        userService.deleteUserByIds(ids);
        return success("用户记录删除成功!");
    }

    /**
     * 用户注册
     * */
    @PostMapping("registerss")
    @ResponseBody//表明返回的是对象不是页面,因为是ajx请求
    public ResultInfo userRegister(@RequestBody User user){
        ResultInfo resultInfo =new ResultInfo();
        userService.userRegister(user);
        resultInfo.setCode(200);
        resultInfo.setMsg("注册成功!");
        return resultInfo;
    }

    /**
     * 用户头像oss服务
     * */
    @PostMapping("oss")
    public String oss(@RequestParam("img") MultipartFile img , HttpServletRequest request, HttpServletResponse response){
//        CommonsMultipartFile cimg = (CommonsMultipartFile) img;
        ResultInfo resultInfo = userService.oss(img,request,response);
        User user= (User) resultInfo.getResult();
        if (resultInfo.getCode()==1){
            // 更新session中用户对象
            request.getSession().setAttribute("loginUser", user);
        }
        request.getSession().setAttribute("resultInfo",resultInfo);

        return "user/setting";
    }
    /**
     * 进入(班长/组长/组员/同学)管理页面
     * @return
     */
    @RequestMapping("/monitor")
    public String monitor(){
        return "user/monitor";
    }

    @RequestMapping("/leader")
    public String Leader(){
        return "user/leader";
    }

    @RequestMapping("/student")
    public String student(){
        return "user/student";
    }

    @RequestMapping("/class")
    public String classList(){
        return "user/class";
    }

    /**
     * 分页多条件查询(班长/组长/学员/同学)列表
     * @param userQuery
     * @return
     */
    @RequiredPermission(code = "6050")
    @ResponseBody
    @RequestMapping("/monitorList")
    public Map<String, Object> selectMonitorByParams(UserQuery userQuery) {
        return userService.selectMonitorByParams(userQuery);
    }

    @RequiredPermission(code = "6060")
    @ResponseBody
    @RequestMapping("/leaderList")
    public Map<String, Object> selectLeaderByParams(UserQuery userQuery, HttpServletRequest request) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return userService.selectLeaderByParams(userQuery,userId);
    }

    @RequiredPermission(code = "6070")
    @ResponseBody
    @RequestMapping("/studentList")
    public Map<String, Object> selectStudentByParams(UserQuery userQuery, HttpServletRequest request) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return userService.selectStudentByParams(userQuery,userId);
    }

    @RequiredPermission(code = "6080")
    @ResponseBody
    @RequestMapping("/classList")
    public Map<String, Object> selectClassByParams(UserQuery userQuery, HttpServletRequest request) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return userService.selectClassByParams(userQuery,userId);
    }

    /**
     * 进入修改班组别页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/toAddOrUpdateClassGroup")
    public String toAddOrUpdateClassGroup(Integer id, HttpServletRequest request) {
        if (id != null && id > 0) {
            StudentGroup studentGroup = userService.selectGroupByPrimaryKey(id);
            if (studentGroup==null) {
                studentGroup = new StudentGroup();
                studentGroup.setUserId(id);
            }
            request.setAttribute("student", studentGroup);
        }
        return "user/add_update_class";
    }

    /**
     * 修改/操作班组别
     * @param studentGroup
     * @return
     */
    @RequiredPermission(code = "6090")
    @ResponseBody
    @PostMapping("/addOrUpdateGroup")
    public ResultInfo addOrUpdateGroup(StudentGroup studentGroup) {
        userService.addOrUpdateGroup(studentGroup);
        return success("操作成功!");
    }
}
