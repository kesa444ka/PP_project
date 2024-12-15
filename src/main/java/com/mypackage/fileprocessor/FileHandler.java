package com.mypackage.fileprocessor;

public interface FileHandler {
    String read(String fileName) throws Exception;
    void write(String fileName, String data) throws Exception;
}
