package id.luckynetwork.dev.luckystaffmode.handlers;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.utils.ExecutorUtils;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@UtilityClass
public class LyraTasks {

    @Getter
    private final ExecutorService executor = ExecutorUtils.getUnlimitedExecutorService("LStaffMode LyraTasks Thread - %d");

    public void runLaterAsync(Callable callable, long delay) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            callable.call();
        }, executor).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    public void runLater(Callable callable, long delayInTicks) {
        Bukkit.getScheduler().runTaskLater(LuckyStaffMode.getInstance(), callable::call, delayInTicks);
    }

    public interface Callable {
        void call();
    }

}
