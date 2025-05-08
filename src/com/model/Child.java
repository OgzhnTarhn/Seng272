package com.model;

import java.util.ArrayList;
import java.util.List;

public class Child extends User {
    private List<Task> tasks;
    private List<Wish> wishes;
    private int points;
    private double averageRating;

    public Child(String userId, String name) {
        super(userId, name, "CHILD");
        this.tasks = new ArrayList<>();
        this.wishes = new ArrayList<>();
        this.points = 0;
        this.averageRating = 0.0;
    }

    // Görev ekle
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Dilek ekle
    public void addWish(Wish wish) {
        wishes.add(wish);
    }

    // Görevleri getir
    public List<Task> getTasks() {
        return tasks;
    }

    // Dilekleri getir
    public List<Wish> getWishes() {
        return wishes;
    }

    // Puan ekle
    public void addPoints(int point) {
        this.points += point;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void updateAverageRating() {
        if (tasks.isEmpty()) {
            averageRating = 0.0;
            return;
        }
        double total = 0.0;
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.DONE) {
                total += task.getRating();
                count++;
            }
        }
        this.averageRating = (count == 0) ? 0.0 : total / count;
    }

    public String getLevel() {
        int currentLevel;
        if (averageRating < 2.0) {
            currentLevel = 1;
        } else if (averageRating < 3.0) {
            currentLevel = 2;
        } else if (averageRating < 4.0) {
            currentLevel = 3;
        } else {
            currentLevel = 4;
        }
        return "Level " + currentLevel;
    }

    @Override
    public String toString() {
        return "Child{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", points=" + points +
                ", averageRating=" + averageRating +
                ", level=" + getLevel() +
                '}';
    }
}
