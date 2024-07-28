package com.oss.unist.hr.controller;

import com.oss.unist.hr.dto.UserDTO;
import com.oss.unist.hr.dto.UserTaskDTO;
import com.oss.unist.hr.model.Task;
import com.oss.unist.hr.service.*;
import com.oss.unist.hr.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class WorkController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private final TaskService taskService;
    private UserTaskService userTaskService;

    @Autowired
    public void setUserTaskService(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }
    @Autowired
    private UserService userService;
    public WorkController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/work")
    public String work(Model model, HttpSession session){
        String username = (String) session.getAttribute("username");

        model.addAttribute("username", username);
        return "work/work_dashboard";
    }
    @GetMapping("/select-work")
    public String selectWork(Model model, HttpSession session) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "work/work_select";
    }

    @PostMapping("/select-task")
    public String selectTask(@RequestParam Long taskId, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        UUID userId = (UUID) session.getAttribute("userId");
        try {
            userService.addTaskToUser(userId, taskId);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/select-work";
        }
        return "redirect:/tasks-work";
    }
    @GetMapping("/tasks-work")
    public String showUserTasks(HttpSession session, Model model) {
        UUID userId = (UUID) session.getAttribute("userId");

        List<Task> tasks = userService.getUserTasks(userId);

        model.addAttribute("tasks", tasks);

        return "work/work_tasks";
    }

    @GetMapping("/delete-user-task/{id}")
    public String deleteUserTask(@PathVariable Long id, HttpSession session, Model model) {
        try {
            UUID userId = (UUID) session.getAttribute("userId");
            userService.removeTaskFromUser(userId, id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/tasks-work";
        }
        return "redirect:/tasks-work";
    }

    @PostMapping("/update-user-task/{taskId}")
    public String updateUserTask(Model model, @PathVariable Long taskId, @RequestParam String status, @RequestParam String comment, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        try {
            userService.updateUserTask(userId, taskId, status, comment);
            if ("Finished".equals(status)) {
                userTaskService.markTaskAsFinished(userId, taskId);
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "work/work_tasks_update";
        }
        return "redirect:/tasks-work";
    }
    @GetMapping("/update-user-task/{taskId}")
    public String showUpdateTaskForm(@PathVariable Long taskId, Model model, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        UserTaskDTO userTaskDTO = userTaskService.getUserTask(userId, taskId);
        model.addAttribute("userTaskDTO", userTaskDTO);
        return "work/work_tasks_update";
    }

    @GetMapping("/finished-tasks-graph")
    public String showTasksPerMonth(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        UUID userId = (UUID) session.getAttribute("userId");
        Map<String, Integer> taskData = taskService.getTasksPerMonth(userId);
        model.addAttribute("taskData", taskData);
        return "work/finished_task_graph";
    }

}
