package com.jutem.cases.lock;

import org.junit.Test;

public class NConcurrentLockTest {
    @Test
    public void CommonSingleLockTest() {
        NConcurrentLock lock = new NConcurrentLock(1);
        lock.lock();
        lock.unlock();
    }

    @Test
    public void lockCompetition() {
        NConcurrentLock lock = new NConcurrentLock(3);
        new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "t1").start();
        new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "t2").start();
        new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "t3").start();
        new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "t4").start();

        lock.lock();
        lock.unlock();
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
