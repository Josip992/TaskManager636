package com.oss.unist.hr.service.implementation;

import com.oss.unist.hr.dto.ProjectDTO;
import com.oss.unist.hr.mapper.ProjectMapper;
import com.oss.unist.hr.model.Project;
import com.oss.unist.hr.repository.ProjectRepository;
import com.oss.unist.hr.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public void addProject(ProjectDTO projectDTO) {
        Project existingProject = projectRepository.findByProjectName(projectDTO.getProjectName());
        if (existingProject != null) {
            throw new IllegalArgumentException("A project with this name already exists!");
        }
        Project project = ProjectMapper.convertToProject(projectDTO);
        projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    public Project convertToProject(ProjectDTO projectDTO) {
        Project project = new Project();
        // Set the fields of the Project entity from the ProjectDTO
        // For example:
        project.setId(projectDTO.getId());
        project.setProjectName(projectDTO.getProjectName());
        // Add other fields as necessary
        return project;
    }
    public ProjectDTO getProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + id));
        return projectMapper.toDto(project);
    }

    public void updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + id));
        projectMapper.updateEntityFromDto(projectDTO, project);
        projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid project ID: " + id);
        }
        projectRepository.deleteById(id);
    }
    public List<Project> searchProjects(String projectName) {
        return projectRepository.findByProjectNameContaining(projectName);
    }
}