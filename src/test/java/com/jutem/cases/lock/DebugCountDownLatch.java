package com.jutem.cases.lock;

import com.jutem.cases.base.BaseTest;
import org.junit.Test;
import scala.collection.parallel.ParIterableLike;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DebugCountDownLatch extends BaseTest {

    @Test
    public void countDown() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(() -> {
            try {
                countDownLatch.await();
                System.out.println("t1 over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                countDownLatch.await();
                System.out.println("t2 over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.countDown();
    }
}

