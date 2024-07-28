package com.oss.unist.hr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;

    private Integer priority;

    //due date

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
