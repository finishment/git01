package com.xxx.crm.controller;


import cn.hutool.core.util.StrUtil;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.entity.Collections;
import com.xxx.crm.entity.Note;
import com.xxx.crm.entity.User;
import com.xxx.crm.query.CommentQuery;
import com.xxx.crm.service.CollectionService;
import com.xxx.crm.service.CommentService;
import com.xxx.crm.service.NoteService;
import com.xxx.crm.service.NoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController extends BaseController {
    @Autowired
    CommentService commentService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CollectionService collectionService;
//    private NoteService noteService = new NoteService();

    /**
     * 删除云记
     1. 接收参数 （noteId）
     2. 调用Service层删除方法，返回状态码 （1=成功，0=失败）
     3. 通过流将结果响应给ajax的回调函数 （输出字符串）
     * @param request
     * @param response
     */
    @RequestMapping("/delete")
    private void noteDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 1. 接收参数 （noteId）
        String noteId = request.getParameter("noteId");
        // 2. 调用Service层删除方法，返回状态码 （1=成功，0=失败）
        Integer code = noteService.deleteNote(noteId);
        // 3. 通过流将结果响应给ajax的回调函数 （输出字符串）
        response.getWriter().write(code + "");
        response.getWriter().close();
    }

    /**
     * 查询云记详情
     1. 接收参数 （noteId）
     2. 调用Service层的查询方法，返回Note对象
     3. 将Note对象设置到request请求域中
     4. 设置首页动态包含的页面值
     5. 请求转发跳转到index.jsp
     */
    @RequestMapping("/detail")
    private String noteDetail(HttpServletRequest request, Model model)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("loginUser");
        String userId = String.valueOf(user.getId());
        String noteId = request.getParameter("noteId");
        List<CommentQuery> list = commentService.listByNoteId(noteId);
        Collections collections =collectionService.findCollection(userId,noteId);
        request.setAttribute("collected", collections);
        // 第一层
        if(list.size() > 0 ){
            for (int i = 0; i < list.size(); i++) {
                // 递归查询下层
                getList(list.get(i));
            }
        }

        model.addAttribute("clist",list);
        // 1. 接收参数 （noteId）
        // 2. 调用Service层的查询方法，返回Note对象
        Note note = noteService.findNoteById(noteId);

        // 3. 将Note对象设置到request请求域中
        request.setAttribute("note", note);
        // 4. 设置首页动态包含的页面值
        return "/discuss/detail";
    }
    // 查询下级评论
    private void getList(CommentQuery comment){

        List<CommentQuery> comments = commentService.listEqCommId(String.valueOf(comment.getId()));

        // 判断comments是否为空
        if(comments.size() > 0 ){
            comment.setCommentList(comments);
        }

    }
    /**
     * 添加或修改操作
     1. 接收参数 （类型ID、标题、内容）
     2. 调用Service层方法，返回resultInfo对象
     3. 判断resultInfo的code值
     如果code=1，表示成功
     重定向跳转到首页 index
     如果code=0，表示失败
     将resultInfo对象设置到request作用域
     请求转发跳转到note?actionName=view
     * @param request
     * @param response
     */
    @RequestMapping("/addOrUpdate")
    private String addOrUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // 1. 接收参数 （类型ID、标题、内容）
        String typeId = request.getParameter("typeId");
        String title = request.getParameter("title");
        User user = (User) request.getSession().getAttribute("loginUser");
        Integer userId = user.getId();
        String content = request.getParameter("content");

        // 获取经纬度
        String lon = request.getParameter("lon");
        String lat = request.getParameter("lat");

        // 如果是修改操作，需要接收noteId
        String noteId = request.getParameter("noteId");

        // 2. 调用Service层方法，返回resultInfo对
        ResultInfo resultInfo = noteService.addOrUpdate(typeId,userId, title, content, noteId, lon, lat);

        // 3. 判断resultInfo的code值
        if (resultInfo.getCode() == 1) {
            if (noteId==null){
                return "redirect:/discuss/index";
            }
            // 重定向跳转到首页 index
            return "forward:/note/detail";
        } else {
            // 将resultInfo对象设置到request作用域
            request.setAttribute("resultInfo", resultInfo);

            return "forward:/note/view";
        }

    }
    /**
     * 进入发布云记页面
     1. 从Session对象中获取用户对象
     2. 通过用户ID查询对应的类型列表
     3. 将类型列表设置到request请求域中
     4. 设置首页动态包含的页面值
     5. 请求转发跳转到index.jsp
     * @param request
     * @param response
     */
    @RequestMapping("/view")
    private String noteView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* 修改操作 */
        // 得到要修改的云记ID
        String noteId = request.getParameter("noteId");
        // 通过noteId查询云记对象
        Note note = noteService.findNoteById(noteId);
        // 将note对象设置到请求域中
        request.setAttribute("noteInfo", note);
        /* 修改操作 */
        // 1. 从Session对象中获取用户对象
//        User user = (User) request.getSession().getAttribute("loginUser");
        // 2. 通过用户ID查询对应的类型列表
//        List<NoteType> typeList = new NoteTypeService().findTypeList(user.getId());
        // 3. 将类型列表设置到request请求域中
//        request.setAttribute("typeList", typeList);
        return "/discuss/view";
    }
}
