package com.xxx.crm.dao;

import com.xxx.crm.entity.Note;
import com.xxx.crm.vo.NoteVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface XuYuanMapper {
    Note selectNote(int noteId);

    int findNoteCount(@Param("userId")Integer userId, @Param("title")String title,
                      @Param("date")String date, @Param("typeId") Integer typeId);

    List<Note> selectNotes(@Param("userId") Integer userId, @Param("title")String title,
                           @Param("date")String date, @Param("typeId")Integer typeId,
                           @Param("index")Integer index, @Param("pageSize")Integer pageSize);

    @Insert("insert into iess.t_xuyuan(userId,title, content, typeId, pubTime, lon, lat)VALUES (#{userId},#{title}, #{content}, #{typeId},now(), #{lon}, #{lat})")
    int addNote(Note note);

    @Delete("delete from iess.t_xuyuan where noteId=#{noteId}")
    int deleteNote(int noteId);

    @Update("update iess.t_xuyuan " +
            "set title=#{title},content=#{content},typeId=#{typeId},lon=#{lon},lat=#{lat} where noteId=#{noteId}")
    int updateNote(Note note);

    @Select("select count(*) from iess.t_xuyuan where typeId=#{typeId}")
    int selectNotesByID(int typeId);

    @Select("SELECT count(1) noteCount,DATE_FORMAT(pubTime,'%Y年%m月') groupName FROM iess.t_xuyuan n " +
            " INNER JOIN iess.t_note_type t ON n.typeId = t.typeId WHERE t.userId = #{userId} " +
            " GROUP BY DATE_FORMAT(pubTime,'%Y年%m月')" +
            " ORDER BY DATE_FORMAT(pubTime,'%Y年%m月') DESC ")
    List<NoteVo> findNoteCountByDate(Integer userId);

    @Select("SELECT count(noteId) noteCount, t.typeId, typeName groupName FROM iess.t_xuyuan n " +
            " RIGHT JOIN iess.t_note_type t ON n.typeId = t.typeId WHERE t.userId = #{userId} " +
            " GROUP BY t.typeId ORDER BY COUNT(noteId) DESC ")
    List<NoteVo> findNoteCountByType(Integer userId);

    @Select("select Lon,Lat from iess.t_xuyuan tn inner join iess.t_note_type tnt on tnt.typeId = tn.typeId where tnt.userId =#{userId} ")
    List<Note> queryNoteList(Integer userId);

    @Select("select count(*) from iess.t_xuyuan")
    int findAllNoteCount();

    List<Note> selectNoteListNotByUserId(String title, String date, Integer typeId, Integer index, Integer pageSize);
}
