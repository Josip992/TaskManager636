package com.oss.unist.hr.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;
@Data
public class UserTaskId implements Serializable {
    private UUID userId;
    private Long taskId;

}