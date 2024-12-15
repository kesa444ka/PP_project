package com.mypackage.processor;

import com.mypackage.processor.strategies.*;

public class CalculationStrategyFactory {
    public static CalculationStrategy getStrategy(int choice) {
        return switch (choice) {
            case 1 -> new NoRegexCalculationStrategy();
            case 2 -> new RegexCalculationStrategy();
            case 3 -> new LibraryCalculationStrategy();
            default -> throw new IllegalArgumentException("Неподдерживаемый способ расчёта: " + choice);
        };
    }
}
