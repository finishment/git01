package com.xxx.crm.service;


import com.xxx.crm.base.BaseService;
import com.xxx.crm.dao.AnnounceMapper;
import com.xxx.crm.entity.Announcement;
import com.xxx.crm.entity.User;
import com.xxx.crm.utils.AssertUtil;
import com.xxx.crm.utils.Md5Util;
import com.xxx.crm.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AnnounceService  extends BaseService<Announcement,Integer>{

    @Resource
    private AnnounceMapper announceMapper;

    /**
     * 添加通知：
     *  1.参数校验
     *      内容     非空
     *      时间     非空，
     *  2.设置参数的默认值
     *      createDate 当前系统时间
     *  3.执行添加操作，判断受影响行数
     *
     * @param announcement 通知
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addAnnouncement(Announcement announcement) {
        /* 1.参数校验 */
        checkAnnouncementParams(announcement.getAnnouncement());

        /* 2.设置默认参数 */
        announcement.setCreateDate(new Date());

        /* 3.执行添加操作，判断受影响行数 */
        AssertUtil.isTrue(announceMapper.insertSelective(announcement) != 1, "通知记录添加失败!");
    }

    private void checkAnnouncementParams(String announcement) {
        AssertUtil.isTrue(StringUtils.isBlank(announcement), "通知内容不能为空!");
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateAnnouncement(Announcement announcement){
        AssertUtil.isTrue(announcement.getId() == null , "待更新通知不存在!");
        Announcement temp=announceMapper.selectByPrimaryKey(announcement.getId());
        AssertUtil.isTrue(temp == null , "待更新通知不存在!");
        checkAnnouncementParams(announcement.getAnnouncement());
        announcement.setCreateDate(new Date());
        AssertUtil.isTrue(announceMapper.updateByPrimaryKeySelective(announcement) != 1, "通知记录更新失败!");
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteAnnouncementByIds(Integer[] ids) {
        // 判断id数组是否为空
        AssertUtil.isTrue(ids == null || ids.length < 1, "待删除通知不存在!");
        AssertUtil.isTrue(announceMapper.deleteBatch(ids) != ids.length, "数据异常,请重试!");
    }
}
