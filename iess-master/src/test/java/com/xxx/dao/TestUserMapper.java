package com.xxx.dao;

import com.xxx.crm.App;
import com.xxx.crm.dao.CommentMapper;
import com.xxx.crm.dao.UserMapper;
import com.xxx.crm.query.CommentQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: LuXiangHai
 * @QQ: 3243016771
 * @Date: 2021/5/14 10:21
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestUserMapper {

    @Autowired
    CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;
    @Test
    public void test(){
//        List<Comment> list = commentMapper.selectCommentsByNoteId("24");
        List<CommentQuery> list = commentMapper.selectCommentsByCommentId("1");
        System.out.println(list);
    }
    /**
     * 测试 queryUserByName 方法
     */
    @Test
    public void testQueryUserByName() {
        System.out.println(userMapper.queryUserByName("admin"));
    }

    @Test
    public void testQueryAllSalesman() {
        List<Map<String, Object>> list = userMapper.queryAllSalesman();
        list.forEach(System.out::println);
    }

}
