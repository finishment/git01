package com.xxx.crm.controller;

import com.xxx.crm.entity.Comment;
import com.xxx.crm.base.ResultBean;
import com.xxx.crm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/add")
    public ResultBean<String> add(@RequestBody Comment comment){
        // 判断内容是否全
        if(StringUtils.isEmpty(comment.getContent())){
            return new ResultBean<>(400,"添加失败",0,"评论内容不能为空！");
        }
        comment.setCreateTime(new Date());
        commentService.save(comment);

        return new ResultBean<>(200,"添加成功",0,null);
    }

    @PostMapping("/remove/{id}")
    public ResultBean<String> remove(@PathVariable Integer id){
        // 先删除子回复
        int parent_id=id;
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("parent_id",id);
        commentService.remove(parent_id);
        // 删除父级回复
        commentService.removeById(id);

        return new ResultBean<>(200,"删除成功",0,null);
    }

}
