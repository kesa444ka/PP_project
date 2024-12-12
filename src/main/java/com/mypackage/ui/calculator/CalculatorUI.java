package com.mypackage.ui.calculator;

import com.mypackage.builder.Builder;
import com.mypackage.main.Main;

import javax.swing.*;
import java.io.File;

public class CalculatorUI extends JFrame {
    private JLabel outputFileLabel;
    private JTextField outputFileField;
    private JLabel calcModeLabel;
    private JLabel zipLabel;
    private JLabel encryptLabel;
    private JCheckBox zipCheckbox;
    private JCheckBox encryptCheckbox;
    private JButton processButton;
    private JPanel panel;
    private JLabel resultLabel;
    private JButton FileChooser;
    private JLabel label;
    private JComboBox calcModeBox;

    private String inputFile;

    public CalculatorUI() {
        JFrame frame = new JFrame("Выбор файла");
        JButton button = new JButton("Выбрать файл");
        FileChooser.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                inputFile = file.getAbsolutePath();
                label.setText("Входной файл: " + file.getName());
            }
        });

        processButton.addActionListener(actionEvent -> {
            try {
                String outputFile = outputFileField.getText();
                int calcMode = calcModeBox.getSelectedIndex() + 1;
                boolean shouldZip = zipCheckbox.isSelected();
                boolean shouldEncrypt = encryptCheckbox.isSelected();

                Builder b = Builder.get()
                        .setInputFile(inputFile)
                        .setOutputFile(outputFile)
                        .setCalculationMode(calcMode)
                        .setShouldZip(shouldZip)
                        .setShouldEncrypt(shouldEncrypt);

                Main.main(new String[]{
                        b.getInputFile(),
                        b.getOutputFile(),
                        String.valueOf(b.getCalculationMode()),
                        String.valueOf(b.getShouldZip()),
                        String.valueOf(b.getShouldEncrypt())
                });
                resultLabel.setText("Работа завершена успешно!");
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        CalculatorUI frame = new CalculatorUI();
        frame.setContentPane(frame.panel);
        frame.setTitle("ArraySort");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
