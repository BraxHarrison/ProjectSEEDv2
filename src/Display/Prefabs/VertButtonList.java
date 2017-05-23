package Display.Prefabs;

import Objects.Fighter;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by cacto on 5/19/2017.
 */
public class VertButtonList {

    private VBox box;
    private ScrollPane scroll;

    private double width;
    private double height;
    private String alignment;
    private String type;
    private Pane mainDisplay;

    public VertButtonList(Pane mainDisplay){
        this.mainDisplay = mainDisplay;
        instantiate();
    }


    public VertButtonList(Pane mainDisplay,String info){
        this.mainDisplay = mainDisplay;
        instantiate();
        List<String> infoList = splitString(info);
        width = Double.parseDouble(infoList.get(0));
        height = Double.parseDouble(infoList.get(1));
        alignment = infoList.get(2);
        System.out.println(width);
        autoCreate();
    }

    private void instantiate(){
        scroll = new ScrollPane();
        box = new VBox();
        scroll.setContent(box);
        mainDisplay.getChildren().add(scroll);
    }

    private void autoCreate() {
        setSize(width,height);
        setAlignment(alignment);
    }

    private List<String> splitString(String info) {
        return Arrays.asList(info.split(","));
    }

    public void addAll(ArrayList<Node> list){
        box.getChildren().addAll(list);
    }
    public void add(Node node){
        box.getChildren().add(node);
    }
    public void setSize(double width, double height){
        scroll.setMaxWidth(width);
        scroll.setMaxHeight(height);
    }
    public void setAlignment(String align){
        StackPane.setAlignment(scroll,Pos.valueOf(align));
    }

    public void translate(double x, double y){
        scroll.setTranslateX(x);
        scroll.setTranslateY(y);
    }

    public void addFighterButtons(ArrayList<Fighter> fighters){
        box.getChildren().clear();
        for(int i = 0; i< fighters.size();i++){
            StackPane fighterButton = new StackPane();
            Label name = new Label(fighters.get(i).getName());
            fighterButton.getChildren().add(name);
            formatButton(fighterButton);
            //fighterButton.setOnMousePressed(e->selectMethodType());
            box.getChildren().add(fighterButton);
        }
    }

    private void formatButton(StackPane fighterButton) {
        fighterButton.setMaxWidth(300);
        fighterButton.setMaxHeight(100);
        fighterButton.getStyleClass().add("list-button");
    }

    private void selectMethodType() {
        switch (type) {
            case "selectItem":

                break;

            case "selectItemTarget":

                break;

            case "selectHero":

                break;

            case "selectMoveTarget":

                break;
        }
    }
}
