package com.xxx.crm.vo;

import lombok.Data;

import java.util.Map;

@Data
public class MsmVo {
//    @ApiModelProperty(value = "phone")
    private String phone;
//    @ApiModelProperty(value = "短信模板code")
    private String templateCode;
//    @ApiModelProperty(value = "短信模板参数")
    private Map<String,Object> param;
}
