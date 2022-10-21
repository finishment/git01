package com.xxx.crm.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WelcomeMapper {
    @Select("select announcement from t_announce where id in (SELECT MAX(id) FROM t_announce)")
    String getAnnounce();
}
