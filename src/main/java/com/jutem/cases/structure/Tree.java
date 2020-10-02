package com.jutem.cases.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二叉树基本遍历
 */
public class Tree {

    //Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 前序遍历
     */
    public static List<Integer> preorderTraversal(TreeNode treeNode) {
        List<Integer> result = new ArrayList<>();
        preorderRecursion(result, treeNode);
        return result;
    }

    private static void preorderRecursion(List<Integer> result, TreeNode treeNode) {
        if(treeNode == null) {
            return;
        }
        result.add(treeNode.val);
        preorderRecursion(result, treeNode.left);
        preorderRecursion(result, treeNode.right);
    }

    /**
     * 中序遍历
     */
    public static List<Integer> inorderTraversal(TreeNode treeNode) {
        List<Integer> result = new ArrayList<>();
        inorderRecursion(result, treeNode);
        return result;
    }

    private static void inorderRecursion(List<Integer> result, TreeNode treeNode) {
        if(treeNode == null) {
            return;
        }
        inorderRecursion(result, treeNode.left);
        result.add(treeNode.val);
        inorderRecursion(result, treeNode.right);
    }


    /**
     * 后序遍历
     */
    public static List<Integer> postorderTraversal(TreeNode treeNode) {
        List<Integer> result = new ArrayList<>();
        postorderRecursion(result, treeNode);
        return result;
    }

    private static void postorderRecursion(List<Integer> result, TreeNode treeNode) {
        if(treeNode == null) {
            return;
        }
        postorderRecursion(result, treeNode.left);
        postorderRecursion(result, treeNode.right);
        result.add(treeNode.val);
    }



}
