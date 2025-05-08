import com.kidtask.file.FileHandler;
import com.kidtask.model.Child;
import com.kidtask.model.Parent;
import com.kidtask.model.Task;
import com.kidtask.model.Wish;
import com.kidtask.service.CommandProcessor;
import com.kidtask.service.TaskService;
import com.kidtask.service.WishService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String tasksFile = "tasks.txt";
        String wishesFile = "wishes.txt";
        String commandsFile = "commands.txt";

        try {
            // Dosyalardan görev ve dilekleri oku
            List<Task> tasks = FileHandler.readTasks(tasksFile);
            List<Wish> wishes = FileHandler.readWishes(wishesFile);

            // Servisleri oluştur
            TaskService taskService = new TaskService(tasks);
            WishService wishService = new WishService(wishes);

            // Komut işlemcisini başlat
            Parent parent = new Parent("P1", "Parent1");
            Child child = new Child("C1", "Child1");

            // Dosyadan okunan görev ve dilekleri çocuğa da ekle
            for (Task t : tasks) {
                child.addTask(t);
            }
            for (Wish w : wishes) {
                child.addWish(w);
            }

            CommandProcessor processor = new CommandProcessor(taskService, wishService, parent, child);

            // Komutları sırayla oku ve işle
            List<String> commands = Files.readAllLines(Paths.get(commandsFile));
            for (String cmd : commands) {
                if (!cmd.trim().isEmpty()) {
                    processor.process(cmd);
                }
            }

            // Değişmiş dilekleri yaz (opsiyonel)
            FileHandler.writeWishes(wishesFile, wishes);

        } catch (Exception e) {
            System.err.println("Program çalışırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
