package com.xxx.crm.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVO {
    private Integer id;
    private String content;
    private List<CommentVO> subComment;
}
