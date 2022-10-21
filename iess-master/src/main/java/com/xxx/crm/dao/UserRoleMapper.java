package com.xxx.crm.dao;

import com.xxx.crm.base.BaseMapper;
import com.xxx.crm.entity.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole, Integer> {

    /**
     * 根据用户ID查询用户角色记录数量 t_user_role
     * @param userId
     * @return
     */
    Integer countUserRoleByUserId(Integer userId);

    /**
     * 根据用户ID删除用户角色记录 t_user_role
     * @param userId
     * @return
     */
    Integer deleteUserRoleByUserId(Integer userId);
}