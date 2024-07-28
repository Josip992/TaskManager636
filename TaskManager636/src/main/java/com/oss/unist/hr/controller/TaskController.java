package com.oss.unist.hr.controller;

import com.oss.unist.hr.dto.ProjectDTO;
import com.oss.unist.hr.dto.TaskDTO;
import com.oss.unist.hr.dto.UserDTO;
import com.oss.unist.hr.model.Project;
import com.oss.unist.hr.model.Task;
import com.oss.unist.hr.service.ProjectService;
import com.oss.unist.hr.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class TaskController {

    private final TaskService taskService;
    @Autowired
    private ProjectService projectService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/tasks")
    public String showTaskDashboard(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        return "task/task_dashboard";
    }

    @GetMapping("/show-all-tasks")
    public String showAllTasks(Model model, @PageableDefault(size = 10) Pageable pageable, HttpSession session) {
        Page<Task> taskPage = taskService.getAllTasks(pageable);
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("page", taskPage);
        return "task/task_display_all";
    }

    @GetMapping("/create-task")
    public String showCreateTaskForm(Model model, HttpSession session) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("taskDTO", new TaskDTO());
        return "task/task_create";
    }

    @PostMapping("/create-task")
    public String createTask(TaskDTO taskDTO, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            logger.info("taskDTO: {}", taskDTO.getTaskName());
            logger.info("taskDTO: {}", taskDTO.getProjectId());
            logger.info("taskDTO: {}", taskDTO.getComment());
            taskService.addTask(taskDTO);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/create-task";
        }
        return "redirect:/show-all-tasks";
    }
    @GetMapping("/update-task/{id}")
    public String showUpdateTaskForm(@PathVariable Long id, Model model, HttpSession session) {
        TaskDTO taskDTO = taskService.getTask(id);
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("taskDTO", taskDTO);
        model.addAttribute("projects", projects);
        return "task/task_update";
    }

    @PostMapping("/update-task/{id}")
    public String updateTask(@PathVariable Long id, TaskDTO taskDTO, Model model, HttpSession session) {
        try {
            taskService.updateTask(id, taskDTO);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "task/task_update";
        }
        return "redirect:/show-all-tasks";
    }

    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable Long id, Model model, HttpSession session) {
        try {
            taskService.deleteTask(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/show-all-tasks";
        }
        return "redirect:/show-all-tasks";
    }
    @GetMapping("/search-task")
    public String showTaskSearchForm(Model model) {
        model.addAttribute("taskName", "");
        return "task/task_search";
    }
    @GetMapping("/search-task-execute")
    public String searchTasks(@RequestParam String taskName, Model model) {
        List<Task> tasks = taskService.searchTasks(taskName);
        model.addAttribute("tasks", tasks);
        return "task/task_search_result";
    }
}
