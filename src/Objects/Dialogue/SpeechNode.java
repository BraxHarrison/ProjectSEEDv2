package Objects.Dialogue;

import Utilities.Data.GameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cacto on 5/22/2017.
 */
public class SpeechNode implements java.io.Serializable {

    private GameData gameData;

    private boolean canContinue;
    private int nodeID;
    private int jumpID;
    private ArrayList<ResponseNode> responses;

    private String text;
    private String speakerName;
    private String speakerExpression;

    public SpeechNode(String info){
        List<String> speechInfo = splitString(info);
        nodeID = Integer.parseInt(speechInfo.get(0));
        text = speechInfo.get(1);
        jumpID = Integer.parseInt(speechInfo.get(2));
    }

    public String insertName(String name) {
        if(text.contains("$$$$")){
            text = text.replace("$$$$",name);
        }
        return text;

    }

    private List<String> splitString(String info) {
        return Arrays.asList(info.split("/"));
    }

    public boolean canContinue(){
        return canContinue;
   }

   public int getNodeID(){
       return nodeID;
   }

   public int getJumpID(){
       return jumpID;
   }

   public List<ResponseNode> getResponses(ArrayList<ResponseNode> responses){
       return this.responses;
    }

    public String getText(){
       return text;
    }

    public void setResponses(ArrayList<ResponseNode> responses){
        this.responses = responses;
    }

    public ArrayList<ResponseNode> getResponses(){
        return responses;
    }

    public void setJumpID(int id){
         jumpID = id;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }
}
