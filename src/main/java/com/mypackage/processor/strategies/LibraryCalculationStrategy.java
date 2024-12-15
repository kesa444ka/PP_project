package com.mypackage.processor.strategies;

import net.objecthunter.exp4j.*;

public class LibraryCalculationStrategy implements CalculationStrategy {
    @Override
    public int calculate(String expression) {
        return calculateLibrary(expression.trim());
    }

    protected static int calculateLibrary(String expression) {
        Expression exp = new ExpressionBuilder(expression).build();
        double result = exp.evaluate();
        return (int) result;
    }
}
