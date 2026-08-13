package by.egor.schoolboot.controller;

import by.egor.schoolboot.dto.Task;
import by.egor.schoolboot.entity.TaskPriority;
import by.egor.schoolboot.entity.TaskStatus;
import by.egor.schoolboot.filter.TaskSearchFilter;
import by.egor.schoolboot.service.TaskService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private Logger logger = LogManager.getLogger(TaskController.class);

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTasksWithFilter(
            @RequestParam(required = false) Long creatorId,
            @RequestParam(required = false) Long assignedUserId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageCount
    ) {
        logger.info("getAllTasksWithFilter method was invoked");
        var filter = new TaskSearchFilter(creatorId, assignedUserId, status, priority, pageNumber, pageCount);
        List<Task> tasks = taskService.getAllTasksWithFilter(filter);
        logger.info("getAllTasksWithFilter returned {} tasks", tasks.size());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        logger.info("getTask method was invoked with id={}", id);
        Task task = taskService.getTaskById(id);
        logger.info("getTask returned: {}", task);
        return ResponseEntity.ok(task);
    }

    @PostMapping()
    public ResponseEntity<Task> createTask(@RequestBody @Valid Task task) {
        logger.info("createTask method was invoked with task={}", task);
        Task savedTask = taskService.saveTask(task);
        logger.info("createTask returned: {}", savedTask);
        return ResponseEntity.status(201).body(savedTask);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Task> activateTask(@PathVariable Long id) {
        logger.info("activateTask method was invoked with id={}", id);
        Task task = taskService.makeTaskActive(id);
        logger.info("activateTask returned: {}", task);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/tasks/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) {
        Task task = taskService.completeTask(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(name = "id") Long id, @RequestBody @Valid Task task) {
        logger.info("updateTask method was invoked with task={}", task);
        Task updatedTask = taskService.updateTask(id, task);
        logger.info("updateTask returned: {}", updatedTask);
        return ResponseEntity.status(200).body(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(name = "id") Long id) {
        logger.info("deleteTask method was invoked with id={}", id);
        taskService.deleteTask(id);
        logger.info("deleteTask completed for id={}", id);
        return ResponseEntity.status(200).build();
    }
}
