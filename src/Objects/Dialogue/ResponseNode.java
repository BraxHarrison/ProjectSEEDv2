package Objects.Dialogue;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cacto on 5/22/2017.
 */

public class ResponseNode implements java.io.Serializable {

    private int jumpID;
    private String text;

    public ResponseNode(String info){
        List<String> responseInfo = stringSplitter(info);
        text = responseInfo.get(0);
        jumpID = Integer.parseInt(responseInfo.get(1));
    }

    private List<String> stringSplitter(String info) {
        return Arrays.asList(info.split("/"));
    }

    public int getJumpID(){
        return jumpID;
    }
    public String getText() {
        return text;
    }
}
