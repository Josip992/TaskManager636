package com.oss.unist.hr.service.implementation;

import com.oss.unist.hr.dto.UserDTO;
import com.oss.unist.hr.mapper.UserMapper;
import com.oss.unist.hr.model.User;
import com.oss.unist.hr.repository.UserRepository;
import com.oss.unist.hr.service.AdminService;
import com.oss.unist.hr.specification.UserSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAllUsers(String currentUsername) {
        return userRepository.findAll().stream()
                .filter(user -> !user.getUsername().equals(currentUsername))
                .collect(Collectors.toList());
    }

    public User updateUser(UUID userId, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setCountry(updatedUser.getCountry());
            user.setRole(updatedUser.getRole());
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }
    }

    public void deleteUser(UUID userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }
    }
    public List<UserDTO> filterUsersByRole(String role) {

        Specification<User> spec = UserSpecifications.filterOutRole(role);

        Sort sort = Sort.by(Sort.Direction.ASC, "username");
        var filteredUsers = userRepository.findAll(spec, sort);

        return filteredUsers.stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
