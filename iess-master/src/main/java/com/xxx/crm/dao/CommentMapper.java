package com.xxx.crm.dao;

import com.xxx.crm.entity.Comment;
import com.xxx.crm.query.CommentQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CommentMapper  {

//    @Select("select id,comm_id, parent_id, note_id, user_id, content, create_time, is_valid from iess.t_comment where note_id=#{noteId} and parent_id is null")
    List<CommentQuery> selectCommentsByNoteId(String noteId);
//    @Select("select t2.id,t2.comm_id, t1.user_id as parent_id, t2.note_id, t2.user_id, t2.content, t2.create_time, t2.is_valid from iess.t_comment t1,iess.t_comment t2 where t1.id=t2.parent_id and t2.comm_id=#{commentId} and t2.parent_id is not null")
    List<CommentQuery> selectCommentsByCommentId(String commentId);

    @Insert("insert into iess.t_comment(comm_id, parent_id, note_id, user_id, content, create_time) " +
            "VALUES(#{commId},#{parentId},#{noteId},#{userId},#{content},#{createTime})")
    void saveComment(Comment comment);
    @Delete("delete from iess.t_comment where parent_id=#{parent_id}")
    void deleteByParentId(int parent_id);
    @Delete("delete from iess.t_comment where id=#{id}")
    void deleteById(Integer id);

//    @Select("select  t1.title,t2.id,t2.comm_id, t2.parent_name , t2.note_id,t2.userId, t2.user_name, t2.content, t2.create_time, t2.is_valid\n" +
//            "from   iess.t_note t1 ,(SELECT t2.id,t2.comm_id, t1.user_name as parent_name, t2.note_id,t2.userId, t2.user_name, t2.content, t2.create_time, t2.is_valid\n" +
//            "                        FROM t_user t1,(select t2.id,t2.comm_id,t2.parent_id,t1.user_id, t1.user_id as parent_name, t2.note_id,t3.id as userId, t3.user_name as user_name, t2.content, t2.create_time, t2.is_valid\n" +
//            "                                        from iess.t_comment t1,iess.t_comment t2 ,t_user t3\n" +
//            "                                        where t1.id=t2.parent_id and t2.user_id=t3.id  and t1.user_id=10 and t3.id!=10) t2\n" +
//            "                        WHERE t1.id=t2.parent_name) t2\n" +
//            "where t1.noteId =t2.note_id ")
    List<CommentQuery> commentList(@Param("userId") Integer userId,@Param("content") String content);
}
