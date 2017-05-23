package Utilities;

import Objects.Item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by cacto on 5/12/2017.
 */
public class ItemParser {

    public ItemParser() throws IOException, SAXException, ParserConfigurationException {
        createDoc();
    }

    private Document document;

    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("database/GameInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }

    public HashMap<String, Item> createItemDatabase(){
        HashMap<String, Item> items = new HashMap<>();
        NodeList nodeList = document.getElementsByTagName("item");
        for(int i = 0; i< nodeList.getLength(); i++){
            Node itemInfo = nodeList.item(i);
            Element sourceItem = (Element)itemInfo;
            Item item = new Item(createItemString(sourceItem));
            items.put(item.getName(),item);
        }
        return items;
    }

    private String createItemString(Element sourceItem) {
        return (sourceItem.getAttribute("name") + ",") +
                sourceItem.getAttribute("description") + "," +
                sourceItem.getAttribute("quickSummary") + "," +
                sourceItem.getAttribute("affectAmt") + "," +
                sourceItem.getAttribute("buyPrice") + "," +
                sourceItem.getAttribute("type") + "," +
                sourceItem.getAttribute("type2") + "," +
                sourceItem.getAttribute("imagePath") + ",";
    }
}
