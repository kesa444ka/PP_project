package com.mypackage.fileprocessor;

import java.nio.file.*;

public class TextFileHandler extends FileHandlerDecorator {

    public TextFileHandler(FileHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String read(String fileName) throws Exception {
        if (fileName.endsWith(".txt")) {
            return Files.readString(Paths.get(fileName));
        }
        return super.read(fileName);
    }

    @Override
    public void write(String path, String data) throws Exception {
        if (path.endsWith(".txt")) {
            Files.writeString(Paths.get(path), data);
        } else {
            super.write(path, data);
        }
    }
}
