package com.xxx.dao;

import com.xxx.crm.App;
import com.xxx.crm.dao.RoleMapper;
import com.xxx.crm.dao.UserRoleMapper;
import com.xxx.crm.entity.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: LuXiangHai
 * @QQ: 3243016771
 * @Date: 2021/5/16 20:45
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestUserRoleMapper {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Test
    public void test01() {
        UserRole userRole1 = new UserRole(null,10,20,new Date(),new Date());
        UserRole userRole2 = new UserRole(null,11,21,new Date(),new Date());
        List<UserRole> userRoleList = new ArrayList<>(5);
        userRoleList.add(userRole1);
        userRoleList.add(userRole2);
        Integer integer = userRoleMapper.insertBatch(userRoleList);
        System.out.println(integer);
    }

}
