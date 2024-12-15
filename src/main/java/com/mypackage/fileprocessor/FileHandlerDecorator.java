package com.mypackage.fileprocessor;

public abstract class FileHandlerDecorator implements FileHandler {
    protected FileHandler nextHandler;

    public FileHandlerDecorator(FileHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public String read(String fileName) throws Exception {
        if (nextHandler != null) {
            return nextHandler.read(fileName);
        }
        throw new UnsupportedOperationException("Operation not supported for this file type.");
    }

    @Override
    public void write(String fileName, String data) throws Exception {
        if (nextHandler != null) {
            nextHandler.write(fileName, data);
        } else {
            throw new UnsupportedOperationException("Operation not supported for this file type.");
        }
    }
}
