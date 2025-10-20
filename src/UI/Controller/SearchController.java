package UI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import UI.App;
import data.blogdata.BlogObject;
import functions.function1.BlogComparatorByDate;
import functions.function2.SearchedBlogList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SearchController implements Initializable {

	 @FXML
	 private TextField CurrentPage;

	 @FXML
	 private Button NextButton;

	 @FXML
	 private Button PrevButton;

	 @FXML
	 private TextField SearchBar;

	 @FXML
	 private VBox ShowSpace;

	 @FXML
	 private Label TotalPage;
	 
	 @FXML
	 private ChoiceBox<String> FilterChoiceBox;
	 
	 private List<BlogObject> blogList=new ArrayList<>();
	 private int current=0,total=0,maxAmount=10;
	 private ResultListener resultListener;
	 private SearchedBlogList searchedBlogList=new SearchedBlogList(App.loadedBlogList.getBlogList());
	 private int loadID=0;
	 
	 @Override
	 public void initialize(URL arg0, ResourceBundle arg1) {
		 FilterChoiceBox.getItems().addAll("All","Newest","Oldest");
		 FilterChoiceBox.setValue("All");
		 FilterChoiceBox.setOnAction(arg01 -> {
			try {
				sortByFilter();
				setPage();
			} catch (IOException e) {System.out.println("error");}
		 });
		 
		 SearchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					try {showSearchRes(event);} catch (IOException e) {}
				}
			}		 
		 });
		 
		 CurrentPage.setOnKeyPressed(new EventHandler<KeyEvent>() {
			 @Override
			 public void handle(KeyEvent event) {	 	
				if (event.getCode().equals(KeyCode.ENTER)) {
					try {
						int wantedCurrent=Integer.parseInt(CurrentPage.getText());
						if (wantedCurrent>0&&wantedCurrent<=total) {
							current=wantedCurrent;
							setPage();
						}
					} catch (IOException e) {}
				}
			}	
		 });
		 
	 }
	 
	 public void setResultListener(ResultListener resultListener) {
		 this.resultListener=resultListener;
	 }
	 
	 private void searchWord(Event e) {
		 String target[]=SearchBar.getText().split(" ");
		 blogList=searchedBlogList.searchByKeywords(target, 1);
	 }
	 
	 private void sortByFilter() {
		 if (FilterChoiceBox.getValue().equals("All")) return;
		 blogList.sort(new BlogComparatorByDate());
		 if (FilterChoiceBox.getValue().equals("Newest"))
			 blogList=blogList.reversed();
	 }
	 
	 private void showPageNumber(int cur,int all) {
		 current=cur;
		 total=all;
		 CurrentPage.setText(Integer.toString(current));
		 TotalPage.setText("of "+Integer.toString(total));
	 }
	 
	 private void showResult(int begin,int end) throws IOException {
		 ShowSpace.getChildren().clear();
		 for (int i=begin;i<=end;i++) {
			  FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Resource/SearchSection.fxml"));
			  AnchorPane tempPane=loader.load();
			  SearchSectionController ctrl= loader.getController();
			  ctrl.setInformation(blogList.get(i), resultListener);
			  ShowSpace.getChildren().add(tempPane);
		  }
	 }
	 
	 private void setPage() throws IOException {
		 int begin=(current-1)*maxAmount,end=blogList.size()-1;
		 if (end>=begin+maxAmount) end=begin+maxAmount-1;
		 showResult(begin, end);
	 }
	
	 public void showSearchRes(Event e) throws IOException {
		 if (loadID!=App.loadID) {//Re-upload
			 searchedBlogList=new SearchedBlogList(App.loadedBlogList.getBlogList());
		 }
		 ShowSpace.getChildren().clear();
		 searchWord(e);
		 sortByFilter();
		  if (blogList.size()==0) {
			  showPageNumber(0, 0);
			  return;
		  }
		  
		  showPageNumber(1, (blogList.size()-1)/maxAmount+1);	  
		  setPage();
	 }
	 
	 public void showNext(Event e) throws IOException {
		 if (current>=total) return;
		 ShowSpace.getChildren().clear();
		 showPageNumber(current+1, total);
		 setPage();
	 }
	 
	 public void showPrev(Event e) throws IOException {
		 if (current<2) return;
		 ShowSpace.getChildren().clear();
		 showPageNumber(current-1, total);
		 setPage();
	 }
	 
	 public void showBegin() throws IOException {
		 if (current<2) return;
		 showPageNumber(1, total);
		 setPage();
	 }
	 
	 public void showEnd() throws IOException {
		 if (current>=total) return;
		 showPageNumber(total, total);
		 setPage();
	 }
}
