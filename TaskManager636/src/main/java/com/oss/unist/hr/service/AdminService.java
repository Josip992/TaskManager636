package com.oss.unist.hr.service;

import com.oss.unist.hr.dto.UserDTO;
import com.oss.unist.hr.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<User> getAllUsers(String currentUsername);
    User updateUser(UUID userId, User updatedUser);
    void deleteUser(UUID userId);
    List<UserDTO> filterUsersByRole(String role);
}
