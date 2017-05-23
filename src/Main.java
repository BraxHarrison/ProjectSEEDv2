import Logic.GameLogic;
import Utilities.Data.GameData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main extends Application {

    private GameLogic game = new GameLogic();
    private GameData data = new GameData();

    public Main() throws ParserConfigurationException, SAXException, IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        game.setCurrentStage(primaryStage);
        game.setGameData(data);
        game.setUp();
        game.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
