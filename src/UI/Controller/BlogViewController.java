package UI.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import data.blogdata.BlogObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class BlogViewController implements Initializable {
	@FXML
    private Label authorTime;

    @FXML
    private Button backButton;

    @FXML
    private Label contentLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField urlText;

    @FXML
    private FlowPane keywordSpace;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void setInformation(BlogObject blogObject) {
		titleLabel.setText(blogObject.getTitle());
    	contentLabel.setText(blogObject.getContent());
    	urlText.setText(blogObject.getUrl());
    	authorTime.setText("Posted by "+blogObject.getAuthor()+", "+
    			blogObject.getTime());
    	keywordSpace.getChildren().clear();
    	keywordSpace.getChildren().add(new Label("Keywords: |"));
    	for (String tempKeyword: blogObject.getKeywords()) {
    		keywordSpace.getChildren().add(new Label(tempKeyword+"| "));
    	}
	}

}

