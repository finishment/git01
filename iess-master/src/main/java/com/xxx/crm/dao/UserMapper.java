package com.xxx.crm.dao;

import com.xxx.crm.base.BaseMapper;
import com.xxx.crm.entity.StudentGroup;
import com.xxx.crm.entity.User;
import com.xxx.crm.query.UserQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {

    /**
     * 通过用户名查询用用户记录，并返回用户对象
     * @param userName
     * @return
     */
    public User queryUserByName(String userName);

    /**
     * 查询所有的销售人员
     *  关联查询 t_user t_role t_user_role
     * @return
     */
    public List<Map<String, Object>> queryAllSalesman();

    /**
     * 查询所有的客户经理
     * @return
     */
    List<Map<String, Object>> queryAllCustomerManagers();

    @Select("select id,user_name from iess.t_user where phone=#{phone}")
    User getUserByPhone(String phone);

    /**
     * 一下三个 查询所有（班长/组长/组员）信息
     * @return
     */
    List<User> selectMonitorByParams(UserQuery userQuery);

    List<User> selectLeaderByParams(@Param("userQuery") UserQuery userQuery, @Param("userId") Integer userId);

    List<User> selectStudentByParams(@Param("userQuery") UserQuery userQuery,@Param("userId") Integer userId);

    List<User> selectClassByParams(@Param("userQuery") UserQuery userQuery,@Param("userId") Integer userId);

    StudentGroup selectGroupByPrimaryKey(Integer userId);

    int insertStudentGroup(StudentGroup studentGroup);

    int updateStudentGroup(StudentGroup studentGroup);
}
