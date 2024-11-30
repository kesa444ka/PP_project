import java.util.Scanner;

public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);

    private static void handleFileProcessing() {
        try {
            System.out.print("Введите путь к входному файлу: ");
            String inputFile = scanner.nextLine();

            System.out.print("Введите путь к выходному файлу: ");
            String outputFile = scanner.nextLine();

            System.out.print("Введите формат входного файла (txt, json, xml, yaml): ");
            String inputType = scanner.nextLine();

            System.out.print("Введите формат выходного файла (txt, json, xml, yaml): ");
            String outputType = scanner.nextLine();

            System.out.println("""
                Выберите способ расчёта:
                1. Без использования регулярных выражений
                2. С использованием регулярных выражений
                3. С использованием библиотеки exp4j
            """);
            System.out.print("Ваш выбор: ");
            int calculationMode = Integer.parseInt(scanner.nextLine());

            System.out.print("Архивировать выходной файл? (true/false): ");
            boolean shouldZip = Boolean.parseBoolean(scanner.nextLine());

            System.out.print("Шифровать выходной файл? (true/false): ");
            boolean shouldEncrypt = Boolean.parseBoolean(scanner.nextLine());

            // Создаем объект Builder с заданными параметрами
            Builder b = Builder.get()
                    .setInputFile(inputFile)
                    .setOutputFile(outputFile)
                    .setInputType(inputType)
                    .setOutputType(outputType)
                    .setCalculationMode(calculationMode)
                    .setShouldZip(shouldZip)
                    .setShouldEncrypt(shouldEncrypt);

            // Вызываем основной метод для обработки файла
            Main.main(new String[]{
                    b.getInputFile(),
                    b.getOutputFile(),
                    b.getInputType(),
                    b.getOutputType(),
                    String.valueOf(b.getCalculationMode()),
                    String.valueOf(b.getShouldZip()),
                    String.valueOf(b.getShouldEncrypt())
            });

            System.out.println("Обработка завершена! Проверьте файл: " + b.getOutputFile());
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Обработать файл");
            System.out.println("2. Выход");
            System.out.print("Ваш выбор: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> handleFileProcessing();
                case 2 -> {
                    System.out.println("Выход из программы. До свидания!");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
