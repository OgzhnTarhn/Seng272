package com.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {
    private List<Task> createdTasks;
    private int budgetPoints;

    public Teacher(String userId, String name) {
        super(userId, name, "TEACHER");
        this.createdTasks = new ArrayList<>();
        this.budgetPoints = 0;
    }

    public Task createTask(String taskId,
                           String title,
                           String description,
                           TaskType taskType,
                           LocalDateTime deadline,
                           Child assignedChild) {
        Task task = new Task(taskId, title, description, deadline, taskType);
        task.setStatus(TaskStatus.PENDING);
        assignedChild.addTask(task);
        this.createdTasks.add(task);
        System.out.println("Teacher " + getName() + " created task: " + title);
        return task;
    }

    public void approveTask(Task task, int rating, Child child) {
        if (isTaskCompleted(task)) {
            task.setStatus(TaskStatus.DONE);  // Alternatif olarak APPROVED enum tanımı varsa oraya güncellenebilir
            task.setRating(rating);
            child.addPoints(rating); // Puan rating üzerinden veriliyor
            child.updateAverageRating();
            System.out.println("Teacher approved task " + task.getTitle() + " with rating " + rating);
        } else {
            System.out.println("Task " + task.getTitle() + " is not completed yet.");
        }
    }

    private boolean isTaskCompleted(Task task) {
        return task.getStatus() == TaskStatus.DONE;
    }

    public void addBudgetPoints(Child child, int points) {
        this.budgetPoints -= points;
        child.addPoints(points);
        System.out.println("Teacher " + getName() + " added " + points + " budget points to child " + child.getName());
    }

    public List<Task> getCreatedTasks() {
        return createdTasks;
    }

    public int getBudgetPoints() {
        return budgetPoints;
    }

    public void addToBudget(int points) {
        this.budgetPoints += points;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", createdTasks=" + createdTasks.size() +
                ", budgetPoints=" + budgetPoints +
                '}';
    }
}
