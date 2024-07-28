package com.oss.unist.hr.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String commentText;
    private TaskDTO task;
}
