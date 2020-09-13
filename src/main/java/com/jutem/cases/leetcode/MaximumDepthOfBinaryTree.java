package com.jutem.cases.leetcode;

/**
 * @author dawei.chen02@ele.me
 * @create 2020-09-13
 */
public class MaximumDepthOfBinaryTree {
    public int maxDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }

        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
