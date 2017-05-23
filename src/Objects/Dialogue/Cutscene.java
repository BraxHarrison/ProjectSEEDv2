package Objects.Dialogue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cacto on 5/22/2017.
 */
public class Cutscene implements java.io.Serializable {

    private int id;
    private String title;
    private String next;
    private boolean isFinished;

    private SpeechNode currentSpeech;
    private ArrayList<SpeechNode> allSpeech;

    public Cutscene(String info){
        List<String> cutInfo = stringSplitter(info);
        id = Integer.parseInt(cutInfo.get(0));
        title = cutInfo.get(1);
        next = cutInfo.get(2);
        allSpeech = new ArrayList<SpeechNode>();
    }

    private List<String> stringSplitter(String info) {
        return Arrays.asList(info.split("/"));
    }

    public boolean canContinue(){
        if(currentSpeech.getJumpID() == -1){
            return false;
        }
        return true;
    }

    public void jump(int id){
        currentSpeech = allSpeech.get(id);

    }

    public void next(){
        currentSpeech = allSpeech.get(currentSpeech.getJumpID());

    }
    public void selectResponse(int responseNo){
        ResponseNode response = currentSpeech.getResponses().get(responseNo);
        currentSpeech = allSpeech.get(response.getJumpID());
    }
    public SpeechNode getCurrentSpeech(){
        return currentSpeech;
    }
    public void setAllSpeech(ArrayList<SpeechNode> allSpeech){
        this.allSpeech = allSpeech;
        currentSpeech = allSpeech.get(0);
    }
    public ArrayList<SpeechNode> getAllSpeech() {
        return allSpeech;
    }
    public String getTitle(){
        return title;
    }
    public String getNext() {
        return next;
    }
    public boolean isFinished() {
        return isFinished;
    }

    public void startOver() {
        currentSpeech = allSpeech.get(0);
    }
}
