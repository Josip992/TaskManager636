package com.oss.unist.hr.repository;

import com.oss.unist.hr.model.Task;
import com.oss.unist.hr.model.User;
import com.oss.unist.hr.model.UserTask;
import com.oss.unist.hr.model.UserTaskId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId> {
    Optional<UserTask> findByUserIdAndTaskId(UUID userId, Long taskId);
    List<UserTask> findByUserId(UUID userId);
    List<UserTask> findByUserIdAndFinishedAtIsNotNull(UUID userId);
}

