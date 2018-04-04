package com.jutem.cases.lock;

import com.jutem.cases.base.BaseTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CLHLockTest extends BaseTest{
    @Test
    public void lock() throws InterruptedException {
        CLHLock clhLock = new CLHLock();
        clhLock.lock();
        new Thread(() -> clhLock.lock()).start();
        Thread.sleep(50);
        clhLock.unlock();
    }

    @Test
    public void test() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list = list.stream().filter(l -> true).collect(Collectors.toList());
        System.out.println(list.size());
    }

}
