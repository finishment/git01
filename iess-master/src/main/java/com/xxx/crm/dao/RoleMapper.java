package com.xxx.crm.dao;

import com.xxx.crm.base.BaseMapper;
import com.xxx.crm.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role, Integer> {

    /**
     * 查询所有角色信息
     *  需要从数据库中查询出id和role_name字段，并使用数据库的case行数创建
     *  自定义字段selected，用于设置formSelects js插件下拉多选的选中状态
     * 数据格式,如：
     *      map1.put("id",1); map1.put('roleName','xxx'); map1.put("selected",'');
     *      map2.put("id",2); map1.put('roleName','xxx'); map2.put("selected","selected");
     *      list.add(map1); list.add(map2);
     * @param userId 用户id
     * @return   用户id为空：查询所有的角色信息，且selected自定义字段值均为空
     *           用户id不为空，查询所有角色信息
     *              如果当前用户具有该权限，则selected自定义字段值为selected
     *              如果当前用户不具有该权限，则selected自定义字段置为空值
     *
     */
    List<Map<String,Object>> queryAllRoles(Integer userId);

    /**
     * 根据用户名查询角色是否存在
     * @param roleName
     * @return 大于等于1表示存在  小于1表示不存在
     */
    Role queryRoleByName(String roleName);

    /**
     * 逻辑删除角色记录
     * @param role
     * @return
     */
    Integer deleteRole(Role role);
}