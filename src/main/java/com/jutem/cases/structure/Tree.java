package com.jutem.cases.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 层序遍历
     */
    public static List<List<Integer>> levelorderTraversal(TreeNode root) {
        if(root == null) {
            return Collections.EMPTY_LIST;
        }
        List<List<Integer>> result = new ArrayList<>();
        List<TreeNode> rootList = new ArrayList<>();
        rootList.add(root);
        levelorderRecursion(result, rootList);
        return result;
    }

    private static void levelorderRecursion(List<List<Integer>> result, List<TreeNode> treeNodes) {
        if(treeNodes == null || treeNodes.size() == 0) {
            return;
        }
        List<Integer> levelList = treeNodes.stream().map(n -> n.val).collect(Collectors.toList());
        result.add(levelList);

        List<TreeNode> childNodes = new ArrayList<>();
        treeNodes.forEach(n -> {
            if(n.left != null) {
                childNodes.add(n.left);
            }
            if(n.right != null) {
                childNodes.add(n.right);
            }
        });

        levelorderRecursion(result, childNodes);
    }

    /**
     * 深度计算,前序遍历
     */
    private static int MAX_DEPTH = 0;
    public int preorderMaxDepth(TreeNode root) {
        depthRecursion(root, 0);
        Integer res = MAX_DEPTH;
        MAX_DEPTH = 0;
        return res;
    }

    private void depthRecursion(TreeNode root, int depth) {
        if(root == null) {
            return;
        }

        depth ++;
        if(isLeaf(root)) {
            MAX_DEPTH = Math.max(depth , MAX_DEPTH);
            return;
        }

        depthRecursion(root.left, depth);
        depthRecursion(root.right, depth);
    }

    private boolean isLeaf(TreeNode root) {
        return root.right == null && root.left == null;
    }

    /**
     * 深度计算, 后续遍历
     */
    public int postorderMaxDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return Math.max(postorderMaxDepth(root.left), postorderMaxDepth(root.right)) + 1;
    }

    /**
     * 镜像对称,层序遍历
     */
    public boolean isSymmetric(TreeNode root) {
        if(root == null) {
            return true;
        }

        List<TreeNode> levelNodes = new ArrayList<>();
        levelNodes.add(root);
        return symmetricRecursion(levelNodes);
    }

    public boolean symmetricRecursion(List<TreeNode> sortedNodes) {
        if(sortedNodes == null || sortedNodes.size() == 0) {
            return true;
        }

        for (int left = 0, right = sortedNodes.size() - 1; left < right ; left ++ , right --) {
            TreeNode leftNode = sortedNodes.get(left);
            TreeNode rightNode = sortedNodes.get(right);
            if(!nodeEquals(leftNode, rightNode)) {
                return false;
            }
        }

        List<TreeNode> nextLevel = new ArrayList<>();
        sortedNodes.forEach(n -> {
            if(n == null) {
                return;
            }
            nextLevel.add(n.left);
            nextLevel.add(n.right);
        });

        return symmetricRecursion(nextLevel);
    }

    private boolean nodeEquals(TreeNode n1, TreeNode n2) {
        if(n1 == null && n2 == null) {
            return true;
        }

        if(n1 != null && n2 != null && n1.val == n2.val) {
            return true;
        }

        return false;
    }

}
