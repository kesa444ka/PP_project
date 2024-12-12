package com.mypackage.processor;

import java.util.*;
import java.util.regex.*;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Processor {

    public static String calculate(String input, int choice) {
        StringBuilder result = new StringBuilder();
        String[] expressions = input.split("\\R");

        for (String expression : expressions) {
            int res;
            try{
                switch (choice) {
                    case 1:
                        String postfix = InfixToPostfix(expression.trim());
                        res = calculateWithoutRegex(postfix);
                        result.append((res)).append("\n");
                        break;
                    case 2:
                        res = calculateWithRegex(expression.trim());
                        result.append((res)).append("\n");
                        break;
                    case 3:
                        res = calculateLibrary(expression.trim());
                        result.append((res)).append("\n");
                        break;
                    default:
                        throw new IllegalArgumentException("Неподдерживаемый способ расчёта: " + choice);
                }
            }
            catch(Exception e){
                System.err.println("Ошибка обработки выражения: " + expression + ". Пропущено.");
            }
        }
        return result.toString().trim();
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

    private static int handleOperation(char op, int a, int b){
        return switch (op){
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new IllegalArgumentException("Неподдерживаемый оператор: " + op);
        };
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


    protected static int calculateWithRegex(String expression) {
        expression = expression.replaceAll("\\s+", "");
        return evaluate(expression);
    }

    private static int evaluate(String expression) {
        Pattern bracketPattern = Pattern.compile("\\(([^()]+)\\)");
        Matcher matcher = bracketPattern.matcher(expression);

        while (matcher.find()) {
            String subExpr = matcher.group(1);
            int subResult = evaluate(subExpr);
            expression = expression.replace("(" + subExpr + ")", String.valueOf(subResult));
            matcher = bracketPattern.matcher(expression);
        }

        expression = processOperators(expression, "[*/]");
        expression = processOperators(expression, "[+-]");

        return Integer.parseInt(expression);
    }

    private static String processOperators(String expression, String operatorPattern) {
        Pattern pattern = Pattern.compile("(-?\\d+)([" + operatorPattern + "])(-?\\d+)");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            int left = Integer.parseInt(matcher.group(1));
            String operator = matcher.group(2);
            int right = Integer.parseInt(matcher.group(3));

            int result = handleOperation(operator.charAt(0), left, right);

            expression = matcher.replaceFirst(String.valueOf(result));
            matcher = pattern.matcher(expression);
        }
        return expression;
    }

    protected static int calculateLibrary(String expression) {
        Expression exp = new ExpressionBuilder(expression).build();
        double result = exp.evaluate();
        return (int) result;
    }
}
