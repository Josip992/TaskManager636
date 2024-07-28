package com.oss.unist.hr.service;

import com.oss.unist.hr.dto.CommentDTO;
import com.oss.unist.hr.model.Comment;

import java.util.List;


public interface CommentService {
    void addComment(CommentDTO commentDTO);
    List<Comment> getAllComments();
}