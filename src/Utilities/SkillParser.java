package Utilities;

import Objects.Skill;
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

/**
 * Created by cacto on 5/12/2017.
 */
public class SkillParser {

    public SkillParser() throws IOException, SAXException, ParserConfigurationException {
        createDoc();
    }

    private Document document;

    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Database/GameInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }
    
    public HashMap<String, Skill> loadSkills(GameData gameData) {
        HashMap<String,Skill> skills = new HashMap<>();
        NodeList nodeList = this.document.getElementsByTagName("Skill");
        for(int i = 0; i< nodeList.getLength();i++){
            Node battleInfo = nodeList.item(i);
            Element skillSource = (Element)battleInfo;
            Skill skill = new Skill(createSkillString(skillSource),gameData);
            skills.put(skill.getName(),skill);
        }
        return skills;
    }

    private String createSkillString(Element skill){

        return (skill.getAttribute("name") + ",") +
                skill.getAttribute("affectAmt") + "," +
                skill.getAttribute("tpCost") + "," +
                skill.getAttribute("element") + "," +
                skill.getAttribute("type") + "," +
                skill.getAttribute("type2") + "," +
                skill.getAttribute("type3") + "," +
                skill.getAttribute("type4f") + "," +
                skill.getAttribute("chance") + "," +
                skill.getAttribute("quickInfo") + "," +
                skill.getAttribute("extraMessage") + "," +
                skill.getAttribute("animType");
    }
}
