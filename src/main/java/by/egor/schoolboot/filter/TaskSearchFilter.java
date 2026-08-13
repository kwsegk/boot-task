package by.egor.schoolboot.filter;

import by.egor.schoolboot.entity.TaskPriority;
import by.egor.schoolboot.entity.TaskStatus;

public record TaskSearchFilter (
    Long creatorId,
    Long assignedUserId,
    TaskStatus status,
    TaskPriority priority,
    Integer pageNumber,
    Integer pageCount
) {
}
