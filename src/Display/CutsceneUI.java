package Display;

import Logic.GameLogic;
import Objects.Dialogue.Cutscene;
import Objects.Dialogue.ResponseNode;
import Utilities.CutsceneAnimator;
import Utilities.Data.GameData;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

/**
 * Created by cacto on 5/12/2017.
 */
public class CutsceneUI {

    public StackPane mainDisplay;
    public Label currentText;
    public StackPane textDisplayArea;
    public ImageView advanceButton;
    public Label textLabel;
    public VBox choiceArea;
    public StackPane choiceShell;
    public ImageView choiceButton;
    public ImageView speakerPortrait;
    public StackPane transitionScreen;
    private boolean choiceMenuExtended;
    private String previousEmote;

    private Cutscene currentCutscene;

    private GameLogic game;
    private GameData gameData;
    private CutsceneAnimator animator;

    public void init(GameLogic game,String cutscene){
        this.game = game;
        this.gameData = game.getGameData();
        animator = new CutsceneAnimator(this);
        currentCutscene = gameData.getAllCutscenes().get(cutscene);
        choiceMenuExtended = false;
        updateTextArea();
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        game.setStageToMainMenu();
    }

    private void updateTextArea(){
        choiceArea.getChildren().clear();
        currentCutscene.getCurrentSpeech().setGameData(gameData);
        checkForAdvance();
        textLabel.setText(currentCutscene.getCurrentSpeech().getText());
        updateSpeakerPortrait();

    }

    private void updateSpeakerPortrait() {
        String emote = currentCutscene.getCurrentSpeech().getSpeaker1Path();
        if(!emote.equals("null")){
            speakerPortrait.setVisible(true);
            speakerPortrait.setImage(new Image(emote));
        }
        else{
            speakerPortrait.setVisible(false);
        }
    }

    //There's probably some janky code here that's tied up with a duplicate in Animator
    public void checkForAdvance(){
        if(!currentCutscene.canContinue()){
            advanceButton.setVisible(false);
            createChoiceSelectors();
        }
    }

    private void createChoiceSelectors() {
        choiceArea.getChildren().clear();
        List<ResponseNode> responses = currentCutscene.getCurrentSpeech().getResponses();
        for(int i =0;i<responses.size();i++){
            int index = i;
            Label choice = new Label(responses.get(i).getText());
            formatChoiceLabel(choice);
            choice.setOnMousePressed(e->selectChoice(index));
            choiceArea.getChildren().add(choice);
        }
    }

    private void formatChoiceLabel(Label choice) {
        choice.getStyleClass().add("choice-font");
    }

    private void selectChoice(int index) {
        ResponseNode selected = currentCutscene.getCurrentSpeech().getResponses().get(index);
        int destination = selected.getJumpID();
        animator.animateChoiceMenuSlideIn(choiceButton,choiceShell);
        choiceMenuExtended = false;
        currentCutscene.jump(destination);
        updateTextArea();
    }

    public void nextText(MouseEvent actionEvent) {
        if(currentCutscene.getCurrentSpeech().getJumpID()==-2){
            currentCutscene = gameData.getAllCutscenes().get(currentCutscene.getNext());
            currentCutscene.startOver();
        }
        else{
            currentCutscene.next();
        }
        checkForCharacterName();
        updateTextArea();
    }

    private void checkForCharacterName() {
        currentCutscene.getCurrentSpeech().insertName(gameData.getCharacterName());
    }

    public void showChoices(MouseEvent mouseEvent) {
        if(!choiceMenuExtended){
            animator.animateChoiceMenuSlideOut(choiceButton,choiceShell);
        }
        else{
            animator.animateChoiceMenuSlideIn(choiceButton,choiceShell);
        }
        choiceMenuExtended = !choiceMenuExtended;

    }

    public Cutscene getCurrentCutscene(){
        return currentCutscene;
    }
}
