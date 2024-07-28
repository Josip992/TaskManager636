package com.oss.unist.hr.dto;


import com.oss.unist.hr.model.Comment;
import com.oss.unist.hr.model.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String taskName;
    private Long projectId;
    private Integer priority;
    private String comment;
    private String status;
}
