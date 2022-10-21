package com.xxx.crm.service;

import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.ModuleMapper;
import com.xxx.crm.dao.PermissionMapper;
import com.xxx.crm.entity.Module;
import com.xxx.crm.entity.Permission;
import com.xxx.crm.query.TreeModel;
import com.xxx.crm.utils.AssertUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ModuleService extends BaseService<Module, Integer> {

    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 查询所有的资源列表
     * @return
     */
    public List<TreeModel> queryAllModules(Integer roleId) {
        // 查询所有的资源列表
        List<TreeModel> treeModelList = moduleMapper.queryAllModule();
        // 查询指定角色已经授权过的资源列表（查询角色拥有的资源的ID）
        List<Integer> permissionIds = permissionMapper.queryRoleHasModulesByRoleId(roleId);
        // 判断角色是否拥有资源ID
        if (permissionIds != null && permissionIds.size() > 0) {
            // 循环所有的资源列表，判断用户拥有的资源id中是否有匹配的，如果只有则设置checked属性为true
            treeModelList.forEach(treeModel -> {
                // 判断角色拥有的资源ID中是否有当前遍历的资源的ID
                if (permissionIds.contains(treeModel.getId())) {
                    // 如果包含，则说明角色已经具有该权限了，设置checked为true
                    treeModel.setChecked(true);
                }
            });
        }
        return treeModelList;
    }


    /**
     * 查询所有的资源列表，要求数据格式与layui数据表格需要的一致
     * @return
     */
    public Map<String, Object> queryModuleList() {
        Map<String, Object> map = new HashMap<>();
        // 查询资源列表
        List<Module> moduleList = moduleMapper.queryModuleList();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", moduleList.size());
        map.put("data", moduleList);
        return map;
    }

    /**
     * 添加资源
     *  1.参数校验
     *      moduleName  模块名称
     *          非空，同一层级下，模块名不可重复
     *      url         地址
     *          二级菜单（grade=1），非空且同一层级下不可重复
     *      parentId    父级菜单
     *          一级菜单（目录 grade=0）    parentId=-1
     *          二级\三级菜单(菜单\按钮,grade=1或2)    非空，且父级菜单必须存在
     *      grade       层级
     *          非空，0\1\2
     *      optValue    权限码
     *          非空，不可重复
     *
     *  2.设置参数的默认值
     *      是否有效    is_valid = 1
     *      创建时间    createDate 当前系统时间
     *      修改时间    updateDate 当前系统时间
     *
     *  3.执行添加操作，判断受影响的行数
     * @param module
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addModule(Module module) {
        /* 1.参数校验 */
        //grade       层级    非空，0\1\2
        Integer grade = module.getGrade();
        AssertUtil.isTrue(grade == null || !(grade == 0 || grade == 1 || grade == 2), "菜单层级不合法!");

        //moduleName  模块名称 非空
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "模块名称不能为空!");
        // moduleName  模块名称  同一层级下，模块名不可重复
        AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName()), "层级名称已存在!");

        // 如果是二级菜单（grade=1）
        if (grade == 1) {
            // url  地址  二级菜单（grade=1）,非空
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "URL不能为空!");
            // url  地址 二级菜单（grade=1  同一层级下不可重复
            AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl()), "URL已存在!");
        }

        // 父级菜单 一级菜单（目录 grade=0）    parentId=-1
        if (grade == 0) {
            module.setParentId(-1);
        }
        //父级菜单 二级\三级菜单(菜单\按钮,grade=1或2)    非空，且父级菜单必须存在
        if (grade != 0) {
            //非空
            AssertUtil.isTrue( null == module.getParentId(), "父级菜单不能为空!");
            //父级菜单必须存在(将父级菜单的ID作为主键查询资源记录)
            AssertUtil.isTrue(null == moduleMapper.selectByPrimaryKey(module.getParentId()), "请指定父级菜单!");
        }

        // optValue  权限码   非空
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "权限码不能为空!");
        // optValue  权限码   不可重复
        AssertUtil.isTrue(null != moduleMapper.queryModuleByOptValue(module.getOptValue()), "权限码已存在!");


        /* 2.设置参数的默认值 */
        //是否有效    is_valid = 1
        module.setIsValid((byte) 1);
        //创建时间    createDate 当前系统时间
        module.setCreateDate(new Date());
        //修改时间    updateDate 当前系统时间
        module.setUpdateDate(new Date());

        /* 3.执行添加操作，判断受影响行数 */
        AssertUtil.isTrue(moduleMapper.insertSelective(module) != 1, "添加资源失败!");
    }


    /**
     * 修改资源记录
     *  1.参数校验
     *      id
     *          非空 数据存在
     *      层级 grade
     *          非空 0|1|2
     *      模块名 moduleName
     *          非空，同一层级模块名称唯一（不包含本身）
     *      地址 url
     *          二级菜单（grade=1），非空且同一层级下不可重复
     *      父级菜单 parentId （不可修改）
     *      权限码optValue
     *          非空，不可重复（不包含本身）
     *
     * 2.设置参数的默认值
     *      修改时间 updateDate 系统当前时间
     *
     * 3.执行更新，判断受影响行数
     *

     *
     *  2.设置参数的默认值
     *
     *  3.执行更新，判断受影响的行数
     * @param module
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateModule(Module module) {
        /* 1.参数校验 */
        // id 非空
        AssertUtil.isTrue(null == module.getId(), "待更新记录不存在!");
        // 通过ID查询资源对象
        Module temp = moduleMapper.selectByPrimaryKey(module.getId());
        // 判断id对应的资源存在
        AssertUtil.isTrue(temp == null , "待更新记录不存在!");

        // 层级 grade 0|1|2
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2), "菜单层级不合法!");

        // moduleName 非空
        AssertUtil.isTrue(null == module.getModuleName(), "模块名称不能为空!");
        // moduleName 同一层级模块名称唯一（不包含本身）
        // 通过 moduleName 和 grade 查询资源对象
        temp = moduleMapper.queryModuleByGradeAndModuleName(grade, module.getModuleName());
        AssertUtil.isTrue( temp != null && !( temp.getId().equals(module.getId()) ), "模块名称已存在!");

        // 地址 url 二级菜单（grade=1） 非空且同一层级下不可重复
        if ( grade == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "菜单URL不能为空!");
            // 通过grade和url查询资源对象
            temp = moduleMapper.queryModuleByGradeAndUrl(grade, module.getUrl());
            // 判断是否存在
            AssertUtil.isTrue(temp != null && !(temp.getId().equals(module.getId())), "该层级下菜单URL已存在!");
        }

        // 权限码 optValue 非空，
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "权限码不能为空!");
        // 通过权限码查询资源对象
        temp = moduleMapper.queryModuleByOptValue(module.getOptValue());
        // 不可重复（不包含本身）
        AssertUtil.isTrue(temp != null && !(temp.getId().equals(module.getId())), "权限码已存在!");

        /* 2.设置默认参数 */
        // 修改时间 updateDate 系统当前时间
        module.setUpdateDate(new Date());

        /* 3.执行更新，判断受影响行数 */
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module) != 1, "更新资源记录失败!");
    }

    /**
     * 删除资源(逻辑删除)
     * 1.判断删除的记录是否存在
     * 2.如果资源存在子记录则不可删除
     * 3.删除资源时，将对应的权限表的记录也删除（判断权限表中是否存在关联数据，如果存在则删除）
     * 4.执行删除操作（逻辑删除），判断受影响的行数
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteModule(Integer id) {
        // 判断id是否为空
        AssertUtil.isTrue(null == id, "待删除记录不存在!");
        // 通过ID查询资源对象
        Module temp = moduleMapper.selectByPrimaryKey(id);
        // 判断资源对象是否为空
        AssertUtil.isTrue( null == temp, "待删除记录不存在!");

        // 判断当前资源是否存在子记录（通过将ID当作父id查询资源记录）
        Integer count = moduleMapper.countModuleByParentId(id);
        // 如果存在子记录，则不可删除
        AssertUtil.isTrue(count > 0, "该资源存在子记录,不可删除!");

        // 通过资源ID查询权限表是否存在数据
        count = permissionMapper.countPermissionByModuleId(id);
        // 判断是否存在，存在则删除
        if (count > 0) {
            // 删除指定资源ID的权限记录
            AssertUtil.isTrue( !count.equals(permissionMapper.deletePermissionByModuleId(id)), "删除资源记录失败!");
        }

        // 设置记录无效
        temp.setIsValid((byte) 0);
        temp.setUpdateDate(new Date());

        // 执行更新，判断受影响行数
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(temp) != 1, "删除资源记录失败!");
    }
}
