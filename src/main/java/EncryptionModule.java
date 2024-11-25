import java.io.*;

public class EncryptionModule {

    // Метод для шифрования файла
    public static void encrypt(String filePath) throws IOException {
        executeCommand("cipher /e \"" + filePath + "\"");
    }

    // Метод для расшифровки файла
    public static void decrypt(String filePath) throws IOException {
        executeCommand("cipher /d \"" + filePath + "\"");
    }

    // Метод для проверки, зашифрован ли файл
    public static boolean isEncrypted(String filePath) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("cipher", "/c", filePath);
        Process process = builder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "CP866"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Проверяем, содержит ли строка символ "E" перед именем файла
                if (line.trim().startsWith("E ")) {
                    return true;
                }
            }
        }

        return false;
    }


    // Вспомогательный метод для выполнения команд
    private static void executeCommand(String command) throws IOException {
        Process process = new ProcessBuilder("cmd.exe", "/c", command).start();
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Command failed: " + command);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Command interrupted: " + command, e);
        }
    }
}
