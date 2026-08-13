package by.egor.schoolboot.dto;

import by.egor.schoolboot.entity.TaskPriority;
import by.egor.schoolboot.entity.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    private Long id;

    @NotNull(message = "creator id field can't be null")
    private Long creatorId;

    private Long assignedUserId;

    private TaskStatus status;

    private LocalDateTime createdDateTime;

    @NotNull(message = "deadline date field can't be null")
    @Future(message = "past date in the deadline date field")
    private LocalDate deadlineDate;

    @NotNull(message = "priority field can't be null")
    private TaskPriority priority;

    private LocalDateTime doneDateTime;

    public Task() {
    }

    public Task(Long id, Long creatorId, Long assignedUserId, TaskStatus status, LocalDateTime createdDateTime, LocalDate deadlineDate, TaskPriority priority) {
        this.id = id;
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createdDateTime = createdDateTime;
        this.deadlineDate = deadlineDate;
        this.priority = priority;
    }

    public Task(Long creatorId, Long assignedUserId, TaskStatus status, LocalDateTime createdDateTime, LocalDate deadlineDate, TaskPriority priority) {
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createdDateTime = createdDateTime;
        this.deadlineDate = deadlineDate;
        this.priority = priority;
    }

    public Task(Long creatorId, Long assignedUserId, TaskStatus status, LocalDateTime createdDateTime, LocalDate deadlineDate, TaskPriority priority, LocalDateTime doneDateTime) {
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createdDateTime = createdDateTime;
        this.deadlineDate = deadlineDate;
        this.priority = priority;
        this.doneDateTime = doneDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDoneDateTime() {
        return doneDateTime;
    }

    public void setDoneDateTime(LocalDateTime doneDateTime) {
        this.doneDateTime = doneDateTime;
    }
}
