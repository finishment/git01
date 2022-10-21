package com.xxx.crm.service;


import com.xxx.crm.vo.MsmVo;
import org.springframework.stereotype.Service;

@Service
public interface MsmService {

    //发送手机验证码
    boolean send(String phone, String code);
    boolean send(MsmVo msmVo);
}
