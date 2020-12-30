package 栈;

import java.util.Stack;

/**
 * 根据 逆波兰表示法，求表达式的值。
 *
 * 有效的运算符包括 +, -, *, / 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
 *
 *  
 *
 * 说明：
 *
 * 整数除法只保留整数部分。
 * 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/evaluate-reverse-polish-notation
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 前缀表达式
 * 中缀表达式
 * 后缀表达式
 *
 * 输入: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
 * 输出: 22
 * 解释:
 * 该算式转化为常见的中缀算术表达式为：
 *   ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 */
public class _150_逆波兰表达式求值 {

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        int n1, n2;
        for (String s : tokens) {
            switch (s) {
                case "+":
                    n2 = stack.pop();
                    n1 = stack.pop();
                    stack.push(n1 + n2);
                    break;
                case "-":
                    n2 = stack.pop();
                    n1 = stack.pop();
                    stack.push(n1 - n2);
                    break;
                case "*":
                    n2 = stack.pop();
                    n1 = stack.pop();
                    stack.push(n1 * n2);
                    break;
                case "/":
                    n2 = stack.pop();
                    n1 = stack.pop();
                    stack.push(n1 / n2);
                    break;
                default:
                    stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }
}
