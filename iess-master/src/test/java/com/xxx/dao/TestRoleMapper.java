package com.xxx.dao;

import com.xxx.crm.App;
import com.xxx.crm.dao.CommentMapper;
import com.xxx.crm.dao.RoleMapper;
import com.xxx.crm.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: LuXiangHai
 * @QQ: 3243016771
 * @Date: 2021/5/16 20:45
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestRoleMapper {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private CommentMapper commentMapper;
    @Test
    public void test01() {
//        List<Map<String, Object>> list = roleMapper.queryAllRoles(10);
//        list.forEach(System.out::println);
//        List<CommentQuery> list = commentMapper.selectCommentsByCommentId("1");
//        list.forEach(System.out::println);
        Comment comment =new Comment();
        comment.setCommId(16);
        comment.setNoteId(24);
        comment.setUserId(43);
        comment.setContent("66666");
        comment.setCreateTime(new Date());
        commentMapper.saveComment(comment);
    }


}
