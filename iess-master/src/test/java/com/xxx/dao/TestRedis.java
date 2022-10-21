package com.xxx.dao;

import com.xxx.crm.App;
import com.xxx.crm.dao.CollectionMapper;
import com.xxx.crm.entity.Collections;
import com.xxx.crm.entity.Role;
import com.xxx.crm.query.CollectionQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @Author: LuXiangHai
 * @QQ: 3243016771
 * @Date: 2021/5/21 9:50
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CollectionMapper collectionMapper;

    @Test
    public void test01() {
        CollectionQuery collectionQuery =new CollectionQuery();
        collectionQuery.setUserId(10);
//        collectionQuery.setNoteId(24);
        List<CollectionQuery> collections = collectionMapper.selectByUserIdOrTitle(10,"");
        collections.forEach(System.out::println);
//        // 修改key的序列化方案，修改为String类型的序列化方案
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        // 修改 hashKey 的序列化方案
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//
//        Role role = new Role();
//        role.setIsValid(1).setRoleName("客户经理").setRoleRemark("hhh").setCreateDate(new Date()).setUpdateDate(new Date());
//
//        redisTemplate.opsForValue().set("role", role);
//        role = (Role) redisTemplate.opsForValue().get("role");
//        System.out.println(role);
    }

}
