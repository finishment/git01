package com.xxx.crm.service;


import com.xxx.crm.dao.CommentMapper;
import com.xxx.crm.entity.Comment;
import com.xxx.crm.query.CommentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    public void save(Comment comment) {
        commentMapper.saveComment(comment);
    }

    public void remove(int parent_id) {
        commentMapper.deleteByParentId(parent_id);
    }

    public void removeById(Integer id) {
        commentMapper.deleteById(id);
    }

    public List<CommentQuery> listByNoteId(String noteId) {
        List<CommentQuery> list=commentMapper.selectCommentsByNoteId(noteId);
        return list;
    }
    public List<CommentQuery> listEqCommId(String commentId) {
        List<CommentQuery> list=commentMapper.selectCommentsByCommentId(commentId);
        return list;
    }

    public List<CommentQuery> commentList(Integer userId,String content) {
        List<CommentQuery> list=commentMapper.commentList(userId,content);
        return list;
    }
}
