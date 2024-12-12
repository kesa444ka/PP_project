package com.mypackage.builder;

public class Builder {
    private String inputFile;
    private String outputFile;
    private int calculationMode;
    private boolean shouldZip;
    private boolean shouldEncrypt;

    private static Builder instance;

    private Builder() {}

    public static Builder get() {
        if (instance == null) {
            instance = new Builder();
        }
        return instance;
    }

    public Builder setInputFile(String inputFile) {
        this.inputFile = inputFile;
        return this;
    }

    public Builder setOutputFile(String outputFile) {
        this.outputFile = outputFile;
        return this;
    }

    public Builder setCalculationMode(int calculationMode) {
        this.calculationMode = calculationMode;
        return this;
    }

    public Builder setShouldZip(boolean shouldZip) {
        this.shouldZip = shouldZip;
        return this;
    }

    public Builder setShouldEncrypt(boolean shouldEncrypt) {
        this.shouldEncrypt = shouldEncrypt;
        return this;
    }

    public String getInputFile() {
        return inputFile;
    }
    public String getOutputFile() {
        return outputFile;
    }

    public int getCalculationMode() {
        return calculationMode;
    }
    public boolean getShouldZip() {
        return shouldZip;
    }
    public boolean getShouldEncrypt() {
        return shouldEncrypt;
    }
}
