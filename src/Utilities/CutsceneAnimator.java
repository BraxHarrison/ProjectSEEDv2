package Utilities;

import Display.CutsceneUI;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.Stack;

public class CutsceneAnimator {

    CutsceneUI ui;

    public CutsceneAnimator(CutsceneUI ui){
        this.ui = ui;
    }

    public void animateChoiceMenuSlideOut(ImageView button, StackPane shell){
        Timeline timeline = new Timeline();
        KeyValue buttonSlideOut = new KeyValue(button.translateYProperty(),button.getTranslateY()-150, Interpolator.EASE_BOTH);
        KeyValue shellSlideOut = new KeyValue(shell.translateYProperty(),shell.getTranslateY()-150, Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(240),buttonSlideOut,shellSlideOut);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();

    }
    public void animateChoiceMenuSlideIn(ImageView button, StackPane shell){
        Timeline timeline = new Timeline();
        KeyValue buttonSlideOut = new KeyValue(button.translateYProperty(),button.getTranslateY()+150, Interpolator.EASE_BOTH);
        KeyValue shellSlideOut = new KeyValue(shell.translateYProperty(),shell.getTranslateY()+150, Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(240),buttonSlideOut,shellSlideOut);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.setOnFinished(e->finishChoiceMenuSlide());
        timeline.play();
    }
    private void finishChoiceMenuSlide() {
        if(ui.getCurrentCutscene().canContinue()){
            ui.advanceButton.setVisible(true);
        }
        else{
            ui.advanceButton.setVisible(false);
        }
        ui.checkForAdvance();

    }

    public void transitionScreen(){

    }


}
