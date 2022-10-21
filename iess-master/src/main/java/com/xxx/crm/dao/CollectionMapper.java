package com.xxx.crm.dao;

import com.xxx.crm.base.BaseMapper;
import com.xxx.crm.entity.Collections;
import com.xxx.crm.entity.User;

import com.xxx.crm.query.CollectionQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface CollectionMapper extends BaseMapper<Collections, Integer>{

    @Update("update iess.t_collection " +
            "set type_id=#{type_id},note_id=#{note_id},company_id=#{company_id},create_date=#{create_date},is_valid=#{is_valid} where id=#{id}")
    int deleteCollection(int id);

    @Select("select * from iess.t_collection where user_id=#{userId} and note_id=#{noteId} and is_valid=1")
    Collections findCollection(String userId, String noteId);
    @Update("update iess.t_collection " +
            "set is_valid=1 where user_id=#{user_id} and note_id=#{note_id}")
    void updateUserCollection(Integer user_id, Integer note_id);


    //多条件查询的接口不需要单独定义,定义在BaseMapper中

    /**
     * 通过用户id查询用note记录，并返回对象(userId  t_note.title)
     * @param
     * @return
     */
    public List<CollectionQuery> selectByUserIdOrTitle(@Param("userId") Integer userId,@Param("title") String title);

}
