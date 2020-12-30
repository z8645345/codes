package 栈;

import java.util.Stack;

/**
 * 实现一个基本的计算器来计算一个简单的字符串表达式的值。
 *
 * 字符串表达式可以包含左括号 ( ，右括号 )，加号 + ，减号 -，非负整数和空格  。
 */
public class _224_基本计算器 {

    public int calculate(String s) {
        Stack<String> stack = new Stack<>();
        int len = s.length();
        char fh = '+';
        int r = 0;
        for (int i = 0; i < len; i ++) {
            char n = s.charAt(i);
            if (n == '(') {
                stack.push(String.valueOf(r));
                stack.push(String.valueOf(fh));
                r = 0;
                fh = '+';
            } else if (n == '+' || n == '-') {
                fh = n;
            } else if (n == ' ') {

            } else if (n == ')') {
                r = "+".equals(stack.pop()) ? Integer.parseInt(stack.pop()) + r : Integer.parseInt(stack.pop()) - r;
            } else {
                StringBuilder ss = new StringBuilder(String.valueOf(n));
                int j = 1;
                while (i + j != len) {
                    char nextN = s.charAt(i + j);
                    if (!Character.isDigit(nextN)) {
                        break;
                    }
                    ss.append(nextN);
                    j ++;
                }
                i += j - 1;
                r = fh == '+' ? r + Integer.parseInt(ss.toString()) : r - Integer.parseInt(ss.toString());
            }
        }
        return r;
    }

    public static void main(String[] args) {
        _224_基本计算器 a = new _224_基本计算器();
        System.out.println(a.calculate("1-(5)"));
    }
}
