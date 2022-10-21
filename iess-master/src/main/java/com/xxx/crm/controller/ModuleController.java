package com.xxx.crm.controller;

import com.xxx.crm.annotation.RequiredPermission;
import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.entity.Module;
import com.xxx.crm.query.TreeModel;
import com.xxx.crm.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/module")
@Slf4j
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    /**
     * 点击 授权 后查询所有资源列表
     * @return
     */
    @RequiredPermission(code = "603002")
    @ResponseBody
    @RequestMapping("/queryAllModules")
    public List<TreeModel> queryAllModules(Integer roleId) {
        return moduleService.queryAllModules(roleId);
    }

    /**
     * 进入授权页面
     * @param roleId 要对哪个角色进行授权
     * @return
     */
    @RequiredPermission(code = "603002")
    @RequestMapping("/toGrantPage")
    public String toGrantPage(Integer roleId, HttpServletRequest request) {
        log.info("roleId = " + roleId);
        // 将需要授权的角色的ID值设置到请求域中
        request.setAttribute("roleId", roleId);
        return "role/grant";
    }

    /**
     * 查询资源列表，且返回的数据格式是符合layui数据表格所需的格式的
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> queryModuleList() {
        return moduleService.queryModuleList();
    }

    /**
     * 进入资源（即菜单）管理页面
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "module/module";
    }

    /**
     * 添加资源记录
     * @param module
     * @return
     */
    @RequiredPermission(code = "603001")
    @ResponseBody
    @PostMapping("/add")
    public ResultInfo addModule(Module module) {
        moduleService.addModule(module);
        return success("添加资源记录成功!");
    }

    /**
     * 更新资源记录
     * @param module
     * @return
     */
    @RequiredPermission(code = "603003")
    @ResponseBody
    @PostMapping("/update")
    public ResultInfo updateModule(Module module) {
        moduleService.updateModule(module);
        return success("更新资源记录成功!");
    }

    /**
     * 删除资源记录
     * @param id
     * @return
     */
    @RequiredPermission(code = "603004")
    @ResponseBody
    @PostMapping("/delete")
    public ResultInfo updateModule(Integer id) {
        moduleService.deleteModule(id);
        return success("删除资源记录成功!");
    }

    /**
     * 进入添加资源的页面
     * @param grade 层级
     * @param parentId 父菜单ID
     * @return
     */
    @RequestMapping("/toAddModulePage")
    public String toAddModulePage(Integer grade,Integer parentId, HttpServletRequest request) {
        // 将数据设置到请求域中
        request.setAttribute("grade", grade);
        request.setAttribute("parentId", parentId);
        return "module/add";
    }

    /**
     * 进入更新资源的页面
     * @param id 主键ID
     * @return
     */
    @RequestMapping("/toUpdateModulePage")
    public String toUpdateModulePage(Integer id, Model model) {
        // 将要修改的资源对象设置到请求域中
        model.addAttribute(moduleService.selectByPrimaryKey(id));
        return "module/update";
    }


}
