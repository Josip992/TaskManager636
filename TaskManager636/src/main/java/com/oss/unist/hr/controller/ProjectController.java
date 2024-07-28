package com.oss.unist.hr.controller;

import com.oss.unist.hr.dto.ProjectDTO;
import com.oss.unist.hr.model.Project;
import com.oss.unist.hr.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.modeler.BaseAttributeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping("/projects")
    public String showTaskDashboard(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");

        model.addAttribute("username", username);
        return "project/project_dashboard";
    }
    @GetMapping("/show-all-projects")
    public String showAllProjects(Model model, @PageableDefault(size = 10) Pageable pageable, HttpSession session) {
        Page<Project> projectPage = projectService.getAllProjects(pageable);
        model.addAttribute("projects", projectPage.getContent());
        model.addAttribute("page", projectPage);
        return "project/project_display_all";
    }

    @GetMapping("/create-project")
    public String showCreateProjectForm(Model model, HttpSession session) {
        model.addAttribute("projectDTO", new ProjectDTO());
        return "project/project_create";
    }

    @PostMapping("/create-project")
    public String createProject(ProjectDTO projectDTO, Model model, HttpSession session) {
        try {
            projectService.addProject(projectDTO);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "project/project_create";
        }
        return "redirect:/projects";
    }
    @GetMapping("/update-project/{id}")
    public String showUpdateProjectForm(@PathVariable Long id, Model model, HttpSession session) {
        ProjectDTO projectDTO = projectService.getProject(id);
        model.addAttribute("projectDTO", projectDTO);
        return "project/project_update";
    }

    @PostMapping("/update-project/{id}")
    public String updateProject(@PathVariable Long id, ProjectDTO projectDTO, Model model, HttpSession session) {
        try {
            projectService.updateProject(id, projectDTO);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "project/project_update";
        }
        return "redirect:/show-all-projects";
    }

    @GetMapping("/delete-project/{id}")
    public String deleteProject(@PathVariable Long id, Model model, HttpSession session) {
        try {
            projectService.deleteProject(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/show-all-projects";
        }
        return "redirect:/show-all-projects";
    }

    @GetMapping("/search-project")
    public String showProjectSearchForm(Model model) {
        model.addAttribute("projectName", "");
        return "project/project_search";
    }
    @GetMapping("/search-project-execute")
    public String searchProjects(@RequestParam String projectName, Model model) {
        List<Project> projects = projectService.searchProjects(projectName);
        model.addAttribute("projects", projects);
        return "project/project_search_result";
    }
}
