package com.xxx.crm.dao;

import com.xxx.crm.base.BaseMapper;
import com.xxx.crm.entity.Module;
import com.xxx.crm.query.TreeModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    /**
     * 查询所有的资源列表，将其封装到 TreeModel 数据模型中
     * @return
     */
    public List<TreeModel> queryAllModule();

    /**
     * 查询所有的资源列表
     * @return
     */
    public List<Module> queryModuleList();

    /**
     * 根据资源层级和资源名查询资源对象
     * @param grade 资源层级
     * @param moduleName 资源名
     * @return
     */
    Module queryModuleByGradeAndModuleName(@Param("grade") Integer grade, @Param("moduleName") String moduleName);

    /**
     * 根据资源层级和资源URL查询资源对象
     * @param grade 资源层级
     * @param url   资源列表
     * @return
     */
    Module queryModuleByGradeAndUrl(@Param("grade") Integer grade, @Param("url") String url);

    /**
     * 根据权限码查询资源对象
     * @param optValue 权限码
     * @return
     */
    Module queryModuleByOptValue(String optValue);

    /**
     * 根据父id查询资源记录条目
     * @param parentId
     * @return
     */
    Integer countModuleByParentId(Integer parentId);
}