package com.oss.unist.hr.mapper;

import com.oss.unist.hr.dto.TaskDTO;
import com.oss.unist.hr.service.CommentService;
import com.oss.unist.hr.service.ProjectService;
import com.oss.unist.hr.model.Comment;
import com.oss.unist.hr.model.Project;
import com.oss.unist.hr.repository.ProjectRepository;
import com.oss.unist.hr.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskMapper {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProjectRepository projectRepository;

    public TaskDTO toDto(Task task) {
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        taskDTO.setProjectId(task.getProject().getId()); // Set projectId
        return taskDTO;
    }

    public Task toEntity(TaskDTO taskDto) {
        Task task = modelMapper.map(taskDto, Task.class);
        Project project = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + taskDto.getProjectId()));
        task.setProject(project);
        return task;
    }
    public void updateEntityFromDto(TaskDTO taskDTO, Task task) {
        modelMapper.map(taskDTO, task);
    }
}