package com.mypackage.fileprocessor;

public class FileProcessor {
    private static final FileHandler Chain = createChain();

    private static FileHandler createChain() {
        return new TextFileHandler(
                new XMLFileHandler(
                        new JSONFileHandler(
                                new YAMLFileHandler(null)
                        )
                )
        );
    }

    public static String readFile(String fileName) throws Exception {
        return Chain.read(fileName);
    }

    public static void writeFile(String fileName, String data) throws Exception {
        Chain.write(fileName, data);
    }
}
