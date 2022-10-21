package com.xxx.crm.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class StudentGroup implements Serializable {
    private Integer userId;
    private Integer groupId;
    private Integer classId;
    private Double annualSal;
    private String job;

}
