package 栈;

import java.util.Calendar;
import java.util.Stack;

/**
 * 给定一个平衡括号字符串 S，按下述规则计算该字符串的分数：
 *
 * () 得 1 分。
 * AB 得 A + B 分，其中 A 和 B 是平衡括号字符串。
 * (A) 得 2 * A 分，其中 A 是平衡括号字符串。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/score-of-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _856_括号的分数 {

    public int scoreOfParentheses(String S) {
        Stack<String> stack = new Stack<>();
        int len = S.length();
        int score = 0;
        for (int i = 0; i < len; i ++) {
            char c = S.charAt(i);
            if (c == '(') {
                stack.push(String.valueOf(c));
                if (score != 0) {
                    stack.push(score + "");
                    score = 0;
                }
            } else {
                String n = stack.pop();
                if (n .equals('(')) {
                    if (score == 0) {
                        score = 1;
                    } else {
                        score *= 2;
                    }
                } else {
                    score += Integer.parseInt(n);
                }
            }
        }
        return score;
    }

    public static void main(String[] args) {
        _856_括号的分数 c = new _856_括号的分数();
        System.out.println(c.scoreOfParentheses("(()(()))"));
    }
}
