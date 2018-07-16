package com.example.heros.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * 实现将数据资源转换成xml文档
 * Created by DELL on 2018/7/10.
 */

public class HeroXml {
    private Document doc = null;//DOM文档
    private String encoding = "UTF-8";//文档采用的编码
    private Element root = null;//文档根结点

    public HeroXml() {
        this.initial();
    }

    //创建一个初始化方法，用来生一个DOM对象
    public void initial() {
        //创建一个文档构建器的工厂
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        //创建一个文档对象
        doc = builder.newDocument();
    }

    /**
     * 创建根节点
     * @param rootTagName 节点的名字
     * @return
     */
    public Element createRootElement(String rootTagName) {
        if(doc.getDocumentElement() == null){
            //创建一个指定标签名的元素
            root = doc.createElement(rootTagName);
            //把创建的元素追加到文档上
            doc.appendChild(root);
            return root;
        }
        //返回根节点
        return doc.getDocumentElement();
    }

    /**
     * 创建一个普通的子节点
     * @param tagName 子节点的名字
     * @param value 子节点的值
     * @return
     */
    public Element createElement(String tagName,String value){
        //获得根节点相关的dom文档
        Document doc = root.getOwnerDocument();
        //创建一个元素
        Element child = doc.createElement(tagName);
        //设置元素的文档内容
        child.setTextContent(value);
        //返回子元素
        return child;
    }

    /**
     * 创建一个非叶子的普通节点
     * @param tagName
     * @return
     */
    public Element createElement(String tagName){
        //获得和根节相关的dom对象
        Document doc = root.getOwnerDocument();
        Element child = doc.createElement(tagName);
        return child;
    }

    //返回xml格式的字符串
    public String xmlToString() throws Exception{
        String xmlString = null;
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();

        DOMSource source = new DOMSource(doc);

        StringWriter writer = new StringWriter();

        StreamResult result = new StreamResult(writer);

        transformer.setOutputProperty("encoding", encoding);

        transformer.transform(source, result);
        xmlString = writer.getBuffer().toString();
        return xmlString;
    }

    public Document getDoc() {
        return doc;
    }

    public String getEncoding() {
        return encoding;
    }

    public Element getRoot() {
        return root;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setRoot(Element root) {
        this.root = root;
    }
}
