package com.xxx.crm.query;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentQuery {
    private String title;
    private Integer id;
    private Integer commId;
    private String parentName;
    private Integer noteId;
    private Integer userId;
    private String userName;
    private String content;
    private Date createTime;
    private Integer isValid;

    private List<CommentQuery> commentList;
}
