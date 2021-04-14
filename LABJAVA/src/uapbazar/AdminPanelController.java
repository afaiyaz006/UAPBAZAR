package uapbazar;
import uapbazar.product.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale.Category;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import uapbazar.product.*;
public class AdminPanelController {
	public TabPane tabPane;
	public Button addNewItem;
	public Button backToLogin;
	public Button giveSale;
	
	/*@FXML
	public ListView<String> listView;
	public  ObservableList<String> itemList=FXCollections.observableList(StoreDataLoader.store.viewProducts(true));
	@FXML
	public ListView<String> foodList;
	public  ObservableList<String> fList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Food));
	@FXML
	public ListView<String> electronicsList;
	public  ObservableList<String>	eList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Electronics));
	@FXML
	public ListView<String> clothesList;
	public  ObservableList<String> cList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Cloth));
	*/
	public ListView<Product> listView;
	public  ObservableList<Product> itemList=FXCollections.observableList(StoreDataLoader.store.viewProducts(true));
	@FXML
	public ListView<Product> foodList;
	public ObservableList<Product> fList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Food));
	@FXML
	public ListView<Product> electronicsList;
	public  ObservableList<Product>	eList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Electronics));
	@FXML
	public ListView<Product> clothesList;
	public  ObservableList<Product> cList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Cloth));
	public static int getDays() {
		TextInputDialog dialog = new TextInputDialog("0");
		dialog.setTitle("Give Sale");
		dialog.setHeaderText("Confirm to give Sale");
		
		dialog.setContentText("Insert expire within days");

		
		Optional<String> result = dialog.showAndWait();
		
		
		return Integer.parseInt(result.get());

	}
	public static int saleDialogBox() {
		TextInputDialog dialog = new TextInputDialog("0");
		dialog.setTitle("Give Sale");
		dialog.setHeaderText("Confirm to give Sale");
		
		dialog.setContentText("Please enter sale percentage");

		
		Optional<String> result = dialog.showAndWait();
		
		
		return Integer.parseInt(result.get());
	}
	public void addNewItemClicked() {
		//SceneSwitcher.switchTo(View.ChooseItem);
		////DialogBox create
		Stage ownerStage=(Stage)addNewItem.getScene().getWindow();
		Stage newStage=new Stage();
		try {
			Parent root=FXMLLoader.load(getClass().getResource(View.ChooseItem.getFileName()));
			Scene sc=new Scene(root);
			newStage.initOwner(ownerStage);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.setScene(sc);
			SceneSwitcher.setScene(sc);
			newStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public void giveSaleClicked() {
		Product anyProduct=listView.getSelectionModel().getSelectedItem();
	
		Product electronicsProduct=electronicsList.getSelectionModel().getSelectedItem();
		Product clothProduct=clothesList.getSelectionModel().getSelectedItem();
		int tabNumber=tabPane.getSelectionModel().getSelectedIndex();
		if(tabNumber==0) {
			if(anyProduct.getCategory().equals(uapbazar.product.Category.Cloth)) {
				int percentage=saleDialogBox();
				String id=anyProduct.getId();
				StoreDataLoader.store.putOnSaleCloth(id, percentage);
				StoreDataLoader.writeObject();
			}
			else if(anyProduct.getCategory().equals(uapbazar.product.Category.Electronics)) {
				int percentage=saleDialogBox();
				String id=anyProduct.getId();
				StoreDataLoader.store.putOnSaleElectronic(id, percentage);
				StoreDataLoader.writeObject();
			}
			
			
			
		}
		else if(tabNumber==1) {
			int percentage=saleDialogBox();
			Integer days=getDays();
			StoreDataLoader.store.putOnSaleFood(days, percentage);
			StoreDataLoader.writeObject();
			
		}
		else if(tabNumber==2) {
			int percentage=saleDialogBox();
			String id=clothProduct.getId();
			StoreDataLoader.store.putOnSaleElectronic(id, percentage);
			StoreDataLoader.writeObject();
		}
		else if(tabNumber==3) {
			int percentage=saleDialogBox();
			String id=electronicsProduct.getId();
			StoreDataLoader.store.putOnSaleCloth(id, percentage);
			StoreDataLoader.writeObject();
			
		}
		SceneSwitcher.switchTo(View.Admin,false);
		
	}
	
	public void backToLoginClicked() {
		SceneSwitcher.setScene(backToLogin.getScene());
		SceneSwitcher.switchTo(View.Front);
	}
	
	
	@FXML
	public  void initialize() {
		listView.setItems(itemList);
		foodList.setItems(fList);
		electronicsList.setItems(eList);
		clothesList.setItems(cList);
		listView.setCellFactory(param -> new ListCell<Product>() {
		    @Override
		    protected void updateItem(Product p, boolean empty) {
		        super.updateItem(p,empty);

		        if (empty || p == null || p.details() == null) {
		            setText(null);
		        } else {
		        	//setStyle("-fx-background-color: green; -fx-text-fill: white");
		            setText(p.details());///sauce
		        }
		    }
		});
		foodList.setCellFactory(param -> new ListCell<Product>() {
		    @Override
		    protected void updateItem(Product item, boolean empty) {
		        super.updateItem(item,empty);

		        if (empty || item == null || item.details() == null) {
		            setText(null);
		        } else {
		        	//setStyle("-fx-background-color: green; -fx-text-fill: white");
		        	
		            setText(item.details());///sauce
		        }
		    }
		});
	
		electronicsList.setCellFactory(param -> new ListCell<Product>() {
		    @Override
		    protected void updateItem(Product item, boolean empty) {
		        super.updateItem(item,empty);

		        if (empty || item == null || item.details() == null) {
		            setText(null);
		        } else {
		        	//setStyle("-fx-background-color: green; -fx-text-fill: white");
		            setText(item.details());//sauce
		        }
		    }
		});
		clothesList.setCellFactory(param -> new ListCell<Product>() {
		    @Override
		    protected void updateItem(Product item, boolean empty) {
		        super.updateItem(item,empty);

		        if (empty || item == null || item.details() == null) {
		            setText(null);
		        } else {
		        	//setStyle("-fx-background-color: green; -fx-text-fill: white");
		            setText(item.details());
		        }
		    }
		});
        //listView.cellFactoryProperty();
		listView.setOnMouseClicked(e->System.out.println(listView.getSelectionModel().getSelectedItem().getId()));
	
		/*listView.setItems(itemList);
		foodList.setItems(fList);
		electronicsList.setItems(eList);
		clothesList.setItems(cList);*/
	    
			
		//StoreDataLoader.writeObject();
		
		//ArrayList<String> items=StoreDataLoader.store.viewProductsAsAdmin(uapbazar.product.Category.Electronics);
		
		
	}
	
	
	
}
