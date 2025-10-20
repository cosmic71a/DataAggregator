package UI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.blogdata.BlogObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MenuController implements Initializable {
    @FXML
    private Button buttonAbout;

    @FXML
    private Button buttonTrend;

    @FXML
    private Button buttonHome;

    @FXML
    private Button buttonSearch;

    @FXML
    private Button buttonUpdate;        
    
    @FXML
    private AnchorPane paneAbout;

    @FXML
    private AnchorPane paneTrend;

    @FXML
    private AnchorPane paneHome;

    @FXML
    private AnchorPane paneSearch;

    @FXML
    private AnchorPane paneSearchRes;

    @FXML
    private AnchorPane paneUpdate;
    
    @FXML
    private StackPane stackPane;
    
    @FXML
    private Button buttonHideRes;
    
    private Pane currentPane;
    private ResultListener resultListener;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		resultListener= new ResultListener() {
			@Override
			public void onClickListener(BlogObject blogObject) {
//				System.out.println("I choose blog "+blogObject.getTitle());
				try {
					showBlog(blogObject);
				} catch (IOException e) {}
			}
		};
		
		try {
			setupPane("AboutPane.fxml",paneAbout);
			setupPane("HomePane.fxml", paneHome);
			setupPane("SearchPane.fxml", paneSearch,this.resultListener);
			setupPane("BlogView.fxml", paneSearchRes);
			setupPane("TrendingPane.fxml", paneTrend);
			setupPane("UpdatePane.fxml", paneUpdate);
		} catch (IOException e) {
			e.printStackTrace();
		}
		buttonHideRes.setVisible(false);
		paneHome.setVisible(true);
		currentPane=paneHome;
	}
	
	public void showBlog(BlogObject blogObject) throws IOException {
		buttonHideRes.setVisible(true);
		paneSearchRes.getChildren().clear();
		FXMLLoader loader= new FXMLLoader(getClass().getResource("/UI/Resource/BlogView.fxml"));
		AnchorPane tempPane=loader.load();
		BlogViewController ctrl=loader.getController();
		ctrl.setInformation(blogObject);
		paneSearchRes.getChildren().add(tempPane);
		paneSearchRes.setVisible(true);
	}
	
	public void hideBlog() {
		buttonHideRes.setVisible(false);
		paneSearchRes.setVisible(false);
	}
	
	private void setupPane(String resource,AnchorPane pane) throws IOException {
//		System.out.println(getClass().getResource("/UI/Resource/"+resource));
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/UI/Resource/"+resource));
		AnchorPane tempPane=loader.load();
		pane.getChildren().add(tempPane);
		pane.setVisible(false);
	}
	
	private void setupPane(String resource,AnchorPane pane,ResultListener resultListener) throws IOException {
//		System.out.println(getClass().getResource("/UI/Resource/"+resource));
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/UI/Resource/"+resource));
		AnchorPane tempPane=loader.load();
		if (pane==paneSearch) {
			SearchController ctrl=loader.getController();
			ctrl.setResultListener(resultListener);
		}
		pane.getChildren().add(tempPane);
		pane.setVisible(false);
	}
	
	public void handleClick(ActionEvent event) {
		currentPane.setVisible(false);		
		paneSearchRes.setVisible(false);
		buttonHideRes.setVisible(false);
		if (event.getSource()==buttonAbout) {
			currentPane=paneAbout;
		} else
		if (event.getSource()==buttonTrend) {
			currentPane=paneTrend;	
		} else
		if (event.getSource()==buttonHome) {
			currentPane=paneHome;
		} else
		if (event.getSource()==buttonSearch) {
			currentPane=paneSearch;
		} else
		if (event.getSource()==buttonUpdate) {
			currentPane=paneUpdate;
		}
		currentPane.setVisible(true);
	}
}
