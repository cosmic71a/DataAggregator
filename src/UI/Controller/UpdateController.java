package UI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import UI.App;
import data.blogdata.BeInCryptoScraper;
import data.blogdata.BlockchainNewsScraper;
import data.blogdata.CoinDeskScraper;
import data.blogdata.FreightWavesScraper;
import data.blogdata.UTodayScraper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;

public class UpdateController implements Initializable {

    @FXML
    private CheckBox button1;

    @FXML
    private CheckBox button2;

    @FXML
    private CheckBox button3;

    @FXML
    private CheckBox button4;
    
    @FXML
    private CheckBox button5;

    @FXML
    private CheckBox buttonAll;

    @FXML
    private Button buttonBothFU;

    @FXML
    private Button buttonFetch;

    @FXML
    private Button buttonUpload;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void chooseAll(Event event) {
		if (buttonAll.isSelected()) {
			button1.setSelected(true);
			button2.setSelected(true);
			button3.setSelected(true);
			button4.setSelected(true);
			button5.setSelected(true);
		}
	}
	
	public void removeButtonAll(Event event) {
		if (button1.isSelected()||button2.isSelected()||button3.isSelected()||button4.isSelected()||button5.isSelected())
			buttonAll.setSelected(false);
	}
	
	public void askFetch(Event event) {
		Alert ask=new Alert(AlertType.CONFIRMATION); 
		ask.setTitle("Fetch Information");
		ask.setHeaderText("You're about to fetch the websites! (This action may affect your searching progress)");
		ask.setContentText("Do you really want to fetch?");
		if (ask.showAndWait().get()==ButtonType.OK) {
			System.out.println("Start extractor!");
			fetchData();
			finishFetch();
		}
	}
	
	private void fetchData() {
		if (button1.isSelected()) {
			System.out.println("Collecting data from BeInCrypto...");
			BeInCryptoScraper bic = new BeInCryptoScraper();
			bic.scrape();
			System.out.println("Blogs scraped on BeInCrypto: " + bic.getBlogScraped());
		}
		if (button2.isSelected()) {
			System.out.println("Collecting data from CoinDesk...");
			CoinDeskScraper coin = new CoinDeskScraper();
			coin.scrape();
			System.out.println("Blogs scraped on CoinDesk: " + coin.getBlogScraped());
		}
		if (button3.isSelected()) {
			System.out.println("Collecting data from FreightWaves...");
			FreightWavesScraper fws = new FreightWavesScraper();
			fws.scrape();
			System.out.println("Blogs scraped on FreightWaves: " + fws.getBlogScraped());
		}
		if (button4.isSelected()) {
			System.out.println("Collecting data from UToday...");
			UTodayScraper uto = new UTodayScraper();
			uto.scrape();
			System.out.println("Blogs scraped on UToday: " + uto.getBlogScraped());
		}
		if (button5.isSelected()) {
			System.out.println("Collecting data from BlockchainNews...");
			BlockchainNewsScraper blockchainnews = new BlockchainNewsScraper();
			System.out.println("Blogs scraped on BlockchainNews: " + blockchainnews.getBlogScraped());
			blockchainnews.scrape();
		}
	}
	
	private void finishFetch() {
		Alert ask=new Alert(AlertType.INFORMATION); 
		ask.setTitle("Fetch Information");
		ask.setHeaderText("Finish!");
		ask.setContentText("Press OK to skip");
		if (ask.showAndWait().get()==ButtonType.OK) {
			System.out.println("Finish!");
		}
	}
	
	public void askUpload(Event event) {
		Alert ask=new Alert(AlertType.CONFIRMATION); 
		ask.setTitle("Upload Information");
		ask.setHeaderText("You're about to update data from the Json file!");
		ask.setContentText("Do you really want to upload?");
		if (ask.showAndWait().get()==ButtonType.OK) {
			System.out.println("Start upload!");
			try {
				App.loadAllData();
				App.loadID++;
				finishUpload();
			} catch (IOException e) {}
		}
	}
	
	private void finishUpload() {
		Alert ask=new Alert(AlertType.INFORMATION); 
		ask.setTitle("Upload Information");
		ask.setHeaderText("Uploading successfully!");
		ask.setContentText("Press OK to skip");
		if (ask.showAndWait().get()==ButtonType.OK) {
			System.out.println("Finish upload!");
		}
	} 
	
	public void askBothFU(Event event) {
		Alert ask=new Alert(AlertType.CONFIRMATION); 
		ask.setTitle("Fetch & Upload Information");
		ask.setHeaderText("You're about to update data from the Json file and upload the data!");
		ask.setContentText("Do you really want to start?");
		if (ask.showAndWait().get()==ButtonType.OK) {
			System.out.println("Start fetch & upload!");
			fetchData();
			try {
				App.loadAllData();
				App.loadID++;
			} catch (IOException e) {}
			finishBothFU();
		}
	}
	
	private void finishBothFU() {
		Alert ask=new Alert(AlertType.INFORMATION); 
		ask.setTitle("Fetch & Upload Information");
		ask.setHeaderText("Fetched & Upload successfully!");
		ask.setContentText("Press OK to skip");
		if (ask.showAndWait().get()==ButtonType.OK) {
			System.out.println("Finish!");
		}
	}

}

