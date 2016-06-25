package com.moral.airtree.update;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by bin.shen on 6/25/16.
 */
public class ParseXmlService {

    public HashMap<String, String> parseXml(String uri) throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        // 实例化一个文档构建器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 通过文档构建器工厂获取一个文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 通过文档通过文档构建器构建一个文档实例
        Document document = builder.parse(uri);
        //获取XML文件根节点
        Element root = document.getDocumentElement();
        //获得所有子节点
        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++) {
            //遍历子节点
            Node childNode = (Node) childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                if ("version".equals(childElement.getNodeName())) { //版本号
                    hashMap.put("version",childElement.getFirstChild().getNodeValue());
                } else if (("name".equals(childElement.getNodeName()))) { //软件名称
                    hashMap.put("name",childElement.getFirstChild().getNodeValue());
                } else if (("url".equals(childElement.getNodeName()))) { //下载地址
                    hashMap.put("url",childElement.getFirstChild().getNodeValue());
                }
            }
        }
        return hashMap;
    }
}