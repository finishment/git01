package com.xxx.crm.service;

import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.ModuleMapper;
import com.xxx.crm.dao.PermissionMapper;
import com.xxx.crm.dao.RoleMapper;
import com.xxx.crm.entity.Permission;
import com.xxx.crm.entity.Role;
import com.xxx.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private ModuleMapper moduleMapper;


    /**
     * 查询所有角色的 id 和 roleName，并将其封装为Map放到List中
     * @return
     */
    public List<Map<String, Object>> queryAllRoles(Integer userId) {
        return roleMapper.queryAllRoles(userId);
    }

    /**
     * 添加角色
     * 1.参数校验
     *      roleName角色名称        非空唯一
     *      roleRemark用户备注      非空
     * 2.默认参数
     *      is_valid是否有效    1=有效
     *      createDate创建时间      当前相同时间
     *      updateDate更新时间      当前相同时间
     * 3.执行添加，判断影响行数
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addRole(Role role) {
        /* 1.参数校验 */
        checkParams(role.getRoleName(), role.getRoleRemark(), null);
        /* 2.设置默认参数 */
        role.setIsValid(1).setUpdateDate(new Date()).setCreateDate(new Date());
        /* 3.执行添加，判断影响行数 */
        AssertUtil.isTrue(roleMapper.insertSelective(role) != 1, "添加角色记录失败!");
    }

    /**
     * 修改角色信息
     * 1.参数校验
     *      roleName角色名 非空
     *      roleName角色名 唯一
     *          数据库中不存在该角色名的记录，添加成功
     *          数据库中已存在该角色名的记录
     *              当前角色ID 与 数据库中的角色ID相同，通过
     *              当前角色ID 与 数据库中的角色ID不同，不通过
     *      roleMark角色备注 非空
     * 2.设置默认参数
     *      updateDate更新时间  当前系统时间
     * 3.执行更新，判断受影响行数
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateRole(Role role) {
        /* 1.参数校验 */
        checkParams(role.getRoleName(), role.getRoleRemark(), role.getId());
        /* 2.默认参数 */
        role.setUpdateDate(new Date());
        /* 3.执行更新，判断受影响行数 */
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) != 1, "更新角色记录失败!");
    }

    /**
     * 参数校验
     *      roleName角色名称        非空唯一
     *      roleRemark用户备注      非空
     * @param roleName
     * @param roleRemark
     */
    private void checkParams(String roleName, String roleRemark, Integer roleId) {
        // roleName角色名称 非空唯一
        AssertUtil.isTrue(StringUtils.isBlank(roleName), "角色名称不能为空!");
        // roleRemark用户备注      非空
        AssertUtil.isTrue(StringUtils.isBlank(roleRemark), "角色备注信息不能为空!");
        // 通过roleName查询角色
        Role temp = roleMapper.queryRoleByName(roleName);
        // roleName角色名称 唯一
        AssertUtil.isTrue(temp != null && !roleId.equals(temp.getId()) , "该角色已存在!");
    }

    /**
     * 逻辑删除角色
     *  1.参数校验
     *      roleId角色ID  非空，是否存在
     *  2.设置相关参数的默认值
     *      是否有效    0（删除记录）
     *      更新时间    当前系统时间
     *  3.执行更新操作，判断受影响行数
     * @param roleId
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteRole(Integer roleId) {
        /* 1.参数校验 */
        AssertUtil.isTrue( roleId == null || roleId < 1, "数据异常,请重试!");
        // 根据角色ID查询角色
        Role temp = roleMapper.selectByPrimaryKey(roleId);
        AssertUtil.isTrue(temp == null , "数据异常,请重试!");
        /* 2.设置相关参数的默认值 */
        temp.setIsValid(0).setUpdateDate(new Date());
        /* 3.执行更新操作，判断受影响行数 */
        AssertUtil.isTrue(roleMapper.deleteRole(temp) != 1, "删除角色记录失败!");
    }

    /**
     * 为角色分配相应资源：即角色授权
     *  将对应的角色ID与资源ID添加到对应的权限表中（t_permission）
     *      直接添加权限：不合适，会出现重复的权限数据
     *      推荐使用：
     *          先将已有的权限记录删除，再将需要设置的权限记录添加
     *           1. 通过角色ID查询对应的权限记录
     *           2. 如果该角色有权限，则删除该角色拥有的权限记录
     *           3. 如果有权限记录，则添加(批量添加)
     *
     * @param mIds
     * @param roleId
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addGrant(Integer[] mIds, Integer roleId) {
        // 1. 通过角色ID查询对应的权限记录
        Integer count = permissionMapper.countPermissionByRoleId(roleId);
        // 2. 如果该角色有权限，则删除该角色拥有的权限记录
        if (count > 0) {
            // 删除权限记录
            permissionMapper.deleteByRoleId(roleId);
        }
        if (mIds != null && mIds.length > 0) {
            // 定义Permission集合
            List<Permission> permissionList = new ArrayList<>();

            // 遍历资源ID数组
            for (Integer id : mIds) {
                Permission permission = new Permission();
                // 设置资源ID
                permission.setModuleId(id);
                // 设置角色ID
                permission.setRoleId(roleId);
                // 设置授权码
                permission.setAclValue(moduleMapper.selectByPrimaryKey(id).getOptValue());
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                // 将对象设置到集合中
                permissionList.add(permission);
            }
            // 执行批量添加操作，并判断受影响行数
            AssertUtil.isTrue(permissionMapper.insertBatch(permissionList) != permissionList.size(), "角色授权失败!");
        }
    }
}
