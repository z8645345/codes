package list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyArrayList {
    public static void main(String[] args) {
        List<String> l = new ArrayList<>();
        l.add("zhangsan");
        l.add("lisi");
        l.add("wangwu");
        l.add("zhaoliu");
        l.add("xiaoqi");
        Iterator<String> i = l.iterator();
        while (i.hasNext()) {
            String s = i.next();
            if (s.startsWith("li")) {
                i.remove();
            }
        }
    }
}
