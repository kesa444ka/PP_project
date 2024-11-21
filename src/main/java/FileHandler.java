import java.nio.file.*;
import java.util.*;
import java.io.*;

import org.jdom2.input.*;
import org.jdom2.*;
import org.jdom2.output.*;

public class FileHandler {

    // Чтение данных из файла
    public static String readFile(String fileName, String type) throws IOException, JDOMException {
        String content = Files.readString(Paths.get(fileName));
        switch (type) {
            case "txt":
                return content;
            case "xml":
                return parseXML(fileName);
            default:
                throw new IllegalArgumentException("Неподдерживаемый тип файла: " + type);
        }
    }

    // Запись данных в файл
    public static void writeFile(String path, String data, String type) throws IOException {
        switch (type.toLowerCase()) {
            case "txt":
                Files.writeString(Paths.get(path), data);
                break;
            case "xml":
                writeXML(path, data);
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемый тип файла: " + type);
        }
    }

    private static String parseXML(String filename) throws IOException, JDOMException {
        SAXBuilder sb = new SAXBuilder();
        Document doc=sb.build(new File(filename));

        Element root=doc.getRootElement();

        List<Element> elements=root.getChildren("example");
        StringBuilder result=new StringBuilder();
        for(Element e:elements){
            String exp=e.getChildText("expression").trim();
            result.append(exp).append("\n");
        }

        return result.toString().trim();
    }
    private static void writeXML(String path, String data) throws IOException {
        Element root=new Element("results");
        Document doc=new Document(root);

        String[] results = data.split("\n");
        for(String result:results){
            Element e=new Element("result");
            e.setText(result);
            root.addContent(e);
        }

        XMLOutputter outputter=new XMLOutputter(Format.getPrettyFormat());
        try(FileWriter fw=new FileWriter(path)){
            outputter.output(doc,fw);
        }
    }
}
