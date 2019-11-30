package external;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;

import javax.net.ssl.SSLSocketFactory;
import java.util.Set;
import java.util.concurrent.*;

import static external.Util.*;

@Slf4j
@ThreadSafe
public class Client {

    private final Set<Subscriber> subscribers = new CopyOnWriteArraySet<>();
    private final Set<SubConnection> subConnections = new CopyOnWriteArraySet<>();
    private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private final Object subConMonitor = new Object();
    private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2, Util.threadFactory("nsq-sched"));

    private ExecutorService handlerExecutor;
    private SSLSocketFactory sslSocketFactory;
    private byte[] authSecret;

    private static final Client defaultClient = new Client();

    //--------------------------

    public static Client getDefaultClient() {
        return defaultClient;
    }

    public synchronized boolean stop() {
        return stop(2000);
    }

    /**
     * Stops all subscribers, waits for in-flight messages to be finished or requeued, stops the executor that handles messages,
     * then stops all publishers. All connections will be closed and no threads started by this client should be running when this returns.
     *
     * @param waitMillis Time to wait for everything to stop, in milliseconds. Soft limit that may be exceeded by about 200 ms.
     */
    public synchronized boolean stop(int waitMillis) {
        checkArgument(waitMillis > 0);
        log.info("stopping nsq client");
        long start = Util.clock();
        boolean isClean = stopSubscribers(waitMillis);

        if (handlerExecutor != null && !handlerExecutor.isTerminated()) {
            int timeout = Math.max((int) (waitMillis - (Util.clock() - start)), 100);
            isClean &= Util.shutdownAndAwaitTermination(handlerExecutor, timeout, TimeUnit.MILLISECONDS);
        }

        int timeout = Math.max((int) (waitMillis - (Util.clock() - start)), 100);
        isClean &= Util.shutdownAndAwaitTermination(scheduledExecutor, timeout, TimeUnit.MILLISECONDS);

        log.debug("handlerExecutor.isTerminated:{} scheduledExecutor.isTerminated:{} isClean:{}", handlerExecutor != null ? handlerExecutor.isTerminated() : "null", scheduledExecutor.isTerminated(), isClean);
        log.info("nsq client stopped");
        return isClean;
    }

    /**
     * Stops all subscribers, waits for in-flight messages to be finished or requeued, then closes subscriber connections.
     * Useful if you need to perform some action before publishers are stopped,
     * you should call stop() after this to shutdown all threads.
     *
     * @param waitMillis Time to wait for in-flight messages to be finished, in milliseconds.
     */
    public synchronized boolean stopSubscribers(int waitMillis) {
        checkArgument(waitMillis > 0);
        for (Subscriber subscriber : subscribers) {
            subscriber.stop();
        }
        synchronized (subConMonitor) {
            if (!subConnections.isEmpty()) { //don't loop until empty, try once and if we get interrupted stop right away
                log.info("waiting for subscribers to finish in-flight messages");
                try {
                    subConMonitor.wait(waitMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        boolean isClean = subConnections.isEmpty();
        for (SubConnection subCon : subConnections) {
            subCon.close();
        }
        return isClean;
    }

    public synchronized void setExecutor(ExecutorService exec) {
        checkNotNull(exec);
        checkState(this.handlerExecutor == null, "executor can only be set once, must be set before subscribing");
        this.handlerExecutor = exec;
    }

    public synchronized ExecutorService getExecutor() {
        if (handlerExecutor == null) {
            handlerExecutor = Executors.newFixedThreadPool(6, Util.threadFactory("nsq-sub"));
        }
        return handlerExecutor;
    }

    public synchronized SSLSocketFactory getSSLSocketFactory() {
        return sslSocketFactory;
    }

    public synchronized void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    public synchronized byte[] getAuthSecret() {
        return authSecret;
    }

    public synchronized void setAuthSecret(byte[] authSecret) {
        this.authSecret = authSecret;
    }

    public synchronized void setAuthSecret(String authSecret) {
        this.authSecret = authSecret.getBytes();
    }

    //--------------------------
    // package private

    void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    void addSubConnection(SubConnection subCon) {
        subConnections.add(subCon);
    }

    ScheduledExecutorService getScheduledExecutor() {
        return scheduledExecutor;
    }

    ScheduledFuture scheduleAtFixedRate(final Runnable runnable, int initialDelay, int period, boolean jitter) {
        if (jitter) {
            initialDelay = (int) (initialDelay * 0.1 + Math.random() * initialDelay * 0.9);
        }
        return scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } catch (Throwable t) {
                    log.error("task error", t);
                }
            }
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    void schedule(final Runnable runnable, int delay) {
        scheduledExecutor.schedule(new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } catch (Throwable t) {
                    log.error("task error", t);
                }
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    void connectionClosed(SubConnection closedCon) {
        synchronized (subConMonitor) {
            subConnections.remove(closedCon);
            if (subConnections.isEmpty()) {
                subConMonitor.notifyAll();
            }
        }
    }

    Gson getGson() {
        return gson;
    }

}
