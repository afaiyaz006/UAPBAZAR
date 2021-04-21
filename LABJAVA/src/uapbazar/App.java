package uapbazar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.*;

public class App extends Application {
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Stage window=primaryStage;
	

		Parent root=FXMLLoader.load(getClass().getResource(View.Front.getFileName()));
	
		//StoreDataLoader.writeObject();
		StoreDataLoader.readObject();
		StoreDataLoader.store.clearCart();
		Scene beginScene=new Scene(root);
		SceneSwitcher.setScene(beginScene);
		
		window.setScene(beginScene);
		window.setWidth(1253);
		window.setHeight(600);
		window.show();
		
		
	}
	

	
	
	public static void main(String[] args) {
		launch(args);
	}

	
	
	
	

}