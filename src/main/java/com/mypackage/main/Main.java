package com.mypackage.main;

import com.mypackage.builder.Builder;
import com.mypackage.compression.CompressionModule;
import com.mypackage.encryption.EncryptionModule;
import com.mypackage.fileprocessor.FileProcessor;
import com.mypackage.processor.Processor;

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
        if (args.length < 4) {
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
                .setCalculationMode(Integer.parseInt(args[2]))
                .setShouldZip(Boolean.parseBoolean(args[3]))
                .setShouldEncrypt(Boolean.parseBoolean(args[4]));

        String outputDir = "temp"; // папка для временных файлов

        try{
            String extractedFile = b.getInputFile();
            if(b.getInputFile().endsWith(".zip")){
                new File(outputDir).mkdirs();
                extractedFile = CompressionModule.decompress(b.getInputFile(), outputDir);
            }
            if(EncryptionModule.isEncrypted(extractedFile)){
                EncryptionModule.decrypt(extractedFile);
            }

            String content = FileProcessor.readFile(extractedFile);

            String result = Processor.calculate(content, b.getCalculationMode());

            FileProcessor.writeFile(b.getOutputFile(), result);

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
