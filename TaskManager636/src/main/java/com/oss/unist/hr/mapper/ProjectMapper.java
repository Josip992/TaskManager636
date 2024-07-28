package com.oss.unist.hr.mapper;

import com.oss.unist.hr.dto.ProjectDTO;
import com.oss.unist.hr.model.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectMapper {

    public static Project convertToProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setProjectName(projectDTO.getProjectName());
        return project;
    }
    @Autowired
    private ModelMapper modelMapper;

    public ProjectDTO toDto(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    public void updateEntityFromDto(ProjectDTO projectDTO, Project project) {
        modelMapper.map(projectDTO, project);
    }
}

