package com.oss.unist.hr.service;
import com.oss.unist.hr.dto.UserTaskDTO;
import com.oss.unist.hr.model.UserTask;

import java.util.List;
import java.util.UUID;

public interface UserTaskService {
    UserTaskDTO getUserTask(UUID userId, Long taskId);
    List<UserTask> getUserTasksByUserId(UUID userId);
    void markTaskAsFinished(UUID userId, Long taskId);
}
