package com.oss.unist.hr.service.implementation;

import com.oss.unist.hr.dto.CommentDTO;
import com.oss.unist.hr.model.Comment;
import com.oss.unist.hr.repository.CommentRepository;
import com.oss.unist.hr.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setCommentText(commentDTO.getCommentText());
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

}

