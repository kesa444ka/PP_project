package com.mypackage.processor.strategies;

import java.util.Stack;

public class NoRegexCalculationStrategy implements CalculationStrategy {
    @Override
    public int calculate(String expression) {
        String postfix = InfixToPostfix(expression.trim());
        return calculateWithoutRegex(postfix);
    }

    protected static String InfixToPostfix (String expression) {
        Stack<Character> ops = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        for(int i=0; i<expression.length(); i++) {
            char ch = expression.charAt(i);

            if(Character.isDigit(ch)){
                i = appendNumber(expression, postfix, i);
            }
            else if(ch == '('){
                ops.push(ch);
            }
            else if(ch == ')'){
                processClosingBrackets(ops,postfix);
            }
            else if(isOperator(ch)){
                processOperator(ch,ops,postfix);
            }
        }

        while(!ops.isEmpty()){
            postfix.append(' ').append(ops.pop());
        }


        return postfix.toString().replaceAll("\\s+", " ").trim();
    }

    private static int appendNumber(String expression, StringBuilder postfix, int index) {
        if (!postfix.isEmpty() && postfix.charAt(postfix.length() - 1) != ' ') {
            postfix.append(' ');
        }
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            postfix.append(expression.charAt(index));
            index++;
        }
        postfix.append(' ');
        return index - 1;
    }

    private static void processClosingBrackets(Stack<Character> ops, StringBuilder postfix) {
        while (ops.peek() != '(' && !ops.isEmpty()){
            postfix.append(' ').append(ops.pop());
        }
        if(!ops.isEmpty()){
            ops.pop();
        }
    }

    private static void processOperator(char ch, Stack<Character> ops, StringBuilder postfix) {
        while(!ops.isEmpty() && priority(ops.peek()) >= priority(ch)){
            postfix.append(' ').append(ops.pop());
        }
        ops.push(ch);
    }

    protected static int calculateWithoutRegex(String postfix){
        Stack<Integer> digits = new Stack<>();

        for(int i=0; i<postfix.length(); i++){
            char ch = postfix.charAt(i);

            if(Character.isDigit(ch)){
                i = processNumber(postfix, digits, i);
            }
            else if(isOperator(ch)){
                processOperation(ch, digits);
            }
        }
        return digits.peek();
    }

    private static int processNumber(String postfix, Stack<Integer> digits, int index) {
        int digit = 0;
        while (index < postfix.length() && Character.isDigit(postfix.charAt(index))) {
            digit = digit * 10 + (postfix.charAt(index) - '0');
            index++;
        }
        digits.push(digit);
        return index - 1;
    }

    private static void processOperation(char ch, Stack<Integer> digits) {
        int digit2 = digits.pop();
        int digit1 = digits.pop();
        digits.push(handleOperation(ch, digit1, digit2));
    }

    private static boolean isOperator(char ch){
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static int priority(char op){
        return switch (op){
            case '+','-' -> 1;
            case '*','/' -> 2;
            default -> 0;
        };
    }

    private static int handleOperation(char op, int a, int b){
        return switch (op){
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new IllegalArgumentException("Неподдерживаемый оператор: " + op);
        };
    }
}
