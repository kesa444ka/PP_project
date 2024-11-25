import java.io.*;

public class Main {
    private static void deleteDirectory(File dir){
        File[] files = dir.listFiles();
        if(files != null){
            for(File file : files){
                deleteDirectory(file);
            }
        }
        dir.delete();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 6) {
            System.out.println("Нужный формат данных: <inputFile> <outputFile> <inputType> <outputType>");
            System.out.println("Возможные форматы: txt");
            System.out.println("1. Вычисления без использования регулярных выражений\n" +
                    "2. Вычисления с использованием регулярных выражений\n" +
                    "3. Вычисления с использованием бибилиотеки exp4j");
            System.out.println("<zipOption>: true or false (archive output)");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String inputType = args[2];
        String outputType = args[3];
        int howToCalculate = Integer.parseInt(args[4]);
        boolean shouldZip = Boolean.parseBoolean(args[5]);

        String outputDir = "temp"; // папка для верменных файлов

        try{
            String extractedFile = inputFile;
            if(inputFile.endsWith(".zip")){
                new File(outputDir).mkdirs();
                extractedFile = CompressionModule.decompress(inputFile, outputDir, '.'+inputType);
            }

            String content = FileHandler.readFile(extractedFile, inputType);

            String result = Processor.process(content, howToCalculate);

            FileHandler.writeFile(outputFile, result, outputType);

            //Архивирование и шифрование, если нужно
            String zipFile="output.zip";
            if (shouldZip) {
                CompressionModule.compress(outputFile, zipFile);
                new File(outputFile).delete();
            }


        } catch(Exception e){
            e.printStackTrace();
        } finally {
            deleteDirectory(new File(outputDir));
        }
    }
}
