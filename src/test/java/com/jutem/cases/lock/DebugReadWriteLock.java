package com.jutem.cases.lock;

import com.jutem.cases.base.BaseTest;
import org.junit.Test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DebugReadWriteLock extends BaseTest {

    /**
     * 读锁之后获取写锁则会挂起
     */
    @Test
    public void rwrLock() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        lock.writeLock().lock();
        lock.readLock().lock();
    }

    /**
     * 写锁之后再获取读锁可行
     */
    @Test
    public void wrwLock() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        lock.readLock().lock();
        lock.writeLock().lock();
    }

    @Test
    public void wrLockSameQueue() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();

        new Thread(() -> {
            lock.writeLock().lock();
        }, "t1").start();

        new Thread(() -> {
            lock.readLock().lock();
        }, "t2").start();

        new Thread(() -> {
            lock.readLock().lock();
        }, "t3").start();

        System.out.println("stop");
    }

    @Test
    public void wUnLock() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        lock.writeLock().unlock();
    }

    @Test
    public void rUnLock() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        lock.readLock().unlock();
    }

    @Test
    public void wRelease() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();

        new Thread(() -> {
            lock.writeLock().lock();
        }, "t1").start();

        new Thread(() -> {
            lock.readLock().lock();
        }, "t2").start();

        new Thread(() -> {
            lock.readLock().lock();
        }, "t3").start();

        lock.writeLock().unlock();
        System.out.println("stop");

    }

    @Test
    public void rRelease() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();

        new Thread(() -> {
            lock.writeLock().lock();
        }, "t1").start();

        new Thread(() -> {
            lock.readLock().lock();
        }, "t2").start();

        new Thread(() -> {
            lock.readLock().lock();
        }, "t3").start();

        lock.readLock().unlock();
        System.out.println("stop");
    }

    @Test
    public void multiRUnpark() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();

        new Thread(() -> {
            lock.readLock().lock();
        }, "t1").start();

        new Thread(() -> {
            lock.readLock().lock();
        }, "t2").start();

        new Thread(() -> {
            lock.readLock().lock();
        }, "t3").start();

        lock.writeLock().unlock();
        System.out.println("stop");
    }
}

