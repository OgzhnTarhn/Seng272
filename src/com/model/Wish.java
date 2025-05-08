package com.model;

import java.time.LocalDateTime;

public class Wish {
    private String wishId;
    private String title;
    private String description;
    private WishStatus status;
    private WishType wishType;
    private LocalDateTime creationTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Wish(String wishId, String title, String description, WishType wishType, LocalDateTime creationTime) {
        this.wishId = wishId;
        this.title = title;
        this.description = description;
        this.status = WishStatus.PENDING;
        this.wishType = wishType;
        this.creationTime = creationTime;
        this.startTime = null;
        this.endTime = null;
    }

    // getters and setters (tüm attributeler için)

    public String getWishId() { return wishId; }
    public void setWishId(String wishId) { this.wishId = wishId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public WishStatus getStatus() { return status; }
    public void setStatus(WishStatus status) { this.status = status; }

    public WishType getWishType() { return wishType; }
    public void setWishType(WishType wishType) { this.wishType = wishType; }

    public LocalDateTime getCreationTime() { return creationTime; }
    public void setCreationTime(LocalDateTime creationTime) { this.creationTime = creationTime; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return "Wish{" +
                "wishId='" + wishId + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", wishType=" + wishType +
                ", creationTime=" + creationTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
