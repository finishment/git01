package com.xxx.crm.dao;

import com.xxx.crm.entity.NoteType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NoteTypeMapper {

    @Select("select * from iess.t_note_type where userId=#{userId}")
    List<NoteType> selectNoteType(int userId);

    @Delete("delete from iess.t_note_type where typeId=#{typeId}")
    int deleteNoteType(int typeId);

    @Insert("insert into iess.t_note_type(typeName, userId) values (#{typeName},#{userId})")
    int addNoteType(NoteType noteType);

    @Update("update iess.t_note_type " +
            "set typeName=#{typeName} where typeId=#{typeId}")
    int updateNoteType(NoteType noteType);

    @Select("select typeId from iess.t_note_type where userId=#{userId} and typeName=#{typeName}")
    int selectNoteTypeID(NoteType noteType);
}
