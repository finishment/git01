package com.xxx.crm.controller;

import com.xxx.crm.annotation.RequiredPermission;
import com.xxx.crm.base.BaseController;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.entity.Role;
import com.xxx.crm.query.RoleQuery;
import com.xxx.crm.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有的角色的id和roleName，一个角色对应一个Map，Map最后放到List集合中
     * @return
     */
    @RequestMapping("/queryAllRoles")
    @ResponseBody
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleService.queryAllRoles(userId);
    }

    /**
     * 分页条件查询角色记录
     * @param roleQuery
     * @return
     */
    @RequiredPermission(code = "602002")
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> selectByParams(RoleQuery roleQuery) {
        return roleService.queryByParamsForTable(roleQuery);
    }

    /**
     * 进入角色管理主页
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "role/role";
    }

    /**
     * 添加角色信息
     * @param role
     * @return
     */
    @RequiredPermission(code = "602001")
    @ResponseBody
    @PostMapping("/add")
    public ResultInfo addRole(Role role) {
        roleService.addRole(role);
        return success("添加角色记录成功!");
    }

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    @RequiredPermission(code = "602003")
    @ResponseBody
    @PostMapping("/update")
    public ResultInfo updateRole(Role role) {
        roleService.updateRole(role);
        return success("更新角色记录成功!");
    }

    @RequiredPermission(code = "602004")
    @ResponseBody
    @PostMapping("/delete")
    public ResultInfo deleteRole(Integer id) {
        roleService.deleteRole(id);
        return success("删除角色记录成功!");
    }

    @RequestMapping("/toAddOrUpdateRolePage")
    public String toAddOrUpdateRolePage(Integer id, HttpServletRequest request) {
        // 判断角色ID是否为空，为空表示添加操作，有值表示修改操作
        if (id != null && id > 0) {
            // 更新操作，执行查询并将结果放到作用域中
            Role role = roleService.selectByPrimaryKey(id);
            request.setAttribute("roleInfo", role);
        }
        return "role/add_update";
    }

    /**
     * 为角色分配相应资源：即角色授权
     * @param mIds  资源ID数组
     * @param roleId 角色ID
     * @return
     */
    @RequiredPermission(code = "602005")
    @PostMapping("/addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer mIds[], Integer roleId) {
        roleService.addGrant(mIds, roleId);
        return success("角色授权成功!");
    }
}
