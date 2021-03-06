package 二叉搜索树;

import 二叉搜索树.printer.BinaryTreeInfo;

import java.util.*;

public class SearchBinaryTree<E> implements BinaryTreeInfo {

    private Node<E> root;
    private int size;

    private Comparator<E> comparator;

    public SearchBinaryTree() {
    }

    public SearchBinaryTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 添加节点
     * @param element
     */
    public E add(E element) {
        elementNotNullCheck(element);
        if (root == null) {
            root = new Node<>(element, null);
            return null;
        }
        Node<E> node = root;
        Node<E> parent = null;
        int com = 0;
        while (node != null) {
            com = compare(element, node.element);
            parent = node;
            if (com > 0) {
                node = node.right;
            } else if (com < 0) {
                node = node.left;
            } else {
                break;
            }
        }
        if (com > 0) {
            parent.right = new Node<>(element, parent);
            return null;
        }
        if (com < 0) {
            parent.left = new Node<>(element, parent);
            return null;
        } else {
            E old = node.element;
            node.element = element;
            return old;
        }
    }

    private void elementNotNullCheck(E e) {
        if (e == null) {
            throw new IllegalArgumentException("element not be null ……");
        }
    }

    /**
     * 比较大小
     * @param e1
     * @param e2
     * @return 返回1 e1大,返回-1 e2大,返回0 相等
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable) e1).compareTo(e2);
    }

    /**
     * 前序遍历
     */
    public void preorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        Node<E> node = root;
        Stack<Node<E>> stack = new Stack<>();
        while (node != null) {
            if (visitor.visitor(node.element)) return;
            if (node.right != null) {
                stack.add(node.right);
            }
            node = node.left;
            if (node == null && !stack.empty()) {
                node = stack.pop();
            }
        }
//        preorderTraversal(root, visitor);
    }

    /**
     * 前序遍历-递归方式
     * @param node
     */
    private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (visitor.visitor(node.element)) return;
        preorderTraversal(node.left, visitor);
        preorderTraversal(node.right, visitor);
    }

    /**
     * 中序遍历
     */
    public void inorderTraversal(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        inorderTraversal(root, visitor);
    }

    /**
     * 中序遍历-递归方式
     * @param node
     */
    private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (visitor.stop) return;
        inorderTraversal(node.left, visitor);
        visitor.stop = visitor.visitor(node.element);
        if (visitor.stop) return;
        inorderTraversal(node.right, visitor);
    }

    /**
     * 后续遍历
     */
    public void postorderTraversal(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        postorderTraversal(root, visitor);
    }

    /**
     * 后续遍历-递归方式
     * @param node
     */
    private void postorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (visitor.stop) return;
        postorderTraversal(node.left, visitor);
        postorderTraversal(node.right, visitor);
        visitor.stop = visitor.visitor(node.element);
    }

    /**
     * 层续遍历
     */
    public void levelOrderTraversal(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visitor(node.element)) return;
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }

    /**
     * 判断树的高度
     * @return
     */
    public int height() {
        if (root == null) return 0;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        int height = 0;
        int levelNum = 1;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelNum --;
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
            if (levelNum == 0) {
                levelNum = queue.size();
                height ++;
            }
        }
        return height;
    }

    /**
     * 判断是否完全二叉树
     * @return
     */
    public boolean isComplete() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;
            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) { // left == null && right != null
                return false;
            }
            if (node.right != null) {
                queue.offer(node.right);
            } else { // (left == null && right == null) || (left != null && right == null)
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 递归判断树的高度
     */
    public int height2() {
        return height(root);
    }

    /**
     * 递归判断树的高度
     * @param node
     * @return
     */
    public int height(Node<E> node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    /**
     * 二叉树翻转
     * @return
     */
    public void fip() {
        fip(root);
    }

    public void fip(Node<E> root) {
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            Node<E> temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }

    public abstract static class Visitor<E> {
        private boolean stop;

        abstract boolean visitor(E e);
    }

    private static class Node<E> {
        E element;
        Node<E> parent;
        Node<E> left;
        Node<E> right;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 是否叶子节点
         * @return
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 是否有两个子节点
         * @return
         */
        public boolean is2Children() {
            return left != null && right != null;
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node) node).right;
    }

    @Override
    public Object string(Object node) {
        Node n = ((Node) node);
        String parent = "null";
        if (n.parent != null) {
            parent = n.parent.element.toString();
        }
        return ((Node) node).element + "_P(" + parent + ")";
    }
}
