import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 5) {
            System.out.println("Нужный формат данных: <inputFile> <outputFile> <inputType> <outputType>");
            System.out.println("Возможные форматы: txt");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String inputType = args[2];
        String outputType = args[3];
        int howToCalculate = Integer.parseInt(args[4]);

        try{
            //чтение данных из файла
            String content = FileHandler.readFile(inputFile, inputType);

            String result = Processor.process(content, howToCalculate);

            //запись данных в файл
            FileHandler.writeFile(outputFile, result, outputType);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
