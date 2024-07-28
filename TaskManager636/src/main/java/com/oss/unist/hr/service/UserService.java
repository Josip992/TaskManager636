package com.oss.unist.hr.service;


import com.oss.unist.hr.dto.UserDTO;
import com.oss.unist.hr.model.Task;
import com.oss.unist.hr.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    boolean areInputsInvalid(UserDTO request);
    User registerUser(UserDTO request);
    User authenticate(String email, String enteredPassword);
    String getUsernameByEmail(String email);
    User getUserById(UUID userId);
    int countUsers();
    void addTaskToUser(UUID userId, Long taskId);
    List<Task> getUserTasks(UUID userId);
    void updateUserTask(UUID userId, Long taskId, String status, String comment);
    void removeTaskFromUser(UUID userId, Long taskId);
}




