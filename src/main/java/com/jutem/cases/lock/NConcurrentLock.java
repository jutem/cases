package com.jutem.cases.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 只允许n个线程获取资源的锁
 */
public class NConcurrentLock implements Lock{

    private Sync sync;

    public NConcurrentLock(int n) {
        if(n < 0)
            throw new IllegalArgumentException("初始化指定线程数必须大于0");
        this.sync = new NonFairSync(n);
    }

    /**
     * 继承自AQS的同步器
     */
    abstract static class Sync extends AbstractQueuedSynchronizer {

        //能同时获取资源的线程数
        private int n;

        //保存重入数和线程的map
        private Map<Long, Integer> threadHolder = new ConcurrentHashMap<>();

        public Sync(int n) {
            this.n = n;
        }

        @Override
        protected boolean tryAcquire(int acquires) {
            int c = getState();
            Thread current = Thread.currentThread();
            //如果当前没有线程获取锁
            if(c == 0) {
                if(compareAndSetState(c, c + acquires)) {
                    threadHolder.put(current.getId(), acquires);
                    return true;
                }
            }

            if(c < n) {
                //重入
                if(threadHolder.containsKey(current.getId())) {
                    int count = threadHolder.get(current.getId()) + 1;
                    threadHolder.put(current.getId(), count);
                    return true;
                } else {
                    if(compareAndSetState(c, c + acquires)) {
                        threadHolder.put(current.getId(), acquires);
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        protected boolean tryRelease(int acquires) {
            Thread current = Thread.currentThread();
            if(!threadHolder.containsKey(current.getId()))
                throw new Error("线程未获取锁");

            int count = threadHolder.get(current.getId()) - 1;
            if(count == 0) {
                for(;;) {
                    int c = getState();
                    if(compareAndSetState(c, c - acquires))
                        return true;
                }
            } else {
                threadHolder.put(current.getId(), count);
                return false;
            }
        }
    }

    static final class NonFairSync extends Sync {
        public NonFairSync(int n) {
            super(n);
        }
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}
