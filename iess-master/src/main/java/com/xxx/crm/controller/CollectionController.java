package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;

import com.xxx.crm.entity.Collections;
import com.xxx.crm.entity.Comment;
import com.xxx.crm.entity.Note;
import com.xxx.crm.entity.User;
import com.xxx.crm.enums.StateStatus;
import com.xxx.crm.query.CollectionQuery;
import com.xxx.crm.service.CollectionService;
import com.xxx.crm.utils.CookieUtil;
import com.xxx.crm.utils.LoginUserUtil;
import com.xxx.crm.utils.Page;
import com.xxx.crm.vo.NoteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/collection")
@Controller
public class CollectionController extends BaseController {

    @Autowired
    private CollectionService collectionService;
    /**
     *删除收藏记录
     */
//    @PostMapping("delete")
//    @ResponseBody
//    public ResultInfo deleteCollection(int collectionId){
//        //调用service删除方法
//        collectionService.deleteCollection(collectionId);
//
//        return success("删除收藏记录成功");
//    }

    /**
     * 进入用户收藏页面
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request,
                         String title, String date, String typeId,String menu) {

        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        // 2. 获取Session作用域中的user对象
        User user = (User) request.getSession().getAttribute("loginUser");

        CollectionQuery collectionQuery =new CollectionQuery();
        if(title!=null){
            collectionQuery.setTitle(title);
        }
        if (pageNum!=null) {
            collectionQuery.setPage(Integer.valueOf(pageNum));
        }
        if (pageSize!=null) {
            collectionQuery.setLimit(Integer.valueOf(pageSize));
        }
        collectionQuery.setUserId(user.getId());
        // 3. 调用Service层查询方法，返回Page对象
        List<CollectionQuery> collections= collectionService.queryCollectionByParams(collectionQuery);
        // 4. 将page对象设置到request作用域中

        Page<CollectionQuery> page=new Page<>(collectionQuery.getPage(),collectionQuery.getLimit(),collections.size());
        page.setDataList(collections);
        if (page!=null){
            page.setPageSize(5);
            request.setAttribute("page", page);
        }
//        // 通过日期分组查询当前登录用户下的云记数量
//        List<NoteVo> dateInfo = collectionService.findNoteCountByDate(user.getId());
//        // 设置集合存放在request作用域中
//        request.getSession().setAttribute("dateInfo", dateInfo);

        request.setAttribute("menu",menu);
        return "user/collections";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo add(@RequestBody Collections collections ,HttpServletRequest request) {

        // 调用Service层添加营销机会
        collectionService.addCollection(collections);
        request.setAttribute("collected", collections);
        ResultInfo resultInfo =new ResultInfo();
        resultInfo.setCode(200);
        resultInfo.setMsg("收藏记录添加成功！");
        return resultInfo;
    }

    @PostMapping("delete/{noteId}")
    @ResponseBody
    public ResultInfo delete(@PathVariable Integer noteId,HttpServletRequest request) {

        Integer[] ids = new Integer[1];
        ids[0]=noteId;
        // 调用service层执行逻辑删除
        collectionService.deleteBatch(ids);
        request.removeAttribute("collected");
        ResultInfo resultInfo =new ResultInfo();
        resultInfo.setCode(200);
        resultInfo.setMsg("收藏记录删除成功！");
        return resultInfo;
    }

    @PostMapping("delete/")
    @ResponseBody
    public ResultInfo deleteCollections(Integer[] ids) {
        // 调用service层执行逻辑删除
        collectionService.deleteBatch(ids);
        System.out.println(ids);
        return success("收藏记录删除成功!");
    }


}
