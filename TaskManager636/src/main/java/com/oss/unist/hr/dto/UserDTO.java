package com.oss.unist.hr.dto;

import com.oss.unist.hr.model.enums.Role;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@ToString
public class UserDTO {


    private List<TaskDTO> tasks;
    private String comment;
    private String project;

    private UUID id;
    private String username = "";
    private String email = "";
    private String password = "";
    private String country = "";
    private Role role = Role.USER;

    public UserDTO() {

    }

    public UserDTO(String email, String password) {

        this.email = email;
        this.password = password;
    }


}

