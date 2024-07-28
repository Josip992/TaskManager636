package com.oss.unist.hr.repository;

import com.oss.unist.hr.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjectName(String projectName);
    List<Project> findByProjectNameContaining(String projectName);
}
