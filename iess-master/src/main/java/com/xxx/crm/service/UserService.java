package com.xxx.crm.service;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.crm.base.BaseService;
import com.xxx.crm.base.ResultInfo;
import com.xxx.crm.dao.UserMapper;
import com.xxx.crm.dao.UserRoleMapper;
import com.xxx.crm.entity.StudentGroup;
import com.xxx.crm.entity.UserRole;
import com.xxx.crm.model.UserModel;
import com.xxx.crm.query.UserQuery;
import com.xxx.crm.utils.AssertUtil;
import com.xxx.crm.utils.Md5Util;
import com.xxx.crm.entity.User;
import com.xxx.crm.utils.PhoneUtil;
import com.xxx.crm.utils.UserIDBase64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.*;

@Service
@Slf4j
public class UserService extends BaseService<User, Integer> {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Autowired
    FileService fileService;

    public  User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    /**
     * 1.参数判断，判断用户姓名、用户密码非空
     *   如果参数为空，抛出异常（异常被控制层捕获并处理）
     * 2.调用数据访问层，通过用户名查询用户记录，返回用户对象
     * 3.判断用户对象是否为空
     *   如果对象为空，抛出异常（异常被控制层捕获并处理）
     * 4.判断密码是否正确，比较客户端传递的用户密码与数据库中查询的用户对象的密码
     *   如果密码不相等，抛出异常（异常被控制层捕获并处理）
     * 5.如果密码正确，登录成功
     * @param userName
     * @param userPwd
     */
    public UserModel userLogin(String userName, String userPwd) {
        log.info("userMapper[{}]" + userMapper);
         // 1.参数判断，判断用户姓名、用户密码非空
        checkLoginParams(userName, userPwd);
         // 2.调用数据访问层，通过用户名查询用户记录，返回用户对象
        User user = userMapper.queryUserByName(userName);
        // 3.判断用户对象是否为空，为空则抛出异常
        AssertUtil.isTrue(user == null, "用户名不存在！");
        // 4.判断密码是否正确，比较客户端传递的用户密码与数据库中查询的用户对象的密码
        checkUserPwd(userPwd, user.getUserPwd());
        // 5.构建用户对象
        return buildUserInfo(user);
    }

