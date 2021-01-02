package 二叉搜索树;

import 二叉搜索树.printer.BinaryTreeInfo;

import java.util.Comparator;

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
    public void add(E element) {
        elementNotNullCheck(element);
    }

    private E elementNotNullCheck(E e) {
        if (e == null) {
            throw new IllegalArgumentException("element not be null ……");
        }
        if (root == null) {
            root = new Node<>(e, null);
            return null;
        }
        Node<E> node = root;
        Node<E> parent = null;
        int com = 0;
        while (node != null) {
            com = compare(e, node.element);
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
            parent.right = new Node<>(e, parent);
            return null;
        }
        if (com < 0) {
            parent.left = new Node<>(e, parent);
            return null;
        } else {
            E old = node.element;
            node.element = e;
            return old;
        }
    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable) e1).compareTo(e2);
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
