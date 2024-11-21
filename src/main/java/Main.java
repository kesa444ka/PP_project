import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 5) {
            System.out.println("Нужный формат данных: <inputFile> <outputFile> <inputType> <outputType>");
            System.out.println("Возможные форматы: txt");
            System.out.println("1. Вычисления без использования регулярных выражений\n" +
                    "2. Вычисления с использованием регулярных выражений\n" +
                    "3. Вычисления с использованием бибилиотеки exp4j");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String inputType = args[2];
        String outputType = args[3];
        int howToCalculate = Integer.parseInt(args[4]);

        try{
            String content = FileHandler.readFile(inputFile, inputType);

            String result = Processor.process(content, howToCalculate);

            FileHandler.writeFile(outputFile, result, outputType);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
