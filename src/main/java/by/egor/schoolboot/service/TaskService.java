package by.egor.schoolboot.service;

import by.egor.schoolboot.dto.Task;
import by.egor.schoolboot.entity.TaskEntity;
import by.egor.schoolboot.entity.TaskStatus;
import by.egor.schoolboot.filter.TaskSearchFilter;
import by.egor.schoolboot.mapper.TaskMapper;
import by.egor.schoolboot.repository.TaskRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.mapper = taskMapper;
    }

    public Task getTaskById(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task with id " + id + " not found"));
        return mapper.toDomain(taskEntity);
    }

    public List<Task> getAllTasksWithFilter(TaskSearchFilter filter) {
        int pageCount = filter.pageCount() != null ? filter.pageCount() : 10;
        int pageNumber = filter.pageNumber() != null ? filter.pageNumber() : 0;

        var pageable = Pageable.ofSize(pageCount).withPage(pageNumber);

        List<TaskEntity> entities = taskRepository.searchAllByFilter(
                filter.creatorId(),
                filter.assignedUserId(),
                filter.status(),
                filter.priority(),
                pageable
        );

        return entities.stream().map(mapper::toDomain).toList();
    }

    @Transactional
    public Task saveTask(Task taskToCreate) {
        if (taskToCreate.getId() != null || taskToCreate.getStatus() != null) {
            throw new IllegalArgumentException("There should be no id or status in task to create");
        }
        var taskToPut = mapper.toEntity(taskToCreate);
        taskToPut.setStatus(TaskStatus.CREATED);
        TaskEntity savedEntity = taskRepository.save(taskToPut);
        return mapper.toDomain(savedEntity);
    }

    @Transactional
    public Task updateTask(Long id, Task taskToUpdate) {
        TaskEntity existingEntity = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task with id " + id + " not found"));

        if (taskToUpdate.getId() != null) {
            throw new IllegalArgumentException("There should be no id in task to update");
        }

        if (existingEntity.getStatus() == TaskStatus.DONE) {
            throw new IllegalArgumentException("Unable to update done task");
        }

        existingEntity.setCreatorId(taskToUpdate.getCreatorId());
        existingEntity.setAssignedUserId(taskToUpdate.getAssignedUserId());
        existingEntity.setStatus(taskToUpdate.getStatus());
        existingEntity.setCreatedDateTime(taskToUpdate.getCreatedDateTime());
        existingEntity.setDeadlineDate(taskToUpdate.getDeadlineDate());
        existingEntity.setPriority(taskToUpdate.getPriority());

        return mapper.toDomain(existingEntity);
    }

    @Transactional
    public Task makeTaskActive(Long id) {
        TaskEntity taskToActivate = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task with id " + id + " not found"));
        if (taskToActivate.getAssignedUserId() == null) {
            throw new IllegalArgumentException("Unable to activate task");
        }

        if (taskRepository.countTasks(taskToActivate.getAssignedUserId(), TaskStatus.IN_PROGRESS) >= 5) {
            throw new IllegalArgumentException("User has too many active tasks");
        }

        taskToActivate.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(taskToActivate);
        return mapper.toDomain(taskToActivate);
    }


    @Transactional
    public Task completeTask(Long id) {
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task with id " + id + " not found"));

        if (entity.getAssignedUserId() == null || entity.getDeadlineDate() == null) {
            throw new IllegalArgumentException("Unable to complete task");
        }

        entity.setStatus(TaskStatus.DONE);
        entity.setDoneDateTime(LocalDateTime.now());

        return mapper.toDomain(entity);
    }

    @Transactional
    public void deleteTask(Long id) {
        getTaskById(id);
        taskRepository.deleteById(id);
    }

}