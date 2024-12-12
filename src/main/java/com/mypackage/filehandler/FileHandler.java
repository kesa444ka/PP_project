package com.mypackage.filehandler;

import java.nio.file.*;
import java.util.*;
import java.io.*;

import org.jdom2.input.*;
import org.jdom2.*;
import org.jdom2.output.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

import org.yaml.snakeyaml.*;

public class FileHandler {

    public static String readFile(String fileName) throws IOException, JDOMException {
        String type = GetFileType(fileName);
        return switch (type.toLowerCase()) {
            case "txt" -> Files.readString(Paths.get(fileName));
            case "xml" -> parseXML(fileName);
            case "json" -> parseJSON(fileName);
            case "yaml" -> parseYAML(fileName);
            default -> throw new IllegalArgumentException("Неподдерживаемый тип файла: " + type);
        };
    }

    public static void writeFile(String path, String data) throws IOException {
        String type=GetFileType(path);
        switch (type.toLowerCase()) {
            case "txt":
                Files.writeString(Paths.get(path), data);
                break;
            case "xml":
                writeXML(path, data);
                break;
            case "json":
                writeJSON(path, data);
                break;
            case "yaml":
                writeYAML(path, data);
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

    private static String parseJSON(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(new File(filename));

        StringBuilder result = new StringBuilder();
        JsonNode examples = root.get("mathExamples");
        if(examples!=null && examples.isArray()){
            for(JsonNode node: examples){
                result.append(node.get("expression").asText().trim()).append("\n");            }
        }

        return result.toString().trim();
    }

    private static void writeJSON(String path, String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();

        ArrayNode arr = objectMapper.createArrayNode();
        String[] results = data.split("\n");
        for(String result: results){
            ObjectNode obj = objectMapper.createObjectNode();
            obj.put("result", result);
            arr.add(obj);
        }

        root.set("results", arr);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), root);
    }

    private static String parseYAML(String filename) throws IOException {
        Yaml yaml = new Yaml();

        try(FileInputStream fis = new FileInputStream(filename)){
            Map<String,Object> data = yaml.loadAs(fis, Map.class);
            StringBuilder result = new StringBuilder();

            @SuppressWarnings("unchecked")
            List<Map<String,Object>> examples = (List<Map<String,Object>>) data.get("mathExamples");
            for(Map<String,Object> example: examples){
                result.append(example.get("expression")).append("\n");

            }
            return result.toString().trim();
        }
    }

    private static void writeYAML(String path, String data){
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);

        List<Map<String, String>> results = new ArrayList<>();
        String[] lines = data.split("\n");
        for (String line : lines) {
            Map<String, String> result = new HashMap<>();
            result.put("result", line);
            results.add(result);
        }

        Map<String, Object> root = new HashMap<>();
        root.put("results", results);

        try (FileWriter writer = new FileWriter(path)) {
            yaml.dump(root, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String GetFileType(String filename) {
        Path path = Paths.get(filename);
        String fileName = path.getFileName().toString();
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex + 1);
        }
        return "";
    }
}
