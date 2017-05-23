package Utilities.Data;

import Objects.*;
import Objects.Dialogue.Cutscene;
import Utilities.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GameData implements java.io.Serializable {

    private ArrayList<Fighter> team;
    private ArrayList<Fighter> standby;
    private ArrayList<Fighter> enemyTeam;
    private Wallet wallet;

    private HashMap<String,Fighter> allHeroes;
    private HashMap<String,Fighter> allEnemies;
    private HashMap<String, Item> allItems;
    private HashMap<String, Event> allEvents;
    private HashMap<String,Skill> allSkills;
    private ArrayList<Item> inventory;
    private Map<String, Room> allRooms;
    private Map<String,Cutscene> allCutscenes;
    private Room currentRoom;
    private int tp;
    private int tempMaxTP;
    private int maxTP;
    private int selectedTarget;
    private String status;
    private String characterName;


    public GameData() throws IOException, SAXException, ParserConfigurationException {
    }

    public void init() throws ParserConfigurationException, SAXException, IOException {
        initLists();
        loadData();
        addHeroes();
        initItems();
        calcTP();
        wallet = new Wallet();
    }

    private void loadData() throws ParserConfigurationException, SAXException, IOException {
        loadSkills();
        loadFighters();
        loadItems();
        loadEvents();
        loadRooms();
        loadCutscenes();

    }

    private void loadCutscenes() throws ParserConfigurationException, SAXException, IOException {
        CutsceneParser cutsceneParser = new CutsceneParser(this);
        allCutscenes = cutsceneParser.getCutscenes();
    }

    private void loadEvents() throws ParserConfigurationException, SAXException, IOException {
        EventParser eventParser = new EventParser();
        allEvents = eventParser.createEventDatabase(this);
    }

    private void loadRooms() throws ParserConfigurationException, SAXException, IOException {
        RoomParser roomParser = new RoomParser();
        allRooms = roomParser.createRoomMap();

    }

    private void loadItems() throws ParserConfigurationException, SAXException, IOException {
        ItemParser itemParser = new ItemParser();
        allItems = itemParser.createItemDatabase();
    }

    private void loadSkills() throws ParserConfigurationException, SAXException, IOException {
        SkillParser skillParser = new SkillParser();
        allSkills = skillParser.loadSkills(this);
    }

    private void loadFighters() throws ParserConfigurationException, SAXException, IOException {
        CharacterParser charParser = new CharacterParser(this);
        allHeroes = charParser.loadFighters("hero");
        allEnemies = charParser.loadFighters("enemy");
    }

    public void calcTP(){
        maxTP = 0;
        for (Fighter fighter : team) {
            maxTP += fighter.getAgility();
        }
        tp = maxTP;
        tempMaxTP = maxTP;
    }

    void subtractTP(int amount){
        tp -= amount;
    }

    void tempIncreaseMaxTP(int amount){
        tempMaxTP+=amount;
    }

    void revertMaxTP(){
        tempMaxTP=maxTP;
    }


    private void initLists() {
        team = new ArrayList<>();
        standby = new ArrayList<>();
        allItems = new HashMap<>();
        allEvents = new HashMap<>();
        inventory = new ArrayList<>();
        enemyTeam = new ArrayList<>();
        allCutscenes = new HashMap<>();
    }

    private void addHeroes(){

        team.add(allHeroes.get("Prota"));
        standby.add(allHeroes.get("Blake"));
        standby.add(allHeroes.get("Roxy"));
        standby.add(allHeroes.get("Smitty"));
        standby.add(allHeroes.get("Nicole"));
    }

    void addEnemies(){
        enemyTeam.clear();
        switch (currentRoom.getName()) {
            case "Colossal Plains":
                enemyTeam.add(new Fighter(allEnemies.get("Skraw")));
                enemyTeam.add(new Fighter(allEnemies.get("Jag")));
                break;
            case "Luminous Caves":
                enemyTeam.add(new Fighter(allEnemies.get("Harshmallow")));
                enemyTeam.add(new Fighter(allEnemies.get("Oculith")));
                break;
            case "Forest Clearing":
                enemyTeam.add(new Fighter(allEnemies.get("Blisterbulb")));
                enemyTeam.add(new Fighter(allEnemies.get("Eaflay")));
                break;
            case "Conchbreak Key":
                enemyTeam.add(new Fighter(allEnemies.get("Apparacean")));
                enemyTeam.add(new Fighter(allEnemies.get("Apparacean")));
                break;
            default:
                enemyTeam.add(new Fighter(allEnemies.get("Jag")));
                break;
        }
    }

    void removeObjectFromInventory(int index){
        inventory.remove(index);
    }

    private void initItems() {
        inventory.add(allItems.get("Patch"));
    }

    void resetHeroTP(){
        tp = tempMaxTP;
    }

    public Map<String, Room> getAllRooms(){return allRooms;}
    public Room getCurrentRoom() {
        return currentRoom;
    }
    void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
    public ArrayList<Fighter> getTeam() {
        return team;
    }
    public ArrayList<Fighter> getStandby() {
        return standby;
    }
    public ArrayList<Fighter> getEnemyTeam() {
        return enemyTeam;
    }
    public HashMap<String, Fighter> getAllHeroes() {
        return allHeroes;
    }
    public HashMap<String, Skill> getAllSkills() {
        return allSkills;
    }
    public int getTempMaxTP() {
        return tempMaxTP;
    }
    public int getCurrentTp() {
        return tp;
    }
    int getSelectedTarget() {
        return selectedTarget;
    }
    public void setSelectedTarget(int selectedTarget) {
        this.selectedTarget = selectedTarget;
    }
    void subtractTp(int cost) {
        tp -= cost;
        if(tp < 0){
            tp = 0;
        }
    }
    public HashMap<String, Item> getAllItems() {
        return allItems;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    public HashMap<String, Event> getAllEvents() {
        return allEvents;
    }
    public Map<String,Cutscene> getAllCutscenes(){
        return allCutscenes;
    }
    public Wallet getWallet(){
        return wallet;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
    public String getCharacterName() {
        return characterName;
    }
}

