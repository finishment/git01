package com.xxx.crm.controller;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.query.ViewChanceQuery;
import com.xxx.crm.service.ViewChanceService;
import com.xxx.crm.utils.CookieUtil;
import com.xxx.crm.utils.LoginUserUtil;
import com.xxx.crm.vo.ViewChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/view_chance")
public class ViewChanceController extends BaseController {
    @Resource
    private ViewChanceService viewChanceService;

    /**
     *  多条件分页查询
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> queryViewChanceByParams(ViewChanceQuery viewChanceQuery,Integer flag,HttpServletRequest request){
        if (flag != null && flag ==1){
            //获取当前用户登录id
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            viewChanceQuery.setAssignId(userId);
        }
        return viewChanceService.queryViewChanceByParams(viewChanceQuery);
    }

    @RequestMapping("/index")
    public String index(){
        return "viewChance/view_chance";
    }

    /**
     *  面经数据添加
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultInfo saveViewChance(HttpServletRequest request, ViewChance viewChance){
        //从cookie中获取当前登录的用户名
        String userName = CookieUtil.getCookieValue(request,"userName");
        //设置用户名到面经数据对象
        viewChance.setCreateMan(userName);
        //调用service层的添加方法
        viewChanceService.saveViewChance(viewChance);
        return success("面经数据添加成功！");
    }

    /**
     * 面经数据更新
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultInfo updateViewChance(ViewChance viewChance){
        //更新面经数据
        viewChanceService.updateViewChance(viewChance);
        return success("面经数据更新成功！");
    }
    //页面转发
    @RequestMapping("addOrUpdateViewChancePage")
    public String addOrUpdateViewChancePage(Integer id, Model model){
        //如果id不为空，表示修改，修改操作需要查询被修改的数据
        if (id != null){
            //通过主键查询数据
            ViewChance viewChance = viewChanceService.selectByPrimaryKey(id);
            //将数据存到作用域中
            model.addAttribute("viewChance",viewChance);
        }
        return "viewChance/add_update";
    }

    /**
     * 面经数据删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo deleteViewChance(Integer[] ids){
        viewChanceService.deleteBatch(ids);
        return success("面经数据删除成功！");
    }

}
