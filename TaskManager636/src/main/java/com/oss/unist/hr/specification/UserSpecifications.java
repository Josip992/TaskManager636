package com.oss.unist.hr.specification;

import com.oss.unist.hr.model.User;
import com.oss.unist.hr.model.enums.Role;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;


public class UserSpecifications {

    public static Specification<User> filterOutRole(String role) {
        return (root, query, criteriaBuilder) ->
                Optional.ofNullable(role)
                        .filter(s -> !s.isEmpty())
                        .map(s -> criteriaBuilder.notEqual(root.get("role"), Role.valueOf(s)))
                        .orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
    }

}