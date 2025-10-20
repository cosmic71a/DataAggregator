package UI.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import UI.App;
import functions.function3.KeywordObject;
import functions.function3.TrendingKeywords;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TrendingController implements Initializable {

    @FXML
    private BarChart<String, Integer> TrendChart;

    @FXML
    private Label announcementLabel;

    @FXML
    private DatePicker endTime;

    @FXML
    private DatePicker startTime;
    
    @FXML
    private Label ListTitle;
    
    @FXML
    private VBox timeSpace;
    
    @FXML
    private VBox keywordSpace;
    
    private TrendingKeywords trendingKeywords= new TrendingKeywords(App.loadedBlogList.getBlogList());
    private List<KeywordObject> keywordList;
    private int loadID=0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		endTime.setValue(LocalDate.now());
//		TrendChart.setAnimated(false);
		TrendChart.getXAxis().setAnimated(false);
	}

	public void showInformation(Event event) {
		if (loadID!=App.loadID) {//Re-upload data 
			trendingKeywords= new TrendingKeywords(App.loadedBlogList.getBlogList());
		}
		try {
			keywordList=trendingKeywords.findTrendingKeywords(
					startTime.getValue().toString(), endTime.getValue().toString());
			setChart();
			setList();
			if (keywordList.size()==0) announcementLabel.setText("No articles are found in this period!");
				else announcementLabel.setText("");
		} catch (Exception exception) {
			announcementLabel.setText("Please choose/type the date carefully");
		}
	}
	
	public void setChart() {
		TrendChart.getData().clear();
		XYChart.Series<String,Integer> series= new XYChart.Series<String,Integer>();
		int numberTop=0;
		for (KeywordObject element : keywordList) {
			series.getData().add(new XYChart.Data<String,Integer>(element.getFoundKeyword(),element.getTimeAppears()));
			numberTop++;
			if (numberTop==7) break;
		}
		series.setName(Integer.toString(numberTop)+" top keywords");
		TrendChart.getData().add(series);
	}
	
	public void setList() {
		timeSpace.getChildren().clear();
		keywordSpace.getChildren().clear();
		int maxAmount=500;
		if (maxAmount>keywordList.size()) maxAmount=keywordList.size();
		ListTitle.setText("List of "+Integer.toString(maxAmount)+" most polupar keywords");
		for (KeywordObject element : keywordList) {
			Label timeLabel= new Label(Integer.toString(element.getTimeAppears()));
			timeSpace.getChildren().add(timeLabel);
			Label kwLabel= new Label(element.getFoundKeyword());
			keywordSpace.getChildren().add(kwLabel);
			maxAmount--;
			if (maxAmount==0) break;
		}
	}
	
}
