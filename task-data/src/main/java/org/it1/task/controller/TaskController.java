package org.it1.task.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.it1.task.entity.Task;
import org.it1.task.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/task-data")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public Iterable<Task> getAllTasks() {
        var tasks = taskService.getAll();
        log.info("All: {}", tasks);
        return tasks;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        var task = taskService.findById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Found {}", task);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {
        return taskService.add(task);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Task updatedTask) {
        if (taskService.update(id, updatedTask)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
