import com.kidtask.model.Wish;
import com.kidtask.model.WishStatus;

import java.util.ArrayList;
import java.util.List;

public class WishService {
    private List<Wish> wishes;

    public WishService(List<Wish> initialWishes) {
        this.wishes = new ArrayList<>(initialWishes);
    }

    public void addWish(Wish wish) {
        wishes.add(wish);
        System.out.println("Wish added: " + wish.getTitle());
    }

    public void listAllWishes() {
        if (wishes.isEmpty()) {
            System.out.println("No wishes found.");
            return;
        }
        for (Wish wish : wishes) {
            System.out.println(wish);
        }
    }

    public void approveWish(String wishId) {
        Wish wish = getWishById(wishId);
        if (wish != null && wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.APPROVED);
            System.out.println("Wish approved: " + wish.getTitle());
        } else {
            System.out.println("Wish not found or already processed.");
        }
    }

    public void rejectWish(String wishId) {
        Wish wish = getWishById(wishId);
        if (wish != null && wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.REJECTED);
            System.out.println("Wish rejected: " + wish.getTitle());
        } else {
            System.out.println("Wish not found or already processed.");
        }
    }

    public Wish getWishById(String id) {
        for (Wish wish : wishes) {
            if (wish.getWishId().equals(id)) {
                return wish;
            }
        }
        return null;
    }

    public List<Wish> getPendingWishes() {
        List<Wish> pending = new ArrayList<>();
        for (Wish wish : wishes) {
            if (wish.getStatus() == WishStatus.PENDING) {
                pending.add(wish);
            }
        }
        return pending;
    }
}
