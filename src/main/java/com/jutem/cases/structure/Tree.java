package com.jutem.cases.structure;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 二叉树基本遍历
 */
public class Tree {

    // Definition for a Node.
    public class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

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
        if (treeNode == null) {
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
        if (treeNode == null) {
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
        if (treeNode == null) {
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
        if (root == null) {
            return Collections.EMPTY_LIST;
        }
        List<List<Integer>> result = new ArrayList<>();
        List<TreeNode> rootList = new ArrayList<>();
        rootList.add(root);
        levelorderRecursion(result, rootList);
        return result;
    }

    private static void levelorderRecursion(List<List<Integer>> result, List<TreeNode> treeNodes) {
        if (treeNodes == null || treeNodes.size() == 0) {
            return;
        }
        List<Integer> levelList = treeNodes.stream().map(n -> n.val).collect(Collectors.toList());
        result.add(levelList);

        List<TreeNode> childNodes = new ArrayList<>();
        treeNodes.forEach(n -> {
            if (n.left != null) {
                childNodes.add(n.left);
            }
            if (n.right != null) {
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
        if (root == null) {
            return;
        }

        depth++;
        if (isLeaf(root)) {
            MAX_DEPTH = Math.max(depth, MAX_DEPTH);
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
        if (root == null) {
            return 0;
        }
        return Math.max(postorderMaxDepth(root.left), postorderMaxDepth(root.right)) + 1;
    }

    /**
     * 镜像对称,层序遍历
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        List<TreeNode> levelNodes = new ArrayList<>();
        levelNodes.add(root);
        return symmetricRecursion(levelNodes);
    }

    public boolean symmetricRecursion(List<TreeNode> sortedNodes) {
        if (sortedNodes == null || sortedNodes.size() == 0) {
            return true;
        }

        for (int left = 0, right = sortedNodes.size() - 1; left < right; left++, right--) {
            TreeNode leftNode = sortedNodes.get(left);
            TreeNode rightNode = sortedNodes.get(right);
            if (!nodeEquals(leftNode, rightNode)) {
                return false;
            }
        }

        List<TreeNode> nextLevel = new ArrayList<>();
        sortedNodes.forEach(n -> {
            if (n == null) {
                return;
            }
            nextLevel.add(n.left);
            nextLevel.add(n.right);
        });

        return symmetricRecursion(nextLevel);
    }

    private boolean nodeEquals(TreeNode n1, TreeNode n2) {
        if (n1 == null && n2 == null) {
            return true;
        }

        if (n1 != null && n2 != null && n1.val == n2.val) {
            return true;
        }

        return false;
    }

    /**
     * 镜像，更优雅的做法
     */
    public boolean isSymmetricNice(TreeNode root) {
        return check(root, root);
    }

    public boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }

    /**
     * 路径总和
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        return hasPathSumRecursion(root, 0, sum);
    }

    private boolean hasPathSumRecursion(TreeNode node, int now, int sum) {
        if (node == null) {
            return false;
        }
        now += node.val;
        if (isLeaf(node)) {
            return now == sum;
        }

        return hasPathSumRecursion(node.left, now, sum) || hasPathSumRecursion(node.right, now, sum);
    }


    /**
     * 从中序与后序遍历序列构造二叉树
     */
    private Integer postIndex = 0;
    private Map<Integer, Integer> indexMap = new HashMap<>();

    public TreeNode buildTreeInPost(int[] inorder, int[] postorder) {
        postIndex = postorder.length - 1;
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        return buildTreeInPostRecursion(0, inorder.length - 1, postorder);
    }

    private TreeNode buildTreeInPostRecursion(int leftIndex, int rightIndex, int[] postorder) {
        //使用中序遍历判断是否还有子树
        if (leftIndex > rightIndex) {
            return null;
        }

        TreeNode node = new TreeNode(postorder[postIndex]);
        postIndex--;

        Integer inIndex = indexMap.get(node.val);

        node.right = buildTreeInPostRecursion(inIndex + 1, rightIndex, postorder);
        node.left = buildTreeInPostRecursion(leftIndex, inIndex - 1, postorder);
        return node;
    }

    /**
     * 从中序与前序遍历序列构造二叉树
     */
    private Integer preIndex = 0;

    public TreeNode buildTreeInPre(int[] preorder, int[] inorder) {
        preIndex = 0;
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        return buildTreeInPreRecursion(0, inorder.length - 1, preorder);
    }

    private TreeNode buildTreeInPreRecursion(int leftIndex, int rightIndex, int[] preorder) {
        if (leftIndex > rightIndex) {
            return null;
        }

        TreeNode node = new TreeNode(preorder[preIndex]);
        preIndex++;

        Integer inIndex = indexMap.get(node.val);

        node.left = buildTreeInPreRecursion(leftIndex, inIndex - 1, preorder);
        node.right = buildTreeInPreRecursion(inIndex + 1, rightIndex, preorder);
        return node;
    }

    /**
     * 填充每个节点的下一个右侧节点指针
     * 完美树,非完美树
     */
    public Node connect(Node root) {
        if(root == null) {
            return null;
        }
        List<Node> nodes = new ArrayList<>();
        nodes.add(root);
        connectRecursion(nodes);
        return root;
    }

    public void connectRecursion(List<Node> nodes) {
        if(nodes == null || nodes.size() == 0) {
            return;
        }

        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).next = nodes.get(i + 1);
        }

        List<Node> nextLevel = new ArrayList<>();
        nodes.forEach(n -> {
            if(n.left != null) {
                nextLevel.add(n.left);
            }
            if(n.right != null) {
                nextLevel.add(n.right);
            }
        });
        connectRecursion(nextLevel);
    }

    /**
     * 最近公共祖先
     */
    private TreeNode lowestCommonAncestorNode;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        lowestCommonAncestorNode = null;
        if(lowestCommonAncestorRecursion(root, p, q) != 1) {
            return null;
        } else {
            return lowestCommonAncestorNode;
        }
    }

    public int lowestCommonAncestorRecursion(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || lowestCommonAncestorNode != null) {
            return 0;
        }

        int left = lowestCommonAncestorRecursion(root.left, p, q);
        int right = lowestCommonAncestorRecursion(root.right, p, q);

        int res = cal(left, right, p.val == root.val, q.val == root.val);
        if(res >= 2) {
            lowestCommonAncestorNode = root;
            return 1;
        }

        return res == 1 ? 1 : 0;
    }

    private int cal(int left, int right, boolean p, boolean q) {
        return left + right + (p ? 1 : 0) + (q ? 1 : 0);
    }

    //序列化树，反序列化树
    // Encodes a tree to a single string.
    private static final char LEFT = 'L';
    private static final char RIGHT = 'R';
    private static final char NULL = '#';
    private static final char SPLIT = '_';
    private static final StringBuilder EMPTY = new StringBuilder();

    private class IndexBook {
        int index = 0;
        public void up() {
            index ++;
        };
    }

    public static void main(String[] args) {
        Tree tree = new Tree();
        String str = "#R1_##2";
        TreeNode root = tree.deserialize(str);
        String serialStr = tree.serialize(root);
        System.out.printf("serial str = " + serialStr);
    }

    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeRecursion(root, sb).toString();
        if(sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public StringBuilder serializeRecursion(TreeNode node, StringBuilder sb) {
        if(node == null) {
            return EMPTY;
        }
        sb.append(node.left == null ? NULL : LEFT).append(node.right == null ? NULL : RIGHT).append(node.val).append(SPLIT);
        serializeRecursion(node.left, sb);
        serializeRecursion(node.right, sb);
        return sb;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.length() == 0) {
            return null;
        }
        String[] arr = data.split(String.valueOf(SPLIT));
        IndexBook bk = new IndexBook();
        return deserializeRecursion(bk, arr);
    }

    public TreeNode deserializeRecursion(IndexBook index, String[] arr) {
        String str = arr[index.index];
        TreeNode node = new TreeNode(Integer.valueOf(str.substring(2)));
        if(str.charAt(0) == NULL && str.charAt(1) == NULL) {
            return node;
        }

        if(str.charAt(0) == LEFT) {
            index.up();
            node.left = deserializeRecursion(index, arr);
        }

        if(str.charAt(1) == RIGHT) {
            index.up();
            node.right = deserializeRecursion(index, arr);
        }

        return node;
    }


}
