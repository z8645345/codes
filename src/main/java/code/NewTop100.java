package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewTop100 {

    public static TreeSet<Word> treeSet = new TreeSet<Word>();
    public static CountDownLatch countDownLatch = new CountDownLatch(100);

    public static void main(String[] args) throws InterruptedException {
        NewTop100 newTop100 = new NewTop100();
        newTop100.top100();
    }

    public List<Word> top100() throws InterruptedException {
        // 读取数据文件
        String path = "D:/top100.txt";
        File files = new File(path);
        File[] fileArr = files.listFiles();
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (File file : fileArr) {
            FileJob fileJob = new FileJob(file.getPath());
            service.execute(fileJob);
        }
        service.shutdown();
        countDownLatch.await();
        return new ArrayList<>(treeSet);
    }

    static class FileJob extends Thread {
        private String fileName;

        public FileJob(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void run() {
            TreeMap<Word, Integer> treeMap = new TreeMap<>();

            try (FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr)) {
                String line = "";
                String[] arrs = null;
                while ((line = br.readLine()) != null) {
                    arrs = line.split(" ");
                    for (String e : arrs) {
                        if (!"".equals(e.trim())) {
                            Word word = new Word(e);
                            Integer num = treeMap.get(word);
                            if (num == null) {
                                num = 1;
                            } else {
                                num ++;
                            }
                            word.setNum(num);
                            treeMap.put(word, num);
                        }
                    }
                }
                TreeSet<Word> tempSet = getWords();
                addResult(tempSet);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addResult(Set<Word> wordSet) {
        synchronized (treeSet) {
            treeSet.addAll(wordSet);
            TreeSet<Word> tempSet = getWords();
            treeSet = tempSet;
        }
    }

    private static TreeSet<Word> getWords() {
        TreeSet<Word> tempSet = new TreeSet<Word>();
        int i = 0;
        for (Word word : treeSet) {
            tempSet.add(word);
            if (i++ >= 100) {
                break;
            }
        }
        return tempSet;
    }

    static class Word implements Comparable {
        private String word;
        private int num;

        public Word(String word) {
            this.word = word;
            this.num = 1;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public int compareTo(Object o) {
            Word word = (Word) o;
            return this.num - word.num;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Word word1 = (Word) o;
            return Objects.equals(word, word1.word);
        }

        @Override
        public int hashCode() {
            return Objects.hash(word);
        }
    }
}
