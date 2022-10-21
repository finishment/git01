package com.xxx.crm.service;

import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.PermissionMapper;
import com.xxx.crm.entity.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService extends BaseService<Permission, Integer> {

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 根据用户ID查询当前用户拥有的资源的权限码，需要经过角色来查询资源
     * @param userId
     * @return
     */
    public List<String> queryUserHasPermissionsByUserId(Integer userId) {
        return permissionMapper.queryUserHasPermissionsByUserId(userId);
    }
}
