package com.example.taskapi.dto;

import com.example.taskapi.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateRequest {
    @NotNull(message = "status is required")
    private TaskStatus status;

    public StatusUpdateRequest() {}
    public StatusUpdateRequest(TaskStatus status) { this.status = status; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}
