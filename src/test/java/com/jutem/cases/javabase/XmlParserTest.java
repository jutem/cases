package com.jutem.cases.javabase;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by eleme22 on 18/2/8.
 */
public class XmlParserTest {
    @Test
    public void createDocumentTest() throws FileNotFoundException {
        XmlParser parser = new XmlParser();
        String path = XmlParser.class.getClassLoader().getResource("mapper/UserMapper.xml").getPath();
        System.out.println("path : " + path);
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        Document doc = parser.createDocument(new InputSource(fileInputStream));

        Element mapper = doc.getDocumentElement();
        System.out.println("mapper: " + mapper.getNodeName());
        Node resultMap = mapper.getChildNodes().item(0);
        System.out.println(mapper.getChildNodes().getLength());
        System.out.println("result map : " + resultMap.getNodeName());
        NodeList nodeList = resultMap.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println(i + "  : " + node.getNodeName());
        }
    }
}
