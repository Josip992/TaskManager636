package com.oss.unist.hr.mapper;


import com.oss.unist.hr.dto.CommentDTO;
import com.oss.unist.hr.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static List<Comment> convertToComments(List<String> commentTexts) {
        List<Comment> comments = new ArrayList<>();
        for (String commentText : commentTexts) {
            Comment comment = new Comment();
            // Set the fields of the Comment entity from the commentText
            comment.setCommentText(commentText);
            comments.add(comment);
        }
        return comments;
    }

}
