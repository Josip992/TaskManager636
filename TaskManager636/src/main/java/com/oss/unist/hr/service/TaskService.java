package com.oss.unist.hr.service;

import com.oss.unist.hr.dto.TaskDTO;
import com.oss.unist.hr.model.Project;
import com.oss.unist.hr.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TaskService {
    TaskDTO addTask(TaskDTO taskDTO);
    List<Task> getAllTasks();
    Page<Task> getAllTasks(Pageable pageable);
    TaskDTO getTask(Long id);
    void updateTask(Long id, TaskDTO taskDTO);
    void deleteTask(Long id);
    List<Task> searchTasks(String taskName);
    Map<String, Integer> getTasksPerMonth(UUID userId);
}