package com.mypackage.fileprocessor;

import java.io.*;
import java.util.*;
import org.yaml.snakeyaml.*;

public class YAMLFileHandler extends FileHandlerDecorator {

    public YAMLFileHandler(FileHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String read(String fileName) throws Exception {
        if (fileName.endsWith(".yaml")) {
            Yaml yaml = new Yaml();

            try(FileInputStream fis = new FileInputStream(fileName)){
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
        return super.read(fileName);
    }

    @Override
    public void write(String path, String data) throws Exception {
        if (path.endsWith(".yaml")) {
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
        } else {
            super.write(path, data);
        }
    }
}
