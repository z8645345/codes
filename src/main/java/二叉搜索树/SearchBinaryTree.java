package 二叉搜索树;

public class SearchBinaryTree<E> {

    private Node<E> root;
    private int size;

    /**
     * 添加节点
     * @param element
     */
    public void add(E element) {
        elementNotNullCheck(element);
    }

    private void elementNotNullCheck(E e) {
        if (e == null) {
            throw new IllegalArgumentException("element not be null ……");
        }
        if (root == null) {
            root = new Node<>(e, null);
            return;
        }
        Node<E> node = root;
        Node<E> parent = null;
        int com = 0;
        while (node != null) {
            com = compar(e, node.element);
            parent = node;
            if (com > 0) {
                node = node.right;
            } else if (com < 0) {
                node = node.left;
            } else {
                return;
            }
        }
        if (com > 0) {
            parent.right = new Node<>(e, parent);
        } else if (com < 0) {
            parent.left = new Node<>(e, parent);
        }
    }

    private int compar(E e1, E e2) {
        return 0;
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
}
