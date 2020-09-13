package com.jutem.cases.leetcode;

/**
 * @author dawei.chen02@ele.me
 * @create 2020-09-13
 */
public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode head = null;
        ListNode pre = null;
        int addition = 0;
        while (true) {
            Pair pair = singleCal(l1, l2, addition);
            if(pair == null) {
                break;
            }
            if(head == null) {
                head = pair.node;
            }
            if(pre != null) {
                pre.next = pair.node;
            }
            pre = pair.node;
            addition = pair.addition;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        return head;
    }

    private Pair singleCal(ListNode l1, ListNode l2, int addition) {
        if(l1 == null && l2 == null && addition == 0) {
            return null;
        }

        int l1Val = l1 == null ? 0 : l1.val;
        int l2Val = l2 == null ? 0 : l2.val;

        int val = l1Val + l2Val + addition;
        ListNode node = new ListNode(val % 10);
        return new Pair(node, val > 9 ? 1 : 0);
    }

    public class Pair {
        ListNode node;
        int addition;

        Pair(ListNode node, int addition) {
            this.node = node;
            this.addition = addition;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
