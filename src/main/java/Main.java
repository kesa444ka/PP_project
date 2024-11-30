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

    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Нужный формат данных: <inputFile> <outputFile> <inputType> <outputType>");
            System.out.println("Возможные форматы: txt, json, xml, yaml. Архивы: zip.");
            System.out.println("""
                    1. Вычисления без использования регулярных выражений
                    2. Вычисления с использованием регулярных выражений
                    3. Вычисления с использованием бибилиотеки exp4j""");
            System.out.println("<zipOption>: true or false (archive output)");
            System.out.println("<encryptionOption>: true or false (encryption output)");
            return;
        }

        Builder b = Builder.get()
                .setInputFile(args[0])
                .setOutputFile(args[1])
                .setInputType(args[2])
                .setOutputType(args[3])
                .setCalculationMode(Integer.parseInt(args[4]))
                .setShouldZip(Boolean.parseBoolean(args[5]))
                .setShouldEncrypt(Boolean.parseBoolean(args[6]));

//        String inputFile = args[0];
//        String outputFile = args[1];
//        String inputType = args[2];
//        String outputType = args[3];
//        int howToCalculate = Integer.parseInt(args[4]);
//        boolean shouldZip = Boolean.parseBoolean(args[5]);
//        boolean shouldEncrypt = Boolean.parseBoolean(args[6]);

        String outputDir = "temp"; // папка для верменных файлов

        try{
            String extractedFile = b.getInputFile(); //inputFile;
            if(b.getInputFile().endsWith(".zip")){
                new File(outputDir).mkdirs();
                extractedFile = CompressionModule.decompress(b.getInputFile(), outputDir, '.'+ b.getInputFile());
            }
            if(EncryptionModule.isEncrypted(extractedFile)){
                EncryptionModule.decrypt(extractedFile);
            }

            String content = FileHandler.readFile(extractedFile, b.getInputType());

            String result = Processor.process(content, b.getCalculationMode());

            FileHandler.writeFile(b.getOutputFile(), result, b.getOutputType());

            //Архивирование и шифрование, если нужно
            String zipFile="output.zip";
            if (b.getShouldZip()) {
                CompressionModule.compress(b.getOutputFile(), zipFile);
                if (b.getShouldEncrypt()) {
                    EncryptionModule.encrypt(zipFile);
                }
                new File(b.getOutputFile()).delete();
            }
            else if(b.getShouldEncrypt()){
                EncryptionModule.encrypt(b.getOutputFile());
            }

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            deleteDirectory(new File(outputDir));
        }
    }
}
