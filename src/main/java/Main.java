import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 4) {
            System.out.println("Нужный формат данных: <inputFile> <outputFile> <inputType> <outputType>");
            System.out.println("Возможные форматы: txt");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String inputType = args[2];
        String outputType = args[3];

        try{
            //чтение данных из файла
            String content = FileHandler.readFile(inputFile, inputType);

            //запись данных в файл
            FileHandler.writeFile(outputFile, content, outputType);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
