package UI.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import data.blogdata.BlogObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SearchSectionController implements Initializable {

    @FXML
    private Label LinkContent;

    @FXML
    private Hyperlink LinkTitle;

    @FXML
    private Label LinkURL;
    
    @FXML
    private void getBlog(MouseEvent event) {
    	resultListener.onClickListener(blogObject);
    }
    
    private BlogObject blogObject;
    private ResultListener resultListener;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
	}

    public void setTitle(String title) {
    	LinkTitle.setText(title);
    }
    
    public void setURL(String url) {
    	LinkURL.setText(url);
    }
    
    public void setContent(String content) {
    	LinkContent.setText(content);
    }
    
    public void setInformation(BlogObject blogObject,ResultListener resultListener) {
    	this.blogObject=blogObject;
    	this.resultListener=resultListener;
    	LinkTitle.setText(blogObject.getTitle());
    	LinkContent.setText(" "+ blogObject.getTime()+"|"+ blogObject.getContent());
    	LinkURL.setText(blogObject.getUrl());
    }
}