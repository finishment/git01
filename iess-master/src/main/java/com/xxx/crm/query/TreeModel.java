package com.xxx.crm.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
public class TreeModel  implements Serializable {
   // 节点ID
   private Integer id;
   // 父节点ID
   @JsonProperty("pId") // 踩坑
   private Integer pId;
   // 节点名
   private String name;
   // 复选框是否被选中;ture 则勾选，false则不勾选
   private Boolean checked = false;

}
