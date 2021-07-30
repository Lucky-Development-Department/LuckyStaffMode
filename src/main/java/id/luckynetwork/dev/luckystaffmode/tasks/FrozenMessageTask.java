package id.luckynetwork.dev.luckystaffmode.tasks;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FrozenMessageTask implements Runnable {

    private final LuckyStaffMode plugin;
    private final List<String> message;

    @Override
    public void run() {
        plugin.getCacheManager().getFrozenPlayers().forEach(it -> message.forEach(it::sendMessage));
    }

}
