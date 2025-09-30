package com.example.taskapi.service;

import com.example.taskapi.entity.Task;
import com.example.taskapi.enums.TaskStatus;
import com.example.taskapi.exception.ResourceNotFoundException;
import com.example.taskapi.repository.TaskRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    @Caching(evict = {
        @CacheEvict(cacheNames = "tasks", allEntries = true)
    })
    public Task createTask(Task t) {
        // createdAt auto-set by @PrePersist
        return repo.save(t);
    }

    @Cacheable(cacheNames = "tasks")
    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    @Cacheable(cacheNames = "task", key = "#id")
    public Task getTaskById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(cacheNames = "task", key = "#id"),
        @CacheEvict(cacheNames = "tasks", allEntries = true)
    })
    public Task updateStatus(Long id, TaskStatus newStatus) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        task.setStatus(newStatus);
        return repo.save(task);
    }

    @Caching(evict = {
        @CacheEvict(cacheNames = "task", key = "#id"),
        @CacheEvict(cacheNames = "tasks", allEntries = true)
    })
    public void deleteTask(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
        repo.deleteById(id);
    }
}
