package list;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList {
    public static void main(String[] args) {
        List<String> l = new LinkedList<>();
        l.add("zhangsan");
        l.set(5, "zhangsan");
        l.remove(5);
    }
}
