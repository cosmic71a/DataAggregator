package UI;

import java.io.IOException;

import functions.function1.JsonReader;
import functions.function1.LoadedBlogList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import paths.JsonFiles;

public class App extends Application {
	static public LoadedBlogList loadedBlogList;
	static public int loadID=0;

	@Override
	public void start(Stage primaryStage) throws IOException {
		loadAllData();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Resource/MainMenu.fxml"));
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("Resource/UpdatePane.fxml"));
		Parent root = loader.load();		
		Scene scene=new Scene(root);
		
		primaryStage.setOnCloseRequest(event-> {
			event.consume();
			logout(primaryStage);
		});
		
		primaryStage.setTitle("DataAggregator");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/UI/Resource/DataAggregatorIcon.png"));
	}
	
	private void logout(Stage stage) {
		Alert alert=new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Log out");
		alert.setHeaderText("You're about to log out!");
		alert.setContentText("Do you really want to exit?");
		if (alert.showAndWait().get()==ButtonType.OK) {
			System.out.println("You logged out successfully!");
			stage.close();
		}
	}
	static public void loadAllData() throws IOException {
		loadedBlogList=new LoadedBlogList();
		String tempString= JsonReader.readJsonFile(JsonFiles.BEINCRYPTO);
		loadedBlogList.loadData(tempString);
		tempString= JsonReader.readJsonFile(JsonFiles.COINDESK);
		loadedBlogList.loadData(tempString);
		tempString= JsonReader.readJsonFile(JsonFiles.UTODAY);
		loadedBlogList.loadData(tempString);
		tempString= JsonReader.readJsonFile(JsonFiles.FREIGHTWAVES);
		loadedBlogList.loadData(tempString);
		tempString= JsonReader.readJsonFile(JsonFiles.BLOCKCHAINNEWS);
		loadedBlogList.loadData(tempString);
	}

	static public void main(String[] args) {
		launch(args);
	}
}
