package com.hellolife.sys.pub;

import java.util.Collections;
import java.util.Stack;

/**
 * 需要根据配置的表达式(例如：5+12*(3+5)/7.0)计算出相应的结果，因此使用java中的栈利用后缀表达式的方式实现该工具类。
 * 后缀表达式就是将操作符放在操作数的后面展示的方式，例如：3+2 后缀表达式为32+，3*(2+1)的后缀表达式为：321+*,解决表达式求值首先需要根据字符串表达式求出后缀表达式，
 * 然后使用后缀表达式和操作数栈实现计算，计算的大致思想是从后缀表达式中取元素，如果元素是数值则加入到操作数栈中，如果是运算符则从操作数栈中取两个数来参与运算。
 * 后缀表达式的获取要借助于两个栈，一个是后缀表达式栈，一个是操作符栈，顺序扫描算术表达式，如果是数值则直接加入后缀表达式栈，
 * 如果是运算符则使用当前运算符和运算符栈中的栈顶元素做比较，如果当前运算符的优先级高则当前元素进入操作符栈，如果当前元素优先级低，
 * 则操作符栈顶元素出栈加入到后缀表达式栈中,一直到当前元素优先级高于操作符栈顶元素优先级则当前元素入操作符栈，目前只支持加减乘除和带小括号的运算。
 *
 *
 * */

public class Calculator {
    private Stack<String> postfixStack  = new Stack<String>();//后缀式栈
    private Stack<Character> opStack  = new Stack<Character>();//运算符栈
    private int [] operatPriority  = new int[] {0,3,2,1,-1,1,0,2};//运用运算符ASCII码-40做索引的运算符优先级
    /**
     * 按照给定的表达式计算
     * @param expression 要计算的表达式例如:5+12*(3+5)/7
     * @return
     */
    public double calculate(String expression) {
        Stack<String> resultStack  = new Stack<String>();
        prepare(expression);
        Collections.reverse(postfixStack);//将后缀式栈反转
        String firstValue  ,secondValue,currentValue;//参与计算的第一个值，第二个值和算术运算符
        while(!postfixStack.isEmpty()) {
            currentValue  = postfixStack.pop();
            if(!isOperator(currentValue.charAt(0))) {//如果不是运算符则存入操作数栈中
                resultStack.push(currentValue);
            } else {//如果是运算符则从操作数栈中取两个值和该数值一起参与运算
                secondValue  = resultStack.pop();
                firstValue  = resultStack.pop();
                String tempResult  = calculate(firstValue, secondValue, currentValue.charAt(0));
                resultStack.push(tempResult);
            }
        }
        return Double.valueOf(resultStack.pop());
    }

    /**
     * 数据准备阶段将表达式转换成为后缀式栈
     * @param expression
     */
    private void prepare(String expression) {
        opStack.push(',');//运算符放入栈底元素逗号，此符号优先级最低
        char[] arr  = expression.toCharArray();
        int currentIndex  = 0;//当前字符的位置
        int count = 0;//上次算术运算符到本次算术运算符的字符的长度便于或者之间的数值
        char currentOp  ,peekOp;//当前操作符和栈顶操作符
        for(int i=0;i<arr.length;i++) {
            currentOp = arr[i];
            if(isOperator(currentOp)) {//如果当前字符是运算符
                if(count > 0) {
                    postfixStack.push(new String(arr,currentIndex,count));//取两个运算符之间的数字
                }
                peekOp = opStack.peek();
                if(currentOp == ')') {//遇到反括号则将运算符栈中的元素移除到后缀式栈中直到遇到左括号
                    while(opStack.peek() != '(') {
                        postfixStack.push(String.valueOf(opStack.pop()));
                    }
                    opStack.pop();
                } else {
                    while(currentOp != '(' && peekOp != ',' && compare(currentOp,peekOp) ) {
                        postfixStack.push(String.valueOf(opStack.pop()));
                        peekOp = opStack.peek();
                    }
                    opStack.push(currentOp);
                }
                count = 0;
                currentIndex = i+1;
            } else {
                count++;
            }
        }
        if(count > 1 || (count == 1 && !isOperator(arr[currentIndex]))) {//最后一个字符不是括号或者其他运算符的则加入后缀式栈中
            postfixStack.push(new String(arr,currentIndex,count));
        }

        while(opStack.peek() != ',') {
            postfixStack.push(String.valueOf( opStack.pop()));//将操作符栈中的剩余的元素添加到后缀式栈中
        }
    }

    /**
     * 判断是否为算术符号
     * @param c
     * @return
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' ||c == ')';
    }

    /**
     * 利用ASCII码-40做下标去算术符号优先级
     * @param cur
     * @param peek
     * @return
     */
    public  boolean compare(char cur,char peek) {// 如果是peek优先级高于cur，返回true，默认都是peek优先级要低
        boolean result  = false;
        if(operatPriority[(peek)-40] >= operatPriority[(cur) - 40]) {
            result = true;
        }
        return result;
    }

    /**
     * 按照给定的算术运算符做计算
     * @param firstValue
     * @param secondValue
     * @param currentOp
     * @return
     */
    private String calculate(String firstValue,String secondValue,char currentOp) {
        String result  = "";
        switch(currentOp) {
            case '+':
                result = String.valueOf(ArithHelper.add(firstValue, secondValue));
                break;
            case '-':
                result = String.valueOf(ArithHelper.sub(firstValue, secondValue));
                break;
            case '*':
                result = String.valueOf(ArithHelper.mul(firstValue, secondValue));
                break;
            case '/':
                result = String.valueOf(ArithHelper.div(firstValue, secondValue));
                break;
        }
        return result;
    }
}
