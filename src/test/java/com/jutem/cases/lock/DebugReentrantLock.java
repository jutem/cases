package com.jutem.cases.lock;

import com.jutem.cases.base.BaseTest;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

public class DebugReentrantLock extends BaseTest{
    @Test
    public void lockChain() {
        ReentrantLock lock = new ReentrantLock();
        lockChain(lock);
    }

    private void lockChain(ReentrantLock lock) {
        lock.lock();
        lockChain(lock);
    }

    @Test
    public void lockRelease() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }

    @Test
    public void lockCompetition() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            while (true)
                sleep(1000);
        }).start();

        sleep(50);
        lock.lock();
    }

    @Test
    public void lockInterrupt() {
        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            while (true)
                sleep(1000);
        }, "t1").start();
        sleep(50);
        Thread t2 = new Thread(() -> {
            lock.lock();
            System.out.println("i'm t2");
        }, "t2");
        sleep(50);
        Thread t3 = new Thread(() -> {
            lock.lock();
            System.out.println("i'm t3");
        }, "t3");
        sleep(50);
        Thread t4 = new Thread(() -> {
            lock.lock();
            System.out.println("i'm t4");
        }, "t4");
        t2.start();
        t3.start();
        t4.start();

        lock.lock();
    }

    @Test
    public void lockInMiddle() {
        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "t1").start();
        sleep(50);
        Thread t2 = new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "t2");
        sleep(50);
        Thread t3 = new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "t3");
        sleep(50);
        Thread t4 = new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "t4");
        t2.start();
        t3.start();
        t4.start();

        t3.interrupt();
    }


    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
