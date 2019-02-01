package application;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ControllerMain {
	private boolean hamMenuIsOpen = false;

    @FXML
    private Pane menuPane;

    @FXML
    private JFXHamburger hamMenu;

    @FXML
    public void hamclicked(MouseEvent event) {
    	//menuPane.resize(menuPane.getWidth() + 100, menuPane.getHeight());
    	if(menuPane.getPrefWidth() == 400)
    		changeSize(menuPane, menuPane.getPrefWidth()-330, menuPane.getPrefHeight());
    	else if(menuPane.getPrefWidth() == 70)
    		changeSize(menuPane, menuPane.getPrefWidth()+330, menuPane.getPrefHeight());
    }
    
    public void initialize() {
    	HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamMenu);
    	transition.setRate(-1);
    	hamMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
    		transition.setRate(transition.getRate()*-1);
    		transition.play();
    	});
    }
    
    
    public void resizePane(Pane p, double w, double h) {
    	p.setPrefWidth(w);
    	p.setPrefHeight(h);
    }
    
    public void changeSize(final Pane pane, double width, double height) {
        hamMenu.setDisable(true);
    	Duration cycleDuration = Duration.millis(330);
        Timeline timeline = new Timeline(
                new KeyFrame(cycleDuration,
                        new KeyValue(pane.prefWidthProperty(),width,Interpolator.EASE_BOTH)),
                new KeyFrame(cycleDuration,
                        new KeyValue(pane.prefHeightProperty(),height,Interpolator.EASE_BOTH))
                );

        timeline.play();
        timeline.setOnFinished(event->{
        	hamMenu.setDisable(false);
        });
    }

}