package com.oss.unist.hr.model;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_tasks")
@IdClass(UserTaskId.class)
public class UserTask {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "task_id")
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;

    @Column
    private String status;
    @Column
    private String comment;
    @Column
    private LocalDateTime finishedAt;
    public UserTask(User user, Task task) {
        this.user = user;
        this.task = task;
        this.userId = user.getId();
        this.taskId = task.getId();
    }
}
