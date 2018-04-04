package com.jutem.cases.lock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @link http://ifeve.com/java_lock_see2/
 */
public class MCSLock {
    public static class MCSNode {
        volatile MCSNode next;
        volatile boolean isLocked = true;
    }

    private volatile MCSNode queue;

    private static final ThreadLocal<MCSNode> NODE = new ThreadLocal<>();

    private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> UPDATER =
            AtomicReferenceFieldUpdater.newUpdater(MCSLock.class, MCSNode.class, "queue");

    public void lock() {
        MCSNode currentNode = new MCSNode();
        NODE.set(currentNode);
        MCSNode preNode = UPDATER.getAndSet(this, currentNode);
        if(preNode != null) {
            preNode.next = currentNode;
            while (currentNode.isLocked) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void unlock() {
        MCSNode currentNode = NODE.get();
        if (currentNode.next == null) {
            if (UPDATER.compareAndSet(this, currentNode, null)) {
                return;
            } else {
                while (currentNode.next == null) {
                }
            }
        }
        currentNode.next.isLocked = false;
        currentNode.next = null;
    }
}
