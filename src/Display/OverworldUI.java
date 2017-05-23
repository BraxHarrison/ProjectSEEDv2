package Display;

import Display.Prefabs.VertButtonList;
import Logic.GameLogic;
import Utilities.Data.GameData;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OverworldUI {

    public StackPane mainDisplay;
    public VBox testMenu;
    private GameLogic game;
    private GameData gameData;
    private VertButtonList vList;

    public void initialize(GameLogic game){
        this.game = game;
        this.gameData = game.getGameData();
        vList = new VertButtonList(testMenu);
        vList.addFighterButtons(gameData.getTeam());
    }


    public void addRoxy(ActionEvent actionEvent) {
        gameData.getTeam().add(gameData.getAllHeroes().get("Roxy"));
        vList.addFighterButtons(gameData.getTeam());
    }

    public void saveGame(ActionEvent actionEvent) throws IOException {
        game.saveGame();
    }
}
