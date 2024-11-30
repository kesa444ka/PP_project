import javax.swing.*;
import java.awt.*;

public class CalculatorUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Math File Processor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(8, 2, 10, 10));

        JLabel inputFileLabel = new JLabel("Имя входного файла(или путь):");
        JTextField inputFileField = new JTextField();

        JLabel outputFileLabel = new JLabel("Имя выходного файла(или путь):");
        JTextField outputFileField = new JTextField();

        JLabel inputTypeLabel = new JLabel("Тип входного файла (txt/json/xml/yaml):");
        JTextField inputTypeField = new JTextField();

        JLabel outputTypeLabel = new JLabel("Тип выходного файла (txt/json/xml/yaml):");
        JTextField outputTypeField = new JTextField();

        JLabel calcModeLabel = new JLabel("Способ расчёта(1/2/3)");
        JTextField calcModeField = new JTextField();

        JLabel zipLabel = new JLabel("Архивировать ли выходной файл? (true/false):");
        JCheckBox zipCheckbox = new JCheckBox();

        JLabel encryptLabel = new JLabel("Шифровать ли выходной файл? (true/false):");
        JCheckBox encryptCheckbox = new JCheckBox();

        JButton processButton = new JButton("Начать работу!");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);

        frame.add(inputFileLabel);
        frame.add(inputFileField);

        frame.add(outputFileLabel);
        frame.add(outputFileField);

        frame.add(inputTypeLabel);
        frame.add(inputTypeField);

        frame.add(outputTypeLabel);
        frame.add(outputTypeField);

        frame.add(calcModeLabel);
        frame.add(calcModeField);

        frame.add(zipLabel);
        frame.add(zipCheckbox);

        frame.add(encryptLabel);
        frame.add(encryptCheckbox);

        frame.add(processButton);
        frame.add(new JScrollPane(resultArea));

        processButton.addActionListener(e -> {
            try {
                String inputFile = inputFileField.getText();
                String outputFile = outputFileField.getText();
                String inputType = inputTypeField.getText();
                String outputType = outputTypeField.getText();
                int calcMode = Integer.parseInt(calcModeField.getText());
                boolean shouldZip = zipCheckbox.isSelected();
                boolean shouldEncrypt = encryptCheckbox.isSelected();

                Builder b = Builder.get()
                        .setInputFile(inputFile)
                        .setOutputFile(outputFile)
                        .setInputType(inputType)
                        .setOutputType(outputType)
                        .setCalculationMode(calcMode)
                        .setShouldZip(shouldZip)
                        .setShouldEncrypt(shouldEncrypt);

                Main.main(new String[]{
                        b.getInputFile(),
                        b.getOutputFile(),
                        b.getInputType(),
                        b.getOutputType(),
                        String.valueOf(b.getCalculationMode()),
                        String.valueOf(b.getShouldZip()),
                        String.valueOf(b.getShouldEncrypt())
                });

                resultArea.setText("Работа завершена успешно!");
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }
}
