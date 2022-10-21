package com.xxx.crm.dao;

import com.xxx.crm.entity.ExperienceNum;
import com.xxx.crm.entity.Leader;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface LeaderMapper {
    @Select("select count(1) as num, c.note_id, n.title\n" +
            "from t_comment c join t_note n on c.note_id=n.noteId\n" +
            "where c.is_valid = 1\n" +
            "group by c.note_id, n.title\n" +
            "order by count(1) desc LIMIT 10")
    List<Leader> selectTitle();

    @Select("select count(1) as num,n.userId from t_note m LEFT JOIN t_note_type n on m.typeId=n.typeId GROUP BY n.userId")
    List<ExperienceNum> selectNum();

    @Select("select true_name from t_user where id=#{userId}")
    List<String> selectUserNameByUserId(int userId);
}
