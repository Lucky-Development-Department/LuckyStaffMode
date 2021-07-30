package id.luckynetwork.dev.luckystaffmode.handlers;

import id.luckynetwork.dev.luckystaffmode.utils.ExecutorUtils;
import lombok.Getter;
import lombok.experimental.UtilityClass;

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

    public interface Callable {
        void call();
    }

}
