package com.jutem.cases.lock;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @link http://ifeve.com/java_lock_see2/
 * CLH = Craig, Landin, and Hagersten
 */
public class CLHLock {
    public static class CLHNode {
        private volatile boolean isLocked = true;
    }

    private volatile CLHNode tail;

    private static final ThreadLocal<CLHNode> LOCAL = new ThreadLocal<>();

    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER =
            AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");

    public void lock() {

        CyclicBarrier barrier = new CyclicBarrier(2);
        barrier.reset();



        CLHNode node = new CLHNode();
        LOCAL.set(node);
        CLHNode preNode = UPDATER.getAndSet(this, node);
        if (preNode != null) {
            while (preNode.isLocked) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            preNode = null;
            LOCAL.set(node);
        }
    }

    public void unlock() {
        CLHNode node = LOCAL.get();
        if(!UPDATER.compareAndSet(this, node, null))
            node.isLocked = false;
        node = null;
    }
}
