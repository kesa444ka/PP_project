import java.io.*;
import java.util.zip.*;

public class CompressionModule {

    // Метод для извлечения файла из архива
    public static String decompress(String archivePath, String outputDir, String requiredExtension) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(archivePath))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                if (fileName.endsWith(requiredExtension)) {
                    File outputFile = new File(outputDir, fileName);
                    try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }
                    }
                    return outputFile.getAbsolutePath();
                }
            }
        }
        throw new FileNotFoundException("File with extension " + requiredExtension + " not found in ZIP archive.");
    }

    // Метод для архивирования файла
    public static void compress (String outputFile, String zipFile) throws IOException {
        File sourceFile = new File(outputFile);

        // Создаём ZIP файл
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(sourceFile)) {

            // Создаём запись для файла в архиве
            ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
            zos.putNextEntry(zipEntry);

            // Копируем содержимое файла в архив
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            // Завершаем запись в ZIP
            zos.closeEntry();
        }
    }
}