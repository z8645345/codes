package 二叉搜索树;

import 二叉搜索树.printer.BinaryTrees;
import 二叉搜索树.printer.Printer;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        Integer[] arr = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1, 7, 5, 8
        };
        SearchBinaryTree<Person> sbt = new SearchBinaryTree<>(Comparator.comparingInt(Person::getAge));
        for (Integer i : arr) {
            sbt.add(new Person(i));
        }
        BinaryTrees.print(sbt);
    }

//    SearchBinaryTree<Person> sbt = new SearchBinaryTree<>(Comparator.comparingInt(Person::getAge));
//    SearchBinaryTree<Person> sbt1 = new SearchBinaryTree<>(((o1, o2) -> o2.getAge() - o1.getAge()));
}
