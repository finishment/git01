package com.xxx.crm.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("default")
    private String regionId;

    @Value("LTAI5tG8k4FLmxdnQV6r78up")
    private String accessKeyId;

    @Value("QTyZMoKFMawf8M5zYGupQvHeFVPQJ3")
    private String secret;

    public static String REGION_Id;
    public static String ACCESS_KEY_ID;
    public static String SECRECT;

    @Override
    public void afterPropertiesSet() throws Exception {
        REGION_Id=regionId;
        ACCESS_KEY_ID=accessKeyId;
        SECRECT=secret;
    }
}
