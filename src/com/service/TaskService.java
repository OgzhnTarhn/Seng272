package com.service;

import com.model.Task;
import com.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {
    private List<Task> tasks;

    public TaskService(List<Task> initialTasks) {
        this.tasks = new ArrayList<>(initialTasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added: " + task.getTitle());
    }

    public void markTaskDone(String taskId) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                task.setStatus(TaskStatus.DONE);
                System.out.println("Task marked as done: " + task.getTitle());
                return;
            }
        }
        System.out.println("Task not found: " + taskId);
    }

    public void listAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public List<Task> getDailyTasks() {
        LocalDateTime now = LocalDateTime.now();
        return tasks.stream()
                .filter(t -> t.getDeadline().toLocalDate().equals(now.toLocalDate()))
                .collect(Collectors.toList());
    }

    public List<Task> getWeeklyTasks() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysLater = now.plusDays(7);
        return tasks.stream()
                .filter(t -> !t.getDeadline().isBefore(now) && !t.getDeadline().isAfter(sevenDaysLater))
                .collect(Collectors.toList());
    }

    public Task getTaskById(String id) {
        return tasks.stream()
                .filter(t -> t.getTaskId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
