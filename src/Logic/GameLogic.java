package Logic;


import Display.CutsceneUI;
import Display.MainMenuUI;
import Display.OverworldUI;
import Utilities.Data.GameData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class GameLogic {

    private Stage currentStage;
    private GameData gameData;
    private FileInputStream loadFile;
    private MainMenuUI menuUI;
    private OverworldUI overworldUI;
    private boolean canLoad;

    private Scene mainMenuScene;
    private Scene cutsceneScene;

    public void setCurrentStage(Stage stage){
        currentStage = stage;
    }

    public void setGameData(GameData data) {
        this.gameData = data;
        gameData.setStatus("null");
    }

    public void setUp() throws IOException {
        setStageToMainMenu();
    }

    public void setStageToCutscene(String cutscene) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/FXML/Cutscene.fxml").openStream());
        currentStage.setTitle("Project SEED: Chapter 1");
        currentStage.setScene(new Scene(root,900,600));
        CutsceneUI cutsceneUI = fxmlLoader.getController();
        cutsceneUI.init(this,cutscene);
        currentStage.show();
    }

    public void setStageToOverworld() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/FXML/Overworld.fxml").openStream());
        currentStage.setTitle("Project SEED: Area 1");
        currentStage.setScene(new Scene(root,900,600));
        OverworldUI overworldUI = fxmlLoader.getController();
        overworldUI.initialize(this);
        currentStage.show();
    }

    public void setStageToMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/FXML/MainMenu.fxml").openStream());
        currentStage.setTitle("Project SEED: Main Menu");
        mainMenuScene = new Scene(root,900,600);
        currentStage.setScene(mainMenuScene);
        menuUI = fxmlLoader.getController();
        menuUI.initialize(this);
        currentStage.show();
    }

    private void updateStageTitle() {
        //Add functionality for showing location/cutscene chapter here.
    }

//    private void initStage() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        Parent root = fxmlLoader.load(getClass().getResource("/FXML/MainMenu.fxml").openStream());
//        currentStage.setTitle("Project SEED: Main Menu");
//        mainMenuScene = new Scene(root,900,600);
//        currentStage.setScene(mainMenuScene);
//        menuUI = fxmlLoader.getController();
//        menuUI.initialize(this);
//        currentStage.show();
//    }

    public void play() throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        checkForSaveGame();
        menuUI.update();
    }

    private void checkForSaveGame() throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        try {
            loadFile = new FileInputStream("saveFile.sav");
            canLoad = true;
            gameData.setStatus("loaded");

        } catch (FileNotFoundException e) {
            canLoad = false;
        }
    }

    public void saveGame() throws IOException {
        FileOutputStream saveFile = new FileOutputStream("saveFile.sav");
        ObjectOutputStream save = new ObjectOutputStream(saveFile);
        save.writeObject(gameData);
        save.close();
    }
    public void loadGame() throws IOException, ClassNotFoundException {
        getFileToLoad();
        ObjectInputStream restore = new ObjectInputStream(loadFile);
        gameData = (GameData)restore.readObject();
        gameData.setStatus("loaded");
        setStageToOverworld();
    }

    private void getFileToLoad(){
        try {
            loadFile = new FileInputStream("saveFile.sav");
            canLoad = true;
            gameData.setStatus("loaded");

        } catch (FileNotFoundException e) {
            canLoad = false;
        }
    }

    public void newGame() throws IOException, SAXException, ParserConfigurationException {
        gameData.init();
        gameData.setCharacterName("Prota");
        saveGame();
        gameData.setStatus("loaded");
        setStageToCutscene("First Day On The Job");
    }

    private void startFromLoaded(){
        setOverworldAsStage();
        OverworldLogic overworldLogic = new OverworldLogic();
        overworldLogic.run();
    }
    private void setOverworldAsStage(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(getClass().getResource("/FXML/Overworld.fxml").openStream());
            setOverworldAsScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        overworldUI = fxmlLoader.getController();
        overworldUI.initialize(this);
    }

    private void setOverworldAsScene(Parent root) {
        currentStage.setTitle("Overworld");
        currentStage.setScene(new Scene(root, 900,600));
    }

    public boolean isLoadable(){
        return canLoad;
    }
    public GameData getGameData(){
        return gameData;
    }

}
