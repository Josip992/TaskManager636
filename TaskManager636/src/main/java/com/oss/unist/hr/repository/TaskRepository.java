package com.oss.unist.hr.repository;

import com.oss.unist.hr.model.Project;
import com.oss.unist.hr.model.Task;
import com.oss.unist.hr.model.TaskResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTaskName(String taskName);
    List<Task> findByTaskNameContaining(String taskName);

    @Query(value = "SELECT CONCAT(EXTRACT(YEAR FROM finished_at), '-', EXTRACT(MONTH FROM finished_at)) AS month, COUNT(*) as count " +
            "FROM user_tasks " +
            "WHERE status = 'Finished' AND user_id = :userId AND finished_at IS NOT NULL " +
            "GROUP BY month " +
            "ORDER BY month ASC", nativeQuery = true)
    List<TaskResult> findTasksPerMonth(@Param("userId") UUID userId);

}
