package com.mypackage.processor.strategies;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCalculationStrategy implements CalculationStrategy {
    @Override
    public int calculate(String expression) {
        return calculateWithRegex(expression.trim());
    }

    private static int calculateWithRegex(String expression) {
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

    private static int handleOperation(char op, int a, int b){
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new IllegalArgumentException("Неподдерживаемый оператор: " + op);
        };
    }
}
