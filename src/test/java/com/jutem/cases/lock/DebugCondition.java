package com.jutem.cases.lock;

import com.jutem.cases.base.BaseTest;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DebugCondition extends BaseTest {
    @Test
    public void RCondition() throws BrokenBarrierException, InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            try {
                lock.lock();
                condition.await();
//                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            lock.lock();
        }, "t2").start();
        lock.lock();
        condition.signalAll();
        lock.unlock();
    }

    /**
     * 读锁不支持条件变量
     */
    @Test
    public void RWCondition() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Condition condition = lock.writeLock().newCondition();
        new Thread(() -> {
            try {
                lock.writeLock().lock();
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        condition.signal();
    }


}
