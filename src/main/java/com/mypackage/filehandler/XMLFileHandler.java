package com.mypackage.filehandler;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

import java.io.*;
import java.util.List;

public class XMLFileHandler extends FileHandlerDecorator {

    public XMLFileHandler(FileHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String read(String fileName) throws Exception {
        if (fileName.endsWith(".xml")) {
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(new File(fileName));

            Element root = doc.getRootElement();
            List<Element> elements = root.getChildren("example");
            StringBuilder result = new StringBuilder();
            for (Element e : elements) {
                String exp = e.getChildText("expression").trim();
                result.append(exp).append("\n");
            }

            return result.toString().trim();
        }
        return super.read(fileName);
    }

    @Override
    public void write(String path, String data) throws Exception {
        if (path.endsWith(".xml")) {
            Element root = new Element("results");
            Document doc = new Document(root);

            String[] results = data.split("\n");
            for (String result : results) {
                Element e = new Element("result");
                e.setText(result);
                root.addContent(e);
            }

            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            try (FileWriter fw = new FileWriter(path)) {
                outputter.output(doc, fw);
            }
        } else {
            super.write(path, data);
        }
    }
}
