package com.service;

import com.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommandProcessor {
    private final TaskService taskService;
    private final WishService wishService;
    private final Parent parent;
    private final Child child;

    public CommandProcessor(TaskService taskService, WishService wishService, Parent parent, Child child) {
        this.taskService = taskService;
        this.wishService = wishService;
        this.parent = parent;
        this.child = child;
    }

    public void process(String line) {
        try {
            if (line.startsWith("#") || line.trim().isEmpty()) return; // comment or blank
            String[] tokens = line.split(" ", 2);
            String cmd = tokens[0];
            String args = tokens.length > 1 ? tokens[1].trim() : "";

            switch (cmd) {
                case "LIST_ALL_TASKS" -> {
                    if (args.equals("D")) {
                        System.out.println("Daily tasks:");
                        taskService.getDailyTasks().forEach(System.out::println);
                    } else if (args.equals("W")) {
                        System.out.println("Weekly tasks:");
                        taskService.getWeeklyTasks().forEach(System.out::println);
                    } else {
                        System.out.println("All tasks:");
                        taskService.listAllTasks();
                    }
                }
                case "ADD_TASK1" -> handleAddTask1(args);
                case "ADD_TASK2" -> handleAddTask2(args);
                case "TASK_DONE" -> taskService.markTaskDone(args);
                case "TASK_CHECKED" -> handleTaskChecked(args);
                case "ADD_WISH1" -> handleAddWishQuoted(args);
                case "ADD_WISH2" -> handleAddWishWithTime(args);
                case "LIST_ALL_WISHES" -> wishService.listAllWishes();
                case "PRINT_BUDGET" -> System.out.println("Child's budget: " + child.getPoints());
                case "WISH_CHECKED" -> handleWishChecked(args);
                case "ADD_BUDGET_COIN" -> {
                    int points = Integer.parseInt(args.split(" ")[0]);
                    child.addPoints(points);
                    System.out.println("Added budget coin: " + points);
                }
                case "PRINT_STATUS" -> System.out.println(child);
                default -> System.out.println("Unknown command: " + cmd);
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + line + "\n" + e);
        }
    }

    private void handleAddTask1(String args) {
        // ADD_TASK1 T 105 "English Homework" "Learn a new content" 2025-05-18 23:56 10
        try {
            String[] tokens = args.split(" ", 4);
            String type = tokens[0];
            String id = tokens[1];
            String rest = tokens[2] + " " + tokens[3];
            List<String> quoted = extractQuotedArgs(rest);
            String title = quoted.get(0);
            String desc = quoted.get(1);
            int descEndIdx = rest.indexOf('"', rest.indexOf('"') + 1) + 1;
            descEndIdx = rest.indexOf('"', descEndIdx) + 1;
            descEndIdx = rest.indexOf('"', descEndIdx) + 1;
            String afterDesc = rest.substring(descEndIdx).trim();
            String[] afterDescArr = afterDesc.split(" ");
            String date = afterDescArr[0];
            String time = afterDescArr[1];
            int points = Integer.parseInt(afterDescArr[2]);
            LocalDateTime deadline = LocalDateTime.parse(date + "T" + time);
            Task task = new Task(id, title, desc, deadline, TaskType.TASK1);
            task.setRating(points);
            taskService.addTask(task);
            child.addTask(task);
        } catch (Exception e) {
            System.out.println("Invalid ADD_TASK1 format: " + args);
        }
    }

    private void handleAddTask2(String args) {
        // ADD_TASK2 F 106 "Painting" "Draw a scenery" 2025-05-19 14:00 2025-05-19 23:30 8
        try {
            String[] tokens = args.split(" ", 4);
            String type = tokens[0];
            String id = tokens[1];
            String rest = tokens[2] + " " + tokens[3];
            List<String> quoted = extractQuotedArgs(rest);
            String title = quoted.get(0);
            String desc = quoted.get(1);
            int descEndIdx = rest.indexOf('"', rest.indexOf('"') + 1) + 1;
            descEndIdx = rest.indexOf('"', descEndIdx) + 1;
            descEndIdx = rest.indexOf('"', descEndIdx) + 1;
            String afterDesc = rest.substring(descEndIdx).trim();
            String[] afterDescArr = afterDesc.split(" ");
            String startDate = afterDescArr[0];
            String startTime = afterDescArr[1];
            String endDate = afterDescArr[2];
            String endTime = afterDescArr[3];
            int points = Integer.parseInt(afterDescArr[4]);
            LocalDateTime deadline = LocalDateTime.parse(startDate + "T" + startTime);
            Task task = new Task(id, title, desc, deadline, TaskType.TASK2);
            task.setRating(points);
            taskService.addTask(task);
            child.addTask(task);
        } catch (Exception e) {
            System.out.println("Invalid ADD_TASK2 format: " + args);
        }
    }

    private void handleTaskChecked(String args) {
        String[] parts = args.split(" ");
        String taskId = parts[0];
        int rating = Integer.parseInt(parts[1]);
        Task task = taskService.getTaskById(taskId);
        if (task != null && task.getStatus() == TaskStatus.DONE) {
            task.setRating(rating);
            child.addPoints(rating);
            child.updateAverageRating();
            System.out.println("Task checked: " + taskId + ", rating: " + rating);
        } else {
            System.out.println("Task not found or not done: " + taskId);
        }
    }

    private void handleAddWishQuoted(String args) {
        try {
            List<String> parts = extractQuotedArgsWithLeadingId(args);
            String id = parts.get(0);
            String title = parts.get(1);
            String desc = parts.get(2);
            Wish wish = new Wish(id, title, desc, WishType.PRODUCT, LocalDateTime.now());
            wishService.addWish(wish);
        } catch (Exception e) {
            System.out.println("Invalid ADD_WISH1 format: " + args);
        }
    }

    private void handleAddWishWithTime(String args) {
        try {
            List<String> parts = extractQuotedArgsWithLeadingId(args);
            String id = parts.get(0);
            String title = parts.get(1);
            String desc = parts.get(2);
            String[] dateParts = args.replace(id, "").replace('"'+title+'"', "").replace('"'+desc+'"', "").trim().split(" ");
            String startDate = dateParts[0];
            String startTime = dateParts[1];
            String endDate = dateParts[2];
            String endTime = dateParts[3];
            LocalDateTime start = LocalDateTime.parse(startDate + "T" + startTime);
            LocalDateTime end = LocalDateTime.parse(endDate + "T" + endTime);
            Wish wish = new Wish(id, title, desc, WishType.ACTIVITY, start);
            wish.setStartTime(start);
            wish.setEndTime(end);
            wishService.addWish(wish);
        } catch (Exception e) {
            System.out.println("Invalid ADD_WISH2 format: " + args);
        }
    }

    private void handleWishChecked(String args) {
        String[] parts = args.split(" ");
        String wishId = parts[0];
        String action = parts[1];
        if (action.equals("APPROVED")) {
            int requiredLevel = parts.length > 2 ? Integer.parseInt(parts[2]) : 1;
            int level = switch (child.getLevel()) {
                case "Bronze" -> 1;
                case "Silver" -> 2;
                case "Gold" -> 3;
                default -> 0;
            };
            if (level >= requiredLevel) {
                wishService.approveWish(wishId);
                System.out.println("Wish " + wishId + " approved.");
            } else {
                wishService.rejectWish(wishId);
                System.out.println("Child's level (" + level + ") is insufficient for required level " + requiredLevel + ". Rejecting wish " + wishId);
            }
        } else if (action.equals("REJECTED")) {
            wishService.rejectWish(wishId);
            System.out.println("Wish " + wishId + " rejected.");
        }
    }

    private List<String> extractQuotedArgs(String input) {
        List<String> parts = new ArrayList<>();
        int index = 0;
        while (index < input.length()) {
            if (input.charAt(index) == '"') {
                int start = input.indexOf('"', index);
                int end = input.indexOf('"', start + 1);
                if (start == -1 || end == -1) break;
                parts.add(input.substring(start + 1, end));
                index = end + 1;
            } else {
                index++;
            }
        }
        return parts;
    }

    private List<String> extractQuotedArgsWithLeadingId(String input) {
        List<String> parts = new ArrayList<>();
        String[] tokens = input.split(" ", 2);
        parts.add(tokens[0]); // ID (ör: W204)
        if (tokens.length > 1) parts.addAll(extractQuotedArgs(tokens[1]));
        return parts;
    }
}
