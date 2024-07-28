package com.oss.unist.hr.service.implementation;

import com.oss.unist.hr.dto.TaskDTO;
import com.oss.unist.hr.model.*;
import com.oss.unist.hr.repository.CommentRepository;
import com.oss.unist.hr.repository.ProjectRepository;
import com.oss.unist.hr.repository.TaskRepository;
import com.oss.unist.hr.mapper.TaskMapper;
import com.oss.unist.hr.repository.UserTaskRepository;
import com.oss.unist.hr.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserTaskRepository userTaskRepository;
    @Autowired
    private TaskMapper taskMapper;
    public TaskDTO addTask(TaskDTO taskDTO) {
        Optional<Task> existingTask = taskRepository.findByTaskName(taskDTO.getTaskName());

        if (existingTask.isPresent()) {
            throw new IllegalArgumentException("A task with that name already exists");
        }
        System.out.println("New Task Name: " + taskDTO.getTaskName());

        // Create a new task entity from the DTO
        Task task = taskMapper.toEntity(taskDTO);

        // Debugging step: Print the task name again to verify it's correct
        System.out.println("Task Name (Entity): " + task.getTaskName());
        task = taskRepository.save(task);

        Comment comment = new Comment();
        comment.setCommentText(taskDTO.getComment());
        comment.setTask(task);
        commentRepository.save(comment);

        return taskMapper.toDto(task);
    }
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public TaskDTO getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
        return taskMapper.toDto(task);
    }

    public void updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
        Project newProject = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + taskDTO.getProjectId()));

        task.setProject(newProject);
        taskMapper.updateEntityFromDto(taskDTO, task);
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid task ID: " + id);
        }
        taskRepository.deleteById(id);
    }
    public List<Task> searchTasks(String taskName) {
        return taskRepository.findByTaskNameContaining(taskName);
    }
    public Map<String, Integer> getTasksPerMonth(UUID userId) {
        List<TaskResult> results = taskRepository.findTasksPerMonth(userId);

        return results.stream()
                .collect(Collectors.toMap(
                        TaskResult::getMonth,
                        TaskResult::getCount
                ));
    }
}