package Display;

import Logic.GameLogic;
import Utilities.Data.GameData;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MainMenuUI {

    public Button newGameButton;
    public StackPane yes;
    public Button loadGameButton;
    public Label test;

    private GameLogic game;
    private GameData gameData;

    public void initialize(GameLogic gameLogic) {
        game = gameLogic;
        gameData = game.getGameData();
    }
    public void checkCanLoad(){
        if(!game.isLoadable()){
            loadGameButton.setVisible(false);
        }
    }

    public void triggerNewGame(ActionEvent actionEvent) throws ParserConfigurationException, SAXException, IOException {
        game.newGame();
    }

    public void triggerLoadGame(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        game.loadGame();
    }

    public void update() {
        checkCanLoad();
    }
}
