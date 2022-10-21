package com.xxx.crm.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.crm.base.BaseQuery;
import com.xxx.crm.dao.CollectionMapper;
import com.xxx.crm.entity.Collections;

import com.xxx.crm.query.CollectionQuery;
import com.xxx.crm.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    /**
     * 删除收藏记录
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBatch(Integer[] ids){
        //判断id是否为空
        AssertUtil.isTrue(ids == null ||ids.length < 1,"请选择要删除的收藏");
        //执行删除(更新)操作,判断受影响行数
        AssertUtil.isTrue(collectionMapper.deleteBatch(ids) ==0,"收藏记录删除失败");

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addCollection(Collections collection) {
        /* 1.校验参数 */
        checkCollectionParams(collection.getUser_id(),collection.getNote_id());
        /* 2.设置相关字段的默认值 */
        // 设置该条记录有效 isValid = 1
        collection.setIs_valid(1);
        // createDate 创建时间 默认当前时间
        collection.setCreate_date(new Date());
        Collections collection1 = findCollection(String.valueOf(collection.getUser_id()), String.valueOf(collection.getNote_id()));
        if (collection1!=null){
            collectionMapper.updateUserCollection(collection1.getUser_id(),collection1.getNote_id());
        }else {
            //3.执行添加操作，判断受影响行数
            AssertUtil.isTrue(collectionMapper.insertSelective(collection) != 1, "添加营销机会失败！");
        }
    }

    private void checkCollectionParams(Integer user_id,Integer note_id) {
        //
        AssertUtil.isTrue(user_id==null, "用户id不能为空！");
        // linkMan      联系人    非空
        AssertUtil.isTrue(note_id==null, "帖子id不能为空！");
    }


    public Collections findCollection(String userId, String noteId) {
        return collectionMapper.findCollection(userId,noteId);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteCollection(Integer userId) {
        // 判断ids是否为空
        AssertUtil.isTrue(null == userId, "待删除记录不存在!");
        // 判断受影响的行数
        AssertUtil.isTrue(collectionMapper.deleteByPrimaryKey(userId) >0, "营销机会数据删除失败!");
    }

    public List<CollectionQuery> queryCollectionByParams(CollectionQuery collectionQuery){

        // 开启分页 参数1：当前页 参数2：页面大小
        PageHelper.startPage(collectionQuery.getPage(), collectionQuery.getLimit());
        // 得到对应的分页对象
        List<CollectionQuery> collections = collectionMapper.selectByUserIdOrTitle(collectionQuery.getUserId(),collectionQuery.getTitle());


        return collections;
    }
}
