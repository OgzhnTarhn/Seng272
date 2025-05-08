package com.model;

public class Parent extends User {
    private int budgetPoints;

    public Parent(String userId, String name) {
        super(userId, name, "PARENT");
        this.budgetPoints = 0;
    }

    public int getBudgetPoints() {
        return budgetPoints;
    }

    public void addBudgetPoints(int points) {
        this.budgetPoints += points;
    }

    public void deductBudgetPoints(int points) {
        this.budgetPoints = Math.max(0, this.budgetPoints - points);
    }

    public void approveTask(Task task, Child child, int rewardPoints, double rating) {
        if (task.getStatus() == TaskStatus.PENDING) {
            task.setStatus(TaskStatus.DONE);
            task.setRating(rating);
            child.addPoints(rewardPoints);
            child.updateAverageRating();
        }
    }

    public void approveWish(Wish wish) {
        if (wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.APPROVED);
        }
    }

    public void rejectWish(Wish wish) {
        if (wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.REJECTED);
        }
    }

    // Parent.java içinde bu metodu tanımla:
    public void addBudgetPoints(Child child, int points) {
        this.budgetPoints -= points;
        child.addPoints(points);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", budgetPoints=" + budgetPoints +
                '}';
    }
}
