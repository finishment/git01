package com.xxx.crm.entity;


import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *  评论
 */
@Data
public class Comment {

    private Integer id;
    private Integer commId;
    private Integer parentId;
    private Integer noteId;
    private Integer userId;
    private String content;
    private Date createTime;
    private Integer isValid;

    private List<Comment> commentList;

}
