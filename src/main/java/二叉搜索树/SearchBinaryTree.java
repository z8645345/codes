package 二叉搜索树;

import 二叉搜索树.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.Stack;

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
    public void preorderTraversal() {
        Node<E> node = root;
        Node<E> parent = node;
        if (node != null) {
            Stack<Node<E>> stack = new Stack<>();
            System.out.println(node.element);
            Node<E> leftNode = node.left;
            if (node.right != null) {
                stack.add(node.right);
            }
            while (leftNode != null) {
                System.out.println(leftNode.element);
                if (leftNode.right != null) {
                    stack.add(leftNode.right);
                }
                leftNode = leftNode.left;
                if (leftNode == null && !stack.empty()) {
                    leftNode = stack.pop();

                }
            }
        }
//        preorderTraversal(root);
    }

    /**
     * 前序遍历-递归方式
     * @param node
     */
    private void preorderTraversal(Node<E> node) {
        if (node == null) return;
        System.out.println(node.element);
        preorderTraversal(node.left);
        preorderTraversal(node.right);
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
