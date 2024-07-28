package com.oss.unist.hr.dto;

import com.oss.unist.hr.model.Task;
import com.oss.unist.hr.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskDTO {
    private UUID userId;
    private Long taskId;
    private String status;
    private String comment;
    private LocalDateTime finishedAt;
}
