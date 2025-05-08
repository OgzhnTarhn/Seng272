import java.time.LocalDateTime;

public class Task {
    private String taskId;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private TaskStatus status;
    private double rating;
    private TaskType taskType;

    public Task(String taskId, String title, String description, LocalDateTime deadline, TaskType taskType) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = TaskStatus.PENDING;
        this.taskType = taskType;
        this.rating = 0;
    }

    // getters and setters (tüm attributeler için)

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public TaskType getTaskType() { return taskType; }
    public void setTaskType(TaskType taskType) { this.taskType = taskType; }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", title='" + title + '\'' +
                ", deadline=" + deadline +
                ", status=" + status +
                ", rating=" + rating +
                ", taskType=" + taskType +
                '}';
    }
}
