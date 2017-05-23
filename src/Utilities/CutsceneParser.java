package Utilities;

import Objects.Dialogue.Cutscene;
import Objects.Dialogue.ResponseNode;
import Objects.Dialogue.SpeechNode;
import Objects.Room;
import Utilities.Data.GameData;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.lang.model.util.Elements;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cacto on 5/12/2017.
 */
public class CutsceneParser {

    private Document document;
    private NodeList nodeList;
    private GameData gameData;

    public CutsceneParser(GameData gameData) throws IOException, SAXException, ParserConfigurationException {
        this.gameData = gameData;
        createDoc();
    }

    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("database/Dialogue.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }

    public Map<String,Cutscene> getCutscenes(){
        Map<String,Cutscene> cutscenes = new HashMap<>();
        nodeList = this.document.getElementsByTagName("Cutscene");
        for(int i = 0; i< nodeList.getLength();i++){
            Node cutInfo = nodeList.item(i);
            Element cutElement = (Element)cutInfo;
            Cutscene cutscene = new Cutscene(createCutsceneString(cutElement));
            cutscene.setAllSpeech(getSpeechNodes(cutElement));
            cutscenes.put(cutscene.getTitle(),cutscene);
        }
        return cutscenes;

    }

    private ArrayList<SpeechNode> getSpeechNodes(Element source){
        ArrayList<SpeechNode> speechNodes = new ArrayList<>();
        NodeList speechNodeList = source.getElementsByTagName("SpeechNode");
        for(int i = 0; i< speechNodeList.getLength();i++){
            Node speechInfo = speechNodeList.item(i);
            Element speechElement = (Element)speechInfo;
            SpeechNode speech = new SpeechNode(createSpeechNodeString(speechElement));
            speech.setResponses(getResponses(speechElement));
            speechNodes.add(speech);
        }
        return speechNodes;
    }

    private ArrayList<ResponseNode> getResponses(Element source){
        ArrayList<ResponseNode> responses = new ArrayList<>();
        NodeList speechNodes = source.getElementsByTagName("Response");
        for(int i = 0; i< speechNodes.getLength();i++){
            Node responseInfo = speechNodes.item(i);
            Element responseElement = (Element)responseInfo;
            ResponseNode response = new ResponseNode(createResponseString(responseElement));
            responses.add(response);
        }
        return responses;
    }

    private String createCutsceneString(Element cutElement) {
        return (cutElement.getAttribute("id") + "/" +
                cutElement.getAttribute("title") + "/" +
                cutElement.getAttribute("next"));
    }

    private String createSpeechNodeString(Element speechElement) {

        return (speechElement.getAttribute("id") + "/" +
                speechElement.getAttribute("text") + "/"+
                speechElement.getAttribute("jumpID"));
    }

    private String createResponseString(Element responseElement) {
        return (responseElement.getAttribute("text") + "/" +
                responseElement.getAttribute("jumpID"));
    }

}
