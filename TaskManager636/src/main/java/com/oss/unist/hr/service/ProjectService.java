package com.oss.unist.hr.service;

import com.oss.unist.hr.dto.ProjectDTO;
import com.oss.unist.hr.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProjectService {
    void addProject(ProjectDTO projectDTO);
    List<Project> getAllProjects();
    Page<Project> getAllProjects(Pageable pageable);
    ProjectDTO getProject(Long id);
    void updateProject(Long id, ProjectDTO projectDTO);
    void deleteProject(Long id);
    List<Project> searchProjects(String projectName);
}