package com.mypackage.fileprocessor;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import java.io.*;

public class JSONFileHandler extends FileHandlerDecorator {

    public JSONFileHandler(FileHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String read(String fileName) throws Exception {
        if (fileName.endsWith(".json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File(fileName));

            StringBuilder result = new StringBuilder();
            JsonNode examples = root.get("mathExamples");
            if(examples!=null && examples.isArray()){
                for(JsonNode node: examples){
                    result.append(node.get("expression").asText().trim()).append("\n");            }
            }

            return result.toString().trim();
        }
        return super.read(fileName);
    }

    @Override
    public void write(String path, String data) throws Exception {
        if (path.endsWith(".json")) {
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
        } else {
            super.write(path, data);
        }
    }

}
