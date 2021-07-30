package id.luckynetwork.dev.luckystaffmode.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class ExecutorUtils {

    private final int poolSize = Runtime.getRuntime().availableProcessors() + 2;

    public ExecutorService getFixedExecutorService(String name) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(name).setThreadFactory(new LuckyThreadFactory(name)).build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize,
                15L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                factory);

        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    public ExecutorService getUnlimitedExecutorService(String name) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(name).setThreadFactory(new LuckyThreadFactory(name)).build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                15L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                factory);

        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    /**
     * The custom thread factory
     */
    static class LuckyThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        LuckyThreadFactory(String name) {
            SecurityManager manager = System.getSecurityManager();
            group = (manager != null) ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = String.format(name + "-", poolNumber.getAndIncrement());
        }

        public Thread newThread(@NotNull Runnable runnable) {
            Thread thread = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
            if (thread.isDaemon())
                thread.setDaemon(false);
            if (thread.getPriority() != Thread.NORM_PRIORITY)
                thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }
}
