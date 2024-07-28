package com.oss.unist.hr.controller;

import jakarta.servlet.http.HttpSession;
import com.oss.unist.hr.dto.UserDTO;
import com.oss.unist.hr.model.User;
import com.oss.unist.hr.mapper.UserMapper;
import com.oss.unist.hr.model.enums.Role;
import com.oss.unist.hr.service.AdminService;
import com.oss.unist.hr.service.ProjectService;
import com.oss.unist.hr.service.TaskService;
import com.oss.unist.hr.service.UserService;
import lombok.AllArgsConstructor;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final AdminService adminService;
    private final UserService userService;


    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAllUsers(Model model, HttpSession session) {
        Role loggedInUserRole = (Role) session.getAttribute("role");
        String roleString = loggedInUserRole.toString();
        model.addAttribute("users", adminService.filterUsersByRole(roleString));
        return "admin/all_users";
    }
    @GetMapping("/users/update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUpdateUserPage(@PathVariable UUID userId, Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        return "admin/admin_update_user";
    }

    @PostMapping("/users/update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUser(@ModelAttribute("user") UserDTO userDto, @PathVariable UUID userId) {
        User user = UserMapper.mapDtoToEntity(userDto);
        adminService.updateUser(userId, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable UUID userId) {
        adminService.deleteUser(userId);
        return "redirect:/admin/users";
    }
    //dodaj da moze prominit nekog usera u admina
}