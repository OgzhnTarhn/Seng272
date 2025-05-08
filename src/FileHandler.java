import com.kidtask.model.Task;
import com.kidtask.model.TaskStatus;
import com.kidtask.model.TaskType;
import com.kidtask.model.Wish;
import com.kidtask.model.WishStatus;
import com.kidtask.model.WishType;

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
            String taskType = parts[0].trim();
            String taskId = parts[1].trim();
            String title = parts[2].replace("\"", "").trim();
            String desc = parts[3].replace("\"", "").trim();
            if (taskType.equals("TASK1")) {
                LocalDateTime deadline = LocalDateTime.parse(parts[4].trim());
                int points = Integer.parseInt(parts[5].trim());
                Task task = new Task(taskId, title, desc, deadline, TaskType.TASK1);
                task.setStatus(TaskStatus.TODO);
                task.setRating(points);
                return task;
            } else if (taskType.equals("TASK2")) {
                LocalDateTime start = LocalDateTime.parse(parts[4].trim());
                LocalDateTime end = LocalDateTime.parse(parts[5].trim());
                int points = Integer.parseInt(parts[6].trim());
                // TASK2 için sadece start'ı deadline olarak kullanıyoruz
                Task task = new Task(taskId, title, desc, start, TaskType.TASK2);
                task.setStatus(TaskStatus.TODO);
                task.setRating(points);
                return task;
            }
        } catch (Exception e) {
            System.out.println("Invalid task format: " + line);
        }
        return null;
    }

    public static void writeWishes(String filePath, List<Wish> wishes) {
        List<String> lines = new ArrayList<>();
        for (Wish wish : wishes) {
            String line = String.format(
                    "%s %s \"%s\" \"%s\" %s %s",
                    wish.getWishType().name(),
                    wish.getWishId(),
                    wish.getTitle(),
                    wish.getDescription(),
                    wish.getStartTime() != null ? wish.getStartTime().toString() : "null",
                    wish.getEndTime() != null ? wish.getEndTime().toString() : "null"
            );
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