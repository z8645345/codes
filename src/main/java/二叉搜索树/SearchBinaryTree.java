package 二叉搜索树;

import java.util.LinkedList;

public class SearchBinaryTree {

    private Node root;

    /**
     * 添加节点
     * @param item
     */
    public Integer add(Integer item) {
        if (root == null) {
            root = new Node(item);
            return null;
        }
        Node parent = root;
        while (true) {
            if (item > parent.item) {
                // 在左节点
                if (parent.left == null) {
                    parent.left = new Node(item);
                    return null;
                }
                parent = parent.left;
            } else if(item < parent.item) {
                // 在由节点
                if (parent.right == null) {
                    parent.right = new Node(item);
                    return null;
                }
                parent = parent.right;
            } else {
                // 相等
                Integer old = parent.item;
                parent.item = item;
                return item;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node parent = root;
        while (parent != null) {
            sb.append(parent.item);
            if (parent.)
        }
        return "SearchBinaryTree{" +
                "root=" + root +
                '}';
    }

    private static class Node {
        Integer item;
        Node parent;
        Node left;
        Node right;

        public Node(Integer item) {
            this(item, null, null);
        }

        public Node(Integer item, Node left, Node right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }
}
