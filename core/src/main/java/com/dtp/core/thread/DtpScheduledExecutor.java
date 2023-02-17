package com.dtp.core.thread;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Support ScheduledThreadExecutor.
 *
 * @author windsearcher
 **/
public class DtpScheduledExecutor extends DtpExecutor implements ScheduledExecutorService {

    private final ScheduledThreadPoolExecutor delegate;

    public DtpScheduledExecutor(int corePoolSize,
                                int maximumPoolSize,
                                long keepAliveTime,
                                TimeUnit unit,
                                BlockingQueue<Runnable> workQueue,
                                ThreadFactory threadFactory,
                                RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        delegate = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, handler);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return delegate.schedule(command, delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return delegate.schedule(callable, delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return delegate.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return delegate.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    @Override
    public void execute(Runnable command) {
        schedule(command, 0, NANOSECONDS);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return schedule(task, 0, NANOSECONDS);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return schedule(Executors.callable(task, result), 0, NANOSECONDS);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return schedule(task, 0, NANOSECONDS);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return delegate.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return delegate.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return delegate.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return delegate.invokeAny(tasks, timeout, unit);
    }

    @Override
    public void shutdown() {
        delegate.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return delegate.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return delegate.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return delegate.awaitTermination(timeout, unit);
    }

    @Override
    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        delegate.setRejectedExecutionHandler(handler);
    }

    @Override
    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return delegate.getRejectedExecutionHandler();
    }

    @Override
    public void setCorePoolSize(int corePoolSize) {
        delegate.setCorePoolSize(corePoolSize);
    }

    @Override
    public int getCorePoolSize() {
        return delegate.getCorePoolSize();
    }

    @Override
    public void setMaximumPoolSize(int maximumPoolSize) {
        delegate.setMaximumPoolSize(maximumPoolSize);
    }

    @Override
    public int getMaximumPoolSize() {
        return delegate.getMaximumPoolSize();
    }

    @Override
    public String getQueueName() {
        return delegate.getQueue().getClass().getSimpleName();
    }

    @Override
    public int getQueueCapacity() {
        int capacity = delegate.getQueue().size() + delegate.getQueue().remainingCapacity();
        return capacity < 0 ? Integer.MAX_VALUE : capacity;
    }

    @Override
    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        delegate.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        delegate.setThreadFactory(threadFactory);
    }

    @Override
    public ThreadFactory getThreadFactory() {
        return delegate.getThreadFactory();
    }

    @Override
    public boolean prestartCoreThread() {
        return delegate.prestartCoreThread();
    }

    @Override
    public void setKeepAliveTime(long time, TimeUnit unit) {
        delegate.setKeepAliveTime(time, unit);
    }

    @Override
    public long getKeepAliveTime(TimeUnit unit) {
        return delegate.getKeepAliveTime(unit);
    }

    @Override
    public BlockingQueue<Runnable> getQueue() {
        return delegate.getQueue();
    }

    @Override
    public int prestartAllCoreThreads() {
        return delegate.prestartAllCoreThreads();
    }

    @Override
    public boolean allowsCoreThreadTimeOut() {
        return delegate.allowsCoreThreadTimeOut();
    }

    @Override
    public void allowCoreThreadTimeOut(boolean value) {
        delegate.allowCoreThreadTimeOut(value);
    }

    @Override
    public int getPoolSize() {
        return delegate.getPoolSize();
    }

    @Override
    public int getActiveCount() {
        return delegate.getActiveCount();
    }

    @Override
    public int getLargestPoolSize() {
        return delegate.getLargestPoolSize();
    }

    @Override
    public long getTaskCount() {
        return delegate.getTaskCount();
    }

    @Override
    public long getCompletedTaskCount() {
        return delegate.getCompletedTaskCount();
    }
}
