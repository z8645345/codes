package list;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapLinkedHashMap {
    public static void main(String[] args) {
        Map<String, String> map = new LinkedHashMap<>(10000);
        map.put("k1", "张三");
    }
}
