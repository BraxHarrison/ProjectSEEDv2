package Utilities;

import Objects.Fighter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cacto on 5/12/2017.
 */
public class CharacterParser {


    private final HashMap<String, Skill> skills;
    private String key;
    private Document document;

    public CharacterParser(GameData data) throws IOException, SAXException, ParserConfigurationException {
        createDoc();
        skills = data.getAllSkills();
    }

    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("database/GameInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }

    public HashMap<String, Fighter> loadFighters(String key){
        this.key = key;
        HashMap<String,Fighter> fighters = new HashMap<>();
        NodeList nodeList = this.document.getElementsByTagName(key);
        for(int i = 0; i< nodeList.getLength();i++){
            Node battleInfo = nodeList.item(i);
            Element battler = (Element)battleInfo;
            Fighter fighter = new Fighter(createFighterString(battler));
            fighters.put(fighter.getName(),fighter);
            setStarterSkills(fighter,i);
        }
        return fighters;
    }

    private String createFighterString(Element battler) {

        return (battler.getAttribute("name") + ",") +
                battler.getAttribute("maxHP") + "," +
                battler.getAttribute("attack") + "," +
                battler.getAttribute("defense") + "," +
                battler.getAttribute("enAttack") + "," +
                battler.getAttribute("enDefense") + "," +
                battler.getAttribute("agility") + "," +
                battler.getAttribute("tpCost") + "," +
                battler.getAttribute("expModifier") + "," +
                battler.getAttribute("weakness") + "," +
                battler.getAttribute("strength") + "," +
                battler.getAttribute("battlerGraphicPath") + "," +
                battler.getAttribute("miniGraphicPath") + "," +
                battler.getAttribute("sizeX") + "," +
                battler.getAttribute("sizeY") + "," +
                battler.getAttribute("rewardAmt");
    }

    private void setStarterSkills(Fighter fighter, int index){
        List<String> starterSkillNames = loadStarterSkills(index);
        ArrayList<Skill> skills = fetchSkills(starterSkillNames);
        for (Skill skill : skills) {
            fighter.addSkill(skill);
        }
    }

    private ArrayList<Skill> fetchSkills(List<String> skillStrings){
        ArrayList<Skill> fullSkills = new ArrayList<>();
        for (String skillName : skillStrings) {
            fullSkills.add(skills.get(skillName));
        }
        return fullSkills;
    }

    private List<String> loadStarterSkills(int index){
        NodeList nodeList = this.document.getElementsByTagName(key + "Skill");
        Element skillStringSource = (Element)nodeList.item(index);
        String skillString = skillStringSource.getAttribute("starter");
        return Arrays.asList(skillString.split(","));
    }
}
