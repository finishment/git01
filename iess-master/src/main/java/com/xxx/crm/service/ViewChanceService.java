package com.xxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.ViewChanceMapper;
import com.xxx.crm.query.ViewChanceQuery;
import com.xxx.crm.utils.AssertUtil;
import com.xxx.crm.vo.ViewChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ViewChanceService extends BaseService<ViewChance, Integer> {
    @Resource
    private ViewChanceMapper viewChanceMapper;

    /**
     *  多条件分页查询（返回格式必须满足layui中数据表要求的格式）
     */
    public Map<String, Object> queryViewChanceByParams(ViewChanceQuery viewChanceQuery){
        Map<String,Object> map = new HashMap<>();

        //开启分页
        PageHelper.startPage(viewChanceQuery.getPage(),viewChanceQuery.getLimit());

        //得到对应分页对象
        PageInfo<ViewChance> pageInfo = new PageInfo<>(viewChanceMapper.selectByParams(viewChanceQuery));

        //设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        //设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }

    /**
     * 面试信息数据添加
     *      1.参数校验
     *          面试者姓名是否为空
     *          面试官是否为空
     *      2.设置相关参数默认值
     *          时间
     *          逻辑判断值
     *
     *      3.执行添加，判断结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveViewChance(ViewChance viewChance){
        //1.参数校验
        checkViewChanceParams(viewChance.getInterviewerName(),viewChance.getInterviewer());

        //2.设置相关参数
        viewChance.setIsValid(1);
        viewChance.setUpdateDate(new Date());
        viewChance.setCreateDate(new Date());

        //3.执行添加，判断结果
        AssertUtil.isTrue(viewChanceMapper.insertSelective(viewChance) < 1,"面试信息添加失败！");
    }

    private void checkViewChanceParams(String interviewerName, String interviewer) {
        AssertUtil.isTrue(StringUtils.isBlank(interviewerName),"面试者姓名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(interviewer),"面试官不能够为空！");

    }

    /**
     * 面经数据更新
     *      1.参数校验
     *      2.设置相应的参数值
     *      3.执行更新，判断结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateViewChance(ViewChance viewChance){
        //1.参数校验
        AssertUtil.isTrue(null == viewChance.getId(),"待更新记录不存在!");
        //通过id查询记录
        ViewChance temp = viewChanceMapper.selectByPrimaryKey(viewChance.getId());
        //判断是否为空
        AssertUtil.isTrue(null == temp,"待更新记录不存在！");
        //参数校验
        checkViewChanceParams(viewChance.getInterviewerName(),viewChance.getInterviewer());

        //2.设置相关参数值
        viewChance.setUpdateDate(new Date());

        //3.执行更新，判断结果
        AssertUtil.isTrue(viewChanceMapper.updateByPrimaryKeySelective(viewChance) <1,"面经数据更新失败！");
    }

    /**面经数据删除*/
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteViewChance(Integer[] ids){
        AssertUtil.isTrue(null == ids || ids.length == 0,"待删除数据不存在！");
        AssertUtil.isTrue(viewChanceMapper.deleteBatch(ids) < 0,"面经数据删除失败!");
    }


}
