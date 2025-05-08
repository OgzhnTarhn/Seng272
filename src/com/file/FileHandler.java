package com.file;

import com.model.Task;
import com.model.TaskStatus;
import com.model.TaskType;
import com.model.Wish;
import com.model.WishStatus;
import com.model.WishType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public static List<Task> readTasks(String filePath) throws IOException {
        List<Task> tasks = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(filePath))) {
            if (line.trim().isEmpty() || line.trim().startsWith("#")) continue;
            Task task = parseTask(line);
            if (task != null) tasks.add(task);
        }
        return tasks;
    }

    public static List<Wish> readWishes(String filePath) throws IOException {
        List<Wish> wishes = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(filePath))) {
            if (line.trim().isEmpty() || line.trim().startsWith("#")) continue;
            Wish wish = parseWish(line);
            if (wish != null) wishes.add(wish);
        }
        return wishes;
    }

    private static Task parseTask(String line) {
        try {
            String[] parts = line.split(";");
            if (parts.length < 6) {
                System.out.println("Invalid task format: Not enough fields in line: " + line);
                return null;
            }

            String taskType = parts[0].trim();
            String taskId = parts[1].trim();
            String title = parts[2].replace("\"", "").trim();
            String desc = parts[3].replace("\"", "").trim();

            // Validate required fields
            if (taskId.isEmpty() || title.isEmpty() || desc.isEmpty()) {
                System.out.println("Invalid task format: Required fields are empty in line: " + line);
                return null;
            }

            if (taskType.equals("TASK1")) {
                if (parts.length != 6) {
                    System.out.println("Invalid TASK1 format: Expected 6 fields, got " + parts.length + " in line: " + line);
                    return null;
                }
                try {
                    LocalDateTime deadline = LocalDateTime.parse(parts[4].trim());
                    int points = Integer.parseInt(parts[5].trim());
                    Task task = new Task(taskId, title, desc, deadline, TaskType.TASK1);
                    task.setStatus(TaskStatus.TODO);
                    task.setRating(points);
                    return task;
                } catch (Exception e) {
                    System.out.println("Error parsing TASK1 date or points: " + e.getMessage() + " in line: " + line);
                    return null;
                }
            } else if (taskType.equals("TASK2")) {
                if (parts.length != 7) {
                    System.out.println("Invalid TASK2 format: Expected 7 fields, got " + parts.length + " in line: " + line);
                    return null;
                }
                try {
                    LocalDateTime start = LocalDateTime.parse(parts[4].trim());
                    LocalDateTime end = LocalDateTime.parse(parts[5].trim());
                    int points = Integer.parseInt(parts[6].trim());
                    
                    // Validate that end time is after start time
                    if (end.isBefore(start)) {
                        System.out.println("Invalid TASK2 format: End time is before start time in line: " + line);
                        return null;
                    }

                    Task task = new Task(taskId, title, desc, start, TaskType.TASK2);
                    task.setStatus(TaskStatus.TODO);
                    task.setRating(points);
                    return task;
                } catch (Exception e) {
                    System.out.println("Error parsing TASK2 dates or points: " + e.getMessage() + " in line: " + line);
                    return null;
                }
            } else {
                System.out.println("Invalid task type: " + taskType + " in line: " + line);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Unexpected error parsing task: " + e.getMessage() + " in line: " + line);
            return null;
        }
    }

    public static void writeWishes(String filePath, List<Wish> wishes) {
        List<String> lines = new ArrayList<>();
        for (Wish wish : wishes) {
            String line;
            if (wish.getWishType() == WishType.PRODUCT) {
                line = String.format(
                        "WISH1;%s;\"%s\";\"%s\"",
                        wish.getWishId(),
                        wish.getTitle(),
                        wish.getDescription()
                );
            } else {
                line = String.format(
                        "WISH2;%s;\"%s\";\"%s\";%s;%s",
                        wish.getWishId(),
                        wish.getTitle(),
                        wish.getDescription(),
                        wish.getStartTime() != null ? wish.getStartTime().toString() : "null",
                        wish.getEndTime() != null ? wish.getEndTime().toString() : "null"
                );
            }
            lines.add(line);
        }
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            System.out.println("Error writing wishes: " + e.getMessage());
        }
    }

    private static Wish parseWish(String line) {
        try {
            String[] parts = line.split(";");
            String wishType = parts[0].trim();
            String wishId = parts[1].trim();
            String title = parts[2].replace("\"", "").trim();
            String desc = parts[3].replace("\"", "").trim();
            if (wishType.equals("WISH1")) {
                Wish wish = new Wish(wishId, title, desc, WishType.PRODUCT, LocalDateTime.now());
                wish.setStatus(WishStatus.PENDING);
                return wish;
            } else if (wishType.equals("WISH2")) {
                LocalDateTime start = parts[4].trim().equals("null") ? null : LocalDateTime.parse(parts[4].trim());
                LocalDateTime end = parts[5].trim().equals("null") ? null : LocalDateTime.parse(parts[5].trim());
                Wish wish = new Wish(wishId, title, desc, WishType.ACTIVITY, start != null ? start : LocalDateTime.now());
                wish.setStartTime(start);
                wish.setEndTime(end);
                wish.setStatus(WishStatus.PENDING);
                return wish;
            }
        } catch (Exception e) {
            System.out.println("Invalid wish format: " + line);
        }
        return null;
    }
}