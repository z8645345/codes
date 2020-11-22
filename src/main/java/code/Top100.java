package code;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Top100 {
    public static Map<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
        //构建大小为5的线程池
            ExecutorService service = Executors.newFixedThreadPool(5);
            //多个文本文件的根目录
            String path = "testTop100";
            File[] files = new File(path).listFiles();
            for (File file : files) {
            //遍历文件，使用子线程读取
                MyFileUtil f = new MyFileUtil();
                f.filrname = file.getPath();
                service.execute(f);
            }
            service.shutdown();
            while (true) {
            //等待所有线程执行完毕
                if (service.isTerminated()) {
                    getTop100();//获取频率最高的100个单词
                    break;
                }
                Thread.sleep(1000);
            }
            // 关闭线程池
            service.shutdown();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    //获取频率最高的100个单词
    private static void getTop100() {
        Words[] words = null;
        //单词个数少于100直接排序输出
        if(hashMap.size()<=100){
            words = new Words[hashMap.size()];
            int i = 0;
            for(Map.Entry<String, Integer> entry:hashMap.entrySet()){
                words[i++] = new Words(entry.getKey(),entry.getValue());
            }
            Arrays.sort(words);
            for(int k =words.length-1;k>0;k--){
                System.out.println(words[k].word+" "+words[k].counts);           
            }
        }else{
        //否则，构建最小堆
            words = new Words[101];
            int i = 1;
            for(Map.Entry<String, Integer> entry:hashMap.entrySet()){
                if(i>100){
                //将新元素与堆顶元素相比，大于对顶元素则替换掉堆顶元素
                    if(entry.getValue()>words[1].counts){
                        words[1].word = entry.getKey();
                        words[1].counts = entry.getValue();
                        //维护最大堆的性质
                        heapFy(words, 1, 100);
                    }
                }else{
                    words[i++] = new Words(entry.getKey(),entry.getValue());
                    if(i==101){
                        buildMinHeap(words);//构建最小堆
                    }
                }               
            }   
            //堆排序，将第一个元素与“最后一个元素”交换，然后维护最小堆的性质
            for(int k = 1;k<words.length-1;k++){
                exchange(words, 1, words.length-k);
                heapFy(words,1,words.length-1-k);
            }
            for(int k =1 ;k<=words.length-1;k++){
                System.out.println(words[k].word+" "+words[k].counts);           
            }
        }

    }

    //构建最小堆
    private static void buildMinHeap(Words[] words) {
        int length = words.length-1;
        for(int i = length/2;i>=1;i--){
            heapFy(words,i,length);
        }
    }
    //维护最小堆
    private static void heapFy(Words[] words, int i, int length) {
        if(i>length)
            return ;
        int left = left(i);
        int right = right(i);
        int minIndex = i;
        //找到左右节点中最小的节点
        if(left>=1&&left<=length){
            if(words[left].compareTo(words[minIndex])< 0){
                minIndex = left;
            }
        }
        if(right>=1&&right<=length){
            if(words[right].compareTo(words[minIndex])<0){
                minIndex = right;
            }
        }
        if(minIndex==i){
            return ;
        }else{
            exchange(words,i,minIndex);
            heapFy(words, minIndex, length);
        }
    }
    //交换两个元素
    private static void exchange(Words[] words, int i, int maxindex) {
        try {
            Words temp;     
            temp = words[i].clone();
            words[i] = words[maxindex].clone();
            words[maxindex] = temp.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
    //返回左节点索引
    private static int left(int i) {
        return 2*i;
    }
    //返回右节点索引
    private static int right(int i) {
        return 2*i+1;
    }
    //实现Runnable的工具类
    static class MyFileUtil implements Runnable {
        public String filrname;

        @Override
        public void run() {
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(filrname);
                br = new BufferedReader(fr);
                String line = "";
                String[] arrs = null;
                while ((line = br.readLine()) != null) {
                    // System.out.println(line);
                    arrs  = line.split(" ");
                    for (String string : arrs) {
                        string = prePro(string);
                        if(string.equals("")){
                            continue;
                        }
                        if (hashMap.containsKey(string.trim())) {
                            hashMap.put(string, hashMap.get(string) + 1);
                        } else {
                            hashMap.put(string, 1);
                        }
                    }                   
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                    if (fr != null)
                        fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        private String prePro(String str) {
            StringBuilder sb = new StringBuilder();
            char[] ch = str.trim().toCharArray();
            for (char c : ch) {
                if((c>='a'&&c<='z')||(c>='A'&&c<='Z')){
                    sb.append(c);
                }
            }
            return sb.toString();
        }

    }
}