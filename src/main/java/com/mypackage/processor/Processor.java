package com.mypackage.processor;

import com.mypackage.processor.strategies.CalculationStrategy;

public class Processor {

    public static String getResult(String input, int choice) {
        CalculationStrategy strategy = CalculationStrategyFactory.getStrategy(choice);

        StringBuilder result = new StringBuilder();
        String[] expressions = input.split("\\R");

        for (String expression : expressions) {
            try{
                int res = strategy.calculate(expression);
                result.append(res).append("\n");
            }
            catch(Exception e){
                System.err.println("Ошибка обработки выражения: " + expression + ". Пропущено.");
            }
        }
        return result.toString().trim();
    }
}
