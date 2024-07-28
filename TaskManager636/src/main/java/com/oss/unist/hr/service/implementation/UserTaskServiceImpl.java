package com.oss.unist.hr.service.implementation;

import com.oss.unist.hr.dto.UserTaskDTO;
import com.oss.unist.hr.model.UserTask;
import com.oss.unist.hr.model.UserTaskId;
import com.oss.unist.hr.repository.UserTaskRepository;
import com.oss.unist.hr.service.UserService;
import com.oss.unist.hr.service.UserTaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserTaskServiceImpl implements UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;

    public UserTaskDTO getUserTask(UUID userId, Long taskId) {
        UserTaskId id = new UserTaskId();
        id.setUserId(userId);
        id.setTaskId(taskId);

        UserTask userTask = userTaskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));

        UserTaskDTO userTaskDTO = new UserTaskDTO();
        userTaskDTO.setUserId(userTask.getUser().getId());
        userTaskDTO.setTaskId(userTask.getTask().getId());
        userTaskDTO.setStatus(userTask.getStatus());
        userTaskDTO.setComment(userTask.getComment());

        return userTaskDTO;
    }

    public List<UserTask> getUserTasksByUserId(UUID userId) {
        return userTaskRepository.findByUserId(userId);
    }

    public void markTaskAsFinished(UUID userId, Long taskId) {
        Optional<UserTask> optionalUserTask = userTaskRepository.findByUserIdAndTaskId(userId, taskId);
        if (optionalUserTask.isPresent()) {
            UserTask userTask = optionalUserTask.get();
            userTask.setFinishedAt(LocalDateTime.now());
            userTaskRepository.save(userTask);
        }
    }
}
