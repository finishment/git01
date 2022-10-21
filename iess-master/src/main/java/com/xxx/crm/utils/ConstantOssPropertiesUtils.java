package com.xxx.crm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantOssPropertiesUtils implements InitializingBean {

    @Value("oss-accelerate.aliyuncs.com")
    private String endpoint;

    @Value("LTAI5tG8k4FLmxdnQV6r78up")
    private String accessKeyId;

    @Value("QTyZMoKFMawf8M5zYGupQvHeFVPQJ3")
    private String secret;

    @Value("guli-file003")
    private String bucket;

    public static String EDNPOINT;
    public static String ACCESS_KEY_ID;
    public static String SECRECT;
    public static String BUCKET;

    @Override
    public void afterPropertiesSet() throws Exception {
        EDNPOINT=endpoint;
        ACCESS_KEY_ID=accessKeyId;
        SECRECT=secret;
        BUCKET=bucket;
    }
}
