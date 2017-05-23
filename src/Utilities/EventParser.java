package Utilities;

import Objects.Event;
import Utilities.Data.GameData;
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


public class EventParser {

    private Document document;

    public EventParser() throws IOException, SAXException, ParserConfigurationException {
        createDoc();
    }

    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("database/GameInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }

    public HashMap<String, Event> createEventDatabase(GameData gameData){
        HashMap<String, Event> events = new HashMap<>();
        NodeList nodeList = document.getElementsByTagName("event");
        for(int i = 0; i<nodeList.getLength();i++){
            Node eventInfo = nodeList.item(i);
            Element sourceEvent = (Element)eventInfo;
            Event event = new Event(createEventString(sourceEvent),gameData);
            events.put(event.getName(),event);
        }
        return events;
    }

    private String createEventString(Element sourceEvent) {
        return (sourceEvent.getAttribute("name") + "," +
                sourceEvent.getAttribute("type") + "," +
                sourceEvent.getAttribute("stock") + "," +
                sourceEvent.getAttribute("sell") + "," +
                sourceEvent.getAttribute("cluster") + "," +
                sourceEvent.getAttribute("imagePath")
        );
    }
}
