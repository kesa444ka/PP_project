import javax.swing.*;

public class CalculatorUI extends JFrame {
    private JLabel inputFileLabel;
    private JLabel outputFileLabel;
    private JTextField inputFileField;
    private JTextField outputFileField;
    private JLabel inputTypeLabel;
    private JLabel outputTypeLabel;
    private JTextField inputTypeField;
    private JTextField outputTypeField;
    private JLabel calcModeLabel;
    private JLabel zipLabel;
    private JLabel encryptLabel;
    private JTextField calcModeField;
    private JCheckBox zipCheckbox;
    private JCheckBox encryptCheckbox;
    private JButton processButton;
    private JPanel panel;
    private JLabel resultLabel;

    public CalculatorUI() {
        processButton.addActionListener(actionEvent -> {
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
