package com.xxx.crm.controller;

import com.aliyuncs.utils.StringUtils;

import com.xxx.crm.base.BaseController;
import com.xxx.crm.entity.User;
import com.xxx.crm.handler.YyghException;
import com.xxx.crm.model.UserModel;
import com.xxx.crm.result.Result;
import com.xxx.crm.result.ResultCodeEnum;
import com.xxx.crm.service.MsmService;
import com.xxx.crm.service.UserService;
import com.xxx.crm.utils.RandomUtil;
import com.xxx.crm.utils.UserIDBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
@RestController
@RequestMapping("/api/msm")
public class MsmApiController extends BaseController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送手机验证码
    @GetMapping("send/{phone}")
    public Result sendCode(@PathVariable String phone) {
        //从redis获取验证码，如果获取获取到，返回ok
        // key 手机号  value 验证码
        System.out.println(phone);
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        //校验校验验证码

        //如果从redis获取不到，
        // 生成验证码，
        code = RandomUtil.getSixBitRandom();
        while (code.charAt(0)=='0'){
            code = RandomUtil.getSixBitRandom();
        }
        //调用service方法，通过整合短信服务进行发送
        boolean isSend = msmService.send(phone, code);
        //生成验证码放到redis里面，设置有效时间
        if (isSend) {
            redisTemplate.opsForValue().set(phone, code, 600, TimeUnit.DAYS);
            Result result = new Result();
            result.setCode(200);
            result.setMsg("请在手机查看短信验证码");
            return result;
        } else {
            Result result = new Result();
            result.setCode(300);
            result.setMsg("发送短信失败，请重试");
            return result;
        }
//        return Result.ok();
    }

//    ////校验校验验证码
    @GetMapping("send/{phone}/{code}")
    public Result checkCode(HttpServletRequest request, @PathVariable String phone, @PathVariable String code) {
        //手机号:19932223369   验证码:664771
        //手机:15837331913   验证码:892979
        //校验验证码
        String mobleCode = redisTemplate.opsForValue().get(phone);
        System.out.println("手机:"+phone+"验证码:"+code);
        if(!code.equals(mobleCode)) {
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }
        User user = userService.getUserByPhone(phone);
        UserModel userModel =new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        Result result = new Result();
        result.setCode(200);
        result.setMsg("登录成功");
        result.setData(userModel);
        return result;
    }
}
