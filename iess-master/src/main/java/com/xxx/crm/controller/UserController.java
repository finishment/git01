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
     * ????????????
     * @param userName ????????????????????????
     * @param userPwd ?????????????????????
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd) {
        // ??????try catch ??????service?????????????????????service?????????????????????????????????????????????????????????
        ResultInfo resultInfo = new ResultInfo();
        // ??????service???????????????
        UserModel userModel = userService.userLogin(userName, userPwd);
        // ??????ResultInfo???result????????????????????????
        resultInfo.setResult(userModel);

        return resultInfo;
    }

    /**
     * ??????????????????
     * @param request
     * @param oldPwd ????????????
     * @param newPwd ?????????
     * @param repeatPwd ????????????
     * @return
     */
    @ResponseBody
    @PostMapping("/updatePwd")
    public ResultInfo updateUserPassword(HttpServletRequest request,
             String oldPwd, String newPwd, String repeatPwd) {
        ResultInfo resultInfo = new ResultInfo();
        // ??????cookie??????userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // ?????? Service ???????????????
        userService.updatePassword(userId, oldPwd, newPwd, repeatPwd);

        return resultInfo;
    }

    /**
     * ?????????????????????????????????
     * @return
     */
    @RequestMapping("/toSettingPage")
    public String toSetting() {
        return "user/setting";
    }
    /**
     * ???????????????????????????
     * @return
     */
    @RequestMapping("/toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }


    /**
     * ???????????????????????????
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryAllSalesman")
    public List<Map<String, Object>> queryAllSalesman() {
        return userService.queryAllSalesman();
    }


    /**
     * ???????????????????????????
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryAllCustomerManagers")
    public List<Map<String, Object>> queryAllCustomerManagers() {
        return userService.queryAllCustomerManagers();
    }


    /**
     * ?????????????????????????????????
     * @param userQuery
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> selectByParams(UserQuery userQuery) {
        return userService.queryByParamsForTable(userQuery);
    }

    /**
     * ????????????????????????
      * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "user/user";
    }
    /**
     * ????????????????????????
     * @return
     */
    @RequestMapping("/indexx")
    public String indexx(HttpServletRequest request,
                         String title, String date, String typeId,String menu) {
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        // 2. ??????Session???????????????user??????
        User user = (User) request.getSession().getAttribute("loginUser");
        // 3. ??????Service????????????????????????Page??????
        Page<Note> page = indexService.noteList(user.getId(), title, date, typeId,pageNum, pageSize);
        // 4. ???page???????????????request????????????
        if (page!=null){
            page.setPageSize(5);
            request.setAttribute("page", page);
        }
        // ????????????????????????????????????????????????????????????
        List<NoteVo> dateInfo = noteService.findNoteCountByDate(user.getId());
        // ?????????????????????request????????????
        request.getSession().setAttribute("dateInfo", dateInfo);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(leaderMapper.selectTitle());
        // ???ResultInfo???????????????JSON??????????????????????????????ajax???????????????
        request.setAttribute("resultInfo",resultInfo);

        request.setAttribute("menu",menu);
        return "user/index";
    }
    /**
     * ????????????????????????
     * @return
     */
    @RequestMapping("/comment")
    public String comment(HttpServletRequest request,String menu,String content) {
        // 2. ??????Session???????????????user??????
        User user = (User) request.getSession().getAttribute("loginUser");
        List<CommentQuery> list=commentService.commentList(user.getId(),content);
        request.setAttribute("comments",list);
        request.setAttribute("menu",menu);
        return "user/comment";
    }
    /**
     * ????????????/????????????????????????
     * @param id ????????????ID???id???????????????????????????????????????
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
     * ??????????????????
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public ResultInfo addUser(User user) {
        userService.addUser(user);
        return success("????????????????????????!");
    }


    /**
     * ??????????????????
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        return success("????????????????????????!");
    }


    /**
     * ??????????????????????????????
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public ResultInfo deleteUser(Integer[] ids) {
        log.info("ids [{}] "+ Arrays.toString(ids));
        userService.deleteUserByIds(ids);
        return success("????????????????????????!");
    }

    /**
     * ????????????
     * */
    @PostMapping("registerss")
    @ResponseBody//????????????????????????????????????,?????????ajx??????
    public ResultInfo userRegister(@RequestBody User user){
        ResultInfo resultInfo =new ResultInfo();
        userService.userRegister(user);
        resultInfo.setCode(200);
        resultInfo.setMsg("????????????!");
        return resultInfo;
    }

    /**
     * ????????????oss??????
     * */
    @PostMapping("oss")
    public String oss(@RequestParam("img") MultipartFile img , HttpServletRequest request, HttpServletResponse response){
//        CommonsMultipartFile cimg = (CommonsMultipartFile) img;
        ResultInfo resultInfo = userService.oss(img,request,response);
        User user= (User) resultInfo.getResult();
        if (resultInfo.getCode()==1){
            // ??????session???????????????
            request.getSession().setAttribute("loginUser", user);
        }
        request.getSession().setAttribute("resultInfo",resultInfo);

        return "user/setting";
    }
    /**
     * ??????(??????/??????/??????/??????)????????????
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
     * ?????????????????????(??????/??????/??????/??????)??????
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
     * ???????????????????????????
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
     * ??????/???????????????
     * @param studentGroup
     * @return
     */
    @RequiredPermission(code = "6090")
    @ResponseBody
    @PostMapping("/addOrUpdateGroup")
    public ResultInfo addOrUpdateGroup(StudentGroup studentGroup) {
        userService.addOrUpdateGroup(studentGroup);
        return success("????????????!");
    }
}
