package com.oss.unist.hr.service.implementation;


import com.oss.unist.hr.dto.TaskDTO;
import com.oss.unist.hr.dto.UserDTO;
import com.oss.unist.hr.model.*;
import com.oss.unist.hr.repository.*;
import com.oss.unist.hr.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import com.oss.unist.hr.dto.UserDTO;
import com.oss.unist.hr.mapper.UserMapper;
import com.oss.unist.hr.model.User;
import com.oss.unist.hr.repository.UserRepository;
import com.oss.unist.hr.service.UserService;
import com.oss.unist.hr.util.PasswordUtils;
import com.oss.unist.hr.util.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

 @Service
 @AllArgsConstructor
 public class UserServiceImpl implements UserService {

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private TaskRepository taskRepository;

     @Autowired
     private CommentRepository commentRepository;

     @Autowired
     private UserTaskRepository userTaskRepository;

     public void addTaskToUser(UUID userId, Long taskId) {
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
         Task task = taskRepository.findById(taskId)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + taskId));

         if (user.getTasks().contains(task)) {
             throw new IllegalArgumentException("You are already working on this task");
         }
         user.getTasks().add(task);
         userRepository.save(user);
     }
     @Override
     public boolean areInputsInvalid(UserDTO request) {
         return  ValidationUtils.isStringNullOrEmpty(request.getUsername()) ||
                 ValidationUtils.isStringNullOrEmpty(request.getEmail()) ||
                 ValidationUtils.isStringNullOrEmpty(request.getPassword());
        }
        @Override
        public User registerUser(UserDTO request) {
            if (areInputsInvalid(request)) {
                throw new RuntimeException("Invalid user input");
            }

            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("User with the email you entered already exists!");
            }

            var user = UserMapper.mapDtoToEntity(request);

            return userRepository.save(user);
        }
        @Override
        public User authenticate(String email, String enteredPassword) {
            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (PasswordUtils.verifyPassword(enteredPassword, user.getPassword())) {
                    return user;
                } else {
                    throw new RuntimeException("Wrong password");
                }
            } else {
                throw new RuntimeException("User with email " + email + " does not exist!");
            }
        }

     public String getUsernameByEmail(String email) {
         Optional <String> usernameOptional = userRepository.findUsernameByEmail(email);
         if (usernameOptional.isPresent()) {
             return usernameOptional.get();
     }else {
             throw new RuntimeException("User with email " + email + " does not exist!");
         }
     }

     public int countUsers() {
            return userRepository.countUsers();
         }

     @Override
     public User getUserById(UUID userId) {
            return userRepository.findById(userId).orElse(null);
        }

     public List<Task> getUserTasks(UUID userId) {
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
         return user.getTasks();
     }
     public void removeTaskFromUser(UUID userId, Long taskId) {
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
         Task task = taskRepository.findById(taskId)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + taskId));

         user.getTasks().remove(task);
         userRepository.save(user);
     }

     public void updateUserTask(UUID userId, Long taskId, String status, String comment) {
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
         Task task = taskRepository.findById(taskId)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + taskId));

         UserTask userTask = userTaskRepository.findByUserIdAndTaskId(userId, taskId)
                 .orElse(new UserTask(user, task));

         userTask.setStatus(status);
         userTask.setComment(comment);

         userTaskRepository.save(userTask);
     }

 }