    /**
     * 修改密码
        1、接收四个参数（用户ID、原始密码、初始密码、确认密码）
        2、通过用户ID查询用户记录，返回对象
        3、参数校验
            待更新用户记录是否存在（用户对象是否为空）
            原始密码是否为空
            原始密码是否正确
            原始密码是否正确（是否与数据库中的一致）
            判断新密码是否为空
            判断新密码是否与原始密码一致（不允许与原始密码一致）富士達官方撒
            判断确认密码是否为空
            判断确认密码是否与新密码一致
        4、设置用户的新密码
            需要将新密码通过指定算法进行加密（MD5）
        5、调用Dao层执行更新操作，判断受影响的行数
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updatePassword(Integer userId, String oldPwd, String newPwd, String repeatPwd) {
        // 1.通过用户ID查询用户记录，返回对象
        User user = userMapper.selectByPrimaryKey(userId);
        // 2.判断用户记录是否存在
        AssertUtil.isTrue(user == null, "待更新记录不存在！");
        // 3.参数校验
        checkPasswordParams(user, oldPwd, newPwd, repeatPwd);
        // 4.设置用户的新密码(需要进行md5加密)
        user.setUserPwd(Md5Util.encode(newPwd));
        // 5.创建新用户，只加入 id,userPassword和 updateDate 属性
        User newUser = new User();
        newUser.setId(user.getId()).setUserPwd(user.getUserPwd()).setUpdateDate(new Date());
        // 6.调用Dao层执行更新，判断受影响行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(newUser) != 1, "修改密码失败！");
    }

    /**
     * 修改密码的参数校验
     *      原始密码是否为空
     *      判断新密码是否为空
     *      判断确认密码是否为空
     *      判断确认密码是否与新密码一致
     *      判断原始密码是否正确（是否与数据库中的一致）
     *      判断新密码是否与原始密码一致（不允许与原始密码一致）
     * @param user
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     */
    private void checkPasswordParams(User user, String oldPwd, String newPwd, String repeatPwd) {
        // 原始密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd), "原始密码不能为空！");
        // 判断新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPwd), "新密码不能为空！");
        //判断确认密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd), "确认密码不能为空！");
        // 判断确认密码是否与新密码一致
        AssertUtil.isTrue(!repeatPwd.equals(newPwd), "新密码与确认密码不一致！");
        // 判断原始密码是否正确（是否与数据库中的密文密码一致）
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)), "原始密码错误！"  );
        // 判断新密码是否与原始密码一致（不允许与原始密码一致）
        AssertUtil.isTrue(oldPwd.equals(newPwd), "新密码不能与旧密码一致！");
    }

    /**
     * 构建需要返回给客户端的用户对象
     * @param user
     */
    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        // 对userId使用base64加密
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()))
                .setUserName(user.getUserName())
                .setTrueName(user.getTrueName());
        return userModel;
    }

    /**
     * 验证密码
     *  先j将客户端传递的密码进行md5加密，再与数据库中查询到的密码比较
     * @param userPwd 前端传递过来的密码
     * @param userPwd 数据库中查询到的密码
     */
    private void checkUserPwd(String userPwd, String pwd) {
        // 1.将客户端传递的密码进行加密
        userPwd = Md5Util.encode(userPwd);
        // 2.判断密码是否相等
        AssertUtil.isTrue(!pwd.equals(userPwd), "用户密码错误！");
    }

    /**
     * 如果参数为空，抛出异常（异常被控制层捕获并处理）
     * @param userName 前端传递过来的用户名
     * @param userPwd 前端传递过来的密码
     */
    private void checkLoginParams(String userName, String userPwd) {
        /* 验证用户姓名 */
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空！");
        /* 验证用户密码 */
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "用户密码不能为空！");
    }

    /**
     * 查询所有销售人员
     * @return
     */
    public List<Map<String, Object>> queryAllSalesman() {
        return userMapper.queryAllSalesman();
    }

    /**
     * 添加用户：
     *  1.参数校验
     *      用户名 userName 非空唯一
     *      邮箱email     非空
     *      手机号        格式，非空，
     *  2.设置参数的默认值
     *      isValid 1
     *      createDate 当前系统时间
     *      updateDate 当前系统事件
     *  3.执行添加操作，判断受影响行数
     *
     * @param user 用户实体
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addUser(User user) {
        /* 1.参数校验 */
        checkUserParams(user.getUserName(), user.getEmail(), user.getPhone(),null);

        /* 2.设置默认参数 */
        user.setIsValid(1).setCreateDate(new Date()).setUpdateDate(new Date());

        //设置密码
        user.setUserPwd(Md5Util.encode("123456"));

        /* 3.执行添加操作，判断受影响行数 */
        AssertUtil.isTrue(userMapper.insertSelective(user) != 1, "用户记录添加失败!");

        /* 用户角色关联：给用户关联角色 */
        /**
         * 用户ID
         *   userId
         * 角色ID
         *   roleIds
         */
        relationUserRole(user.getId(), user.getRoleIds());
    }

    /**
     * 用户角色关联：给用户关联角色
     * 1.添加操作
     *      原始角色不存在
     *          1.不添加新的角色  不操作用户角色中间表
     *          2.添加新的角色    给指定用户绑定新的角色记录
     * 2.更新操作
     *      原始角色不存在
     *          1.不添加新的角色  不操作用户角色中间表
     *          2.添加新的角色    给指定用户绑定新d的角色记录
     *      原始角色存在
     *          1.添加新的角色记录       已有的角色不添加，新的角色则添加
     *          2.清空所有的角色记录     删除用户绑定的角色信息
     *          3.移除部分已有的角色     删除不存在的角色记录，存在的依然保留
     *          4.移除部分已有的并添加了新的角色   删除不存在的角色记录，保留存在并添加新的
     *
     *  任何进行角色分配呢 ???
     *      判断用户对应的角色记录存在，将原有的角色记录删除，再添加用户已选择的角色记录即可
     *
     * 3.删除操作
     *      删除指定用户绑定的用户角色表中的所有记录
     *
     * @param userId 用户ID
     * @param roleIds 角色ID,一个或多个 格式："1,2,3"
     */
    private void relationUserRole(Integer userId, String roleIds) {

        // 1.通过用户ID查询角色记录
        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        // 2.判断角色记录是否存在
        if (count > 0) {
            // 3.如果角色记录存在，则删除该用户已用的所有角色记录删除
            AssertUtil.isTrue( !count.equals(userRoleMapper.deleteUserRoleByUserId(userId)) , "用户角色分配失败!");
        }

        // 4.判断角色ID是否有值，如果有值则添加该用户对应的角色记录
        if (StringUtils.isNotBlank(roleIds)) {
            // 将用户角色数据设置到集合中，执行批量添加
            List<UserRole> userRoleList = new ArrayList<>(20);
            // 将角色ID字符串转成数组(字符串分隔)
            String[] roleIdsArray = roleIds.split(",");
            // 遍历数组，得到对应用户角色对象，并设置到集合中
            for (String roleId : roleIdsArray) {
                // 设置UserRole对象的值
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                // 将每一个对象保存到List集合中
                userRoleList.add(userRole);
            }
            // 批量添加用户角色记录
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoleList) != userRoleList.size(), "用户角色分配失败!");
        }
    }

    /**
     * 更新用户记录
     * 1.参数校验
     *      判断用户ID是否为空且记录是否存在
     *      用户名 userName 非空唯一
     *      邮箱 email 非空
     *      手机号 phone 非空，格式正确
     * 2.设置参数的默认值
     *      updateDate 当前系统时间
     * 3.执行更新，判断影响行数
     * @param user 用户实体
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateUser(User user) {
        // 判断用户ID是否为空
        AssertUtil.isTrue(user.getId() == null , "数据异常,请重试!");
        // 参数校验
        checkUserParams(user.getUserName(), user.getEmail(), user.getPhone(), user.getId());
        // 根据ID查询的用户记录是否为空
        AssertUtil.isTrue(userMapper.selectByPrimaryKey(user.getId()) == null , "数据异常,请重试!");
        // 设置参数的默认值
        user.setUpdateDate(new Date());
        // 执行更新，判断影响行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) != 1, "用户记录更新失败!");

        /* 用户角色关联：给用户关联角色 */
        /**
         * 用户ID
         *   userId
         * 角色ID
         *   roleIds
         */
        relationUserRole(user.getId(), user.getRoleIds());
    }



    /**
     * 用户参数校验
     * @param userName
     * @param email
     * @param phone
     */
    private void checkUserParams(String userName, String email, String phone, Integer userId) {
        //用户名 userName 非空唯一
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空!");
        //邮箱email     非空
        AssertUtil.isTrue(StringUtils.isBlank(email), "用户邮箱不能为空!");
        //手机号        格式，非空，
        AssertUtil.isTrue( StringUtils.isBlank(phone), "手机号不能为空!");
        AssertUtil.isTrue( !PhoneUtil.isMobile(phone), "手机号格式错误!");
        // 通过用户名查询用户对象
        User temp = userMapper.queryUserByName(userName);
        /**
         * 执行添加操作时，根据用户名查询用户，
         *      记录存在，用户名不可用
         *      记录不存在，用户名可用
         * 执行更新操作时，根据用户名查询用户
         *      记录不存在，用户名可用
         *      记录存在，判断当前用户的ID是否相同（及判断是否是同一人）
         *          ID相同，用户名可用
         *          ID不同，用户名不可用
         */
        AssertUtil.isTrue(temp != null && !(temp.getId().equals(userId)), "用户名已存在,换一个重试!");
    }


    /**
     * 批量逻辑删除用户
     * @param ids 主键ID数组
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteUserByIds(Integer[] ids) {
        // 判断id数组是否为空
        AssertUtil.isTrue(ids == null || ids.length < 1, "待删除记录不存在!");
        AssertUtil.isTrue(userMapper.deleteBatch(ids) != ids.length, "数据异常,请重试!");

        // 遍历用户ID的数组
        for (Integer userId : ids) {
            // 1.通过用户ID查询对应的用户的角色记录
            Integer count = userRoleMapper.countUserRoleByUserId(userId);
            // 2.判断用户是否具有角色
            if (count > 0) {
                // 3.通过用户ID删除用户角色记录
                AssertUtil.isTrue( !count.equals(userRoleMapper.deleteUserRoleByUserId(userId)), "删除用户记录失败!");
            }
        }
    }

    /**
     * 查询所有的客户经理
     * @return
     */
    public List<Map<String, Object>> queryAllCustomerManagers() {
        return userMapper.queryAllCustomerManagers();
    }

    /**
     * 注册
     *
     * */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void userRegister(User user) {
        //1.参数判断，判断用户姓名、用户密码非空
        checkLoginParams(user.getUserName(),user.getUserPwd());

        //2.调用数据访问层，通过用户名查询用户记录，返回用户对象
        User user2=userMapper.queryUserByName(user.getUserName());

        //3.判断用户对象是否为空
        AssertUtil.isTrue(user2!=null,"用户姓名已存在,请重新输入!");

        String userPwd = user.getUserPwd();
        user.setUserPwd(Md5Util.encode(userPwd));

        /* 4.执行添加操作，判断受影响行数 */
        AssertUtil.isTrue(userMapper.insertSelective(user) != 1, "用户记录添加失败!");

    }



    public ResultInfo oss(MultipartFile img, HttpServletRequest request, HttpServletResponse response) {
        ResultInfo resultInfo = new ResultInfo();

        // 3. 从session作用域中获取用户对象（获取用户对象中默认的头像）
        User user = (User) request.getSession().getAttribute("loginUser");
        // 设置修改的昵称和头像
        // 4. 实现上传文件
        try {
            //上传路径保存设置
            String url = fileService.upload(img);
            user.setHead(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 6. 调用Dao层的更新方法，返回受影响的行数
        int row = userMapper.updateByPrimaryKeySelective(user);
        // 7. 判断受影响的行数
        if (row > 0) {
            resultInfo.setCode(1);
        } else {
            resultInfo.setCode(0);
            resultInfo.setMsg("更新失败！");
        }
        resultInfo.setResult(user);
        return resultInfo;
    }

    /**
     * 查询所有（班长/组长/组员）信息
     * @return
     */
    public Map<String, Object> selectMonitorByParams(UserQuery userQuery){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        PageInfo<User> pageInfo=new PageInfo<>(userMapper.selectMonitorByParams(userQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    public Map<String, Object> selectLeaderByParams(UserQuery userQuery, Integer userId){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        PageInfo<User> pageInfo=new PageInfo<>(userMapper.selectLeaderByParams(userQuery,userId));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    public Map<String, Object> selectStudentByParams(UserQuery userQuery, Integer userId){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        PageInfo<User> pageInfo=new PageInfo<>(userMapper.selectStudentByParams(userQuery,userId));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    public Map<String, Object> selectClassByParams(UserQuery userQuery, Integer userId){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        PageInfo<User> pageInfo=new PageInfo<>(userMapper.selectClassByParams(userQuery,userId));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    public StudentGroup selectGroupByPrimaryKey(Integer id) {
        return userMapper.selectGroupByPrimaryKey(id);
    }

    public void addOrUpdateGroup(StudentGroup studentGroup) {
        if (userMapper.selectGroupByPrimaryKey(studentGroup.getUserId())!=null){
            AssertUtil.isTrue(userMapper.updateStudentGroup(studentGroup)<1,"更新失败！");
        }else {
            AssertUtil.isTrue(userMapper.insertStudentGroup(studentGroup) < 1, "添加失败！");
        }
    }
}
