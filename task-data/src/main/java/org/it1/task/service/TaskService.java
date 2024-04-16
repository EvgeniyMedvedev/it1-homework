package org.it1.task.service;

import lombok.AllArgsConstructor;
import org.it1.task.entity.Task;
import org.it1.task.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepositiry;

    public Iterable<Task> getAll() {
        return taskRepositiry.findAll();
    }

    public Task findById(Long id) {
        return taskRepositiry.findById(id).orElse(null);
    }

    @Transactional
    public Task add(Task task) {
        return taskRepositiry.save(task);
    }

    @Transactional
    public boolean update(Long id, Task updatedTask) {
        if (taskRepositiry.findById(id).isPresent()){
            updatedTask.setId(taskRepositiry.findById(id).get().getId());
            taskRepositiry.save(updatedTask);
            return true;
        }

        return false;
    }

    @Transactional
    public void delete(Long id) {
        taskRepositiry.deleteById(id);
    }
}
