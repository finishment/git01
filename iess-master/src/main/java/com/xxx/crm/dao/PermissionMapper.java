package com.xxx.crm.dao;

import com.xxx.crm.base.BaseMapper;
import com.xxx.crm.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission, Integer> {

    /**
     * 根据角色ID查询权限记录条目
     * @param roleId
     * @return
     */
    Integer countPermissionByRoleId(Integer roleId);

    /**
     * 根据角色ID删除权限记录
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);

    /**
     * 查询指定ID的角色拥有的资源的ID的集合
     * @param roleId
     * @return
     */
    List<Integer> queryRoleHasModulesByRoleId(Integer roleId);

    /**
     * 根据用户ID查询当前用户拥有的资源的权限码，需要经过角色来查询资源
     * @param userId
     * @return
     */
    List<String> queryUserHasPermissionsByUserId(Integer userId);

    /**
     * 根据资源ID查询权限记录条目
     * @param id 资源ID
     * @return
     */
    Integer countPermissionByModuleId(Integer moduleId);

    /**
     * 根据资源ID删除权限记录
     * @param id 资源ID
     */
    Integer deletePermissionByModuleId(Integer moduleId);
}