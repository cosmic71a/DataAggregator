package UI.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HomeController implements Initializable {

    @FXML
    private RadioButton button1;

    @FXML
    private RadioButton button2;

    @FXML
    private RadioButton button3;

    @FXML
    private RadioButton button4;
    
    @FXML
    private ImageView slideBackView;

    @FXML
    private ImageView slideFrontView;
    
    final private Image image1= new Image("/UI/Resource/Intro.png");
    final private Image image2= new Image("/UI/Resource/Fetch.png");
    final private Image image3= new Image("/UI/Resource/Search.png");
    final private Image image4= new Image("/UI/Resource/Trends.png");
    private RadioButton curButton;
    private FadeTransition fadeTransition = new FadeTransition();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		curButton=button1;
		curButton.setSelected(true);
		changeSlide(null);
		
		fadeTransition.setDuration(Duration.millis(1000));
		fadeTransition.setCycleCount(1);
		fadeTransition.setInterpolator(Interpolator.LINEAR);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
	}
	
	RadioButton nextButton(RadioButton button) {
		if (button==button1) return button2;
		if (button==button2) return button3;
		if (button==button3) return button4;
		return button1;
	}
	
	public void clickHandle() {
		curButton=nextButton(curButton);
		curButton.setSelected(true);
		changeSlide(null);
	}
	
	public void changeSlide(ActionEvent event) {
		if (button1.isSelected()) {
			fadeTransition.setNode(slideFrontView);
			fadeTransition.play();
			slideFrontView.setImage(image1);
		} else
		if (button2.isSelected()) {
			fadeTransition.setNode(slideFrontView);
			fadeTransition.play();
			slideFrontView.setImage(image2);	
		} else
		if (button3.isSelected()) {
			fadeTransition.setNode(slideFrontView);
			fadeTransition.play();
			slideFrontView.setImage(image3);
		} else
		if (button4.isSelected()) {
			fadeTransition.setNode(slideFrontView);
			fadeTransition.play();
			slideFrontView.setImage(image4);
		}
	}

}
