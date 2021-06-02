package uapbazar;
import uapbazar.Store.*;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	public ObservableList<Product> fList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.Store.Category.Food));
	@FXML
	public ListView<Product> electronicsList;
	public  ObservableList<Product>	eList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.Store.Category.Electronics));
	@FXML
	public ListView<Product> clothesList;
	public  ObservableList<Product> cList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.Store.Category.Cloth));
	
	public static void showDialog(String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Invalid  Data");
		alert.setHeaderText("Warrning");
		alert.setContentText(msg);

		alert.showAndWait();
	}
	public void setGiveSaleButton(boolean isFoodItem){
		if(isFoodItem==true) {
			giveSale.setText("Give Sale(All)");
		}
		else{
			giveSale.setText("Give Sale");
		}

	}
	public void refreshList(){

		listView.getSelectionModel().clearSelection();
		foodList.getSelectionModel().clearSelection();
		electronicsList.getSelectionModel().clearSelection();
		clothesList.getSelectionModel().clearSelection();
		itemList=FXCollections.observableList(StoreDataLoader.store.viewProducts(true));
		fList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.Store.Category.Food));
		eList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.Store.Category.Electronics));
		cList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.Store.Category.Cloth));


	}
	public static int getDays() {
		TextInputDialog dialog = new TextInputDialog("0");
		dialog.setTitle("Give Sale");
		dialog.setHeaderText("Confirm to give Sale(This will give sale to all food items)");
		
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
			System.out.println(e.getMessage());
		}
		
		
		
	}
	public void giveSaleClicked() {
		Product anyProduct=listView.getSelectionModel().getSelectedItem();

		Product electronicsProduct=electronicsList.getSelectionModel().getSelectedItem();
		Product clothProduct=clothesList.getSelectionModel().getSelectedItem();
		Product foodProduct=foodList.getSelectionModel().getSelectedItem();
		int tabNumber=tabPane.getSelectionModel().getSelectedIndex();
		System.out.println(tabNumber);
		if(tabNumber==0 && anyProduct==null){
			showDialog("No item was selected!");
		}
		if(tabNumber==2 && electronicsProduct==null){
			showDialog("No item was selected!");
		}
		if(tabNumber==3 && clothProduct==null){
			showDialog("No item was selected!");
		}
		if(tabNumber==0 && anyProduct!=null) {

			if(anyProduct.getCategory().equals(uapbazar.Store.Category.Cloth)) {
				int percentage=saleDialogBox();
				String id=anyProduct.getId();
				if(percentage>=1 && percentage<=100) {
					StoreDataLoader.store.putOnSaleCloth(id, percentage);
					StoreDataLoader.writeObject();
					//SceneSwitcher.switchTo(View.Admin,false);
					refreshList();
				}
				else {
					showDialog("Invalid data entered.\n");
				}
			}
			else if(anyProduct.getCategory().equals(uapbazar.Store.Category.Electronics)) {
				int percentage=saleDialogBox();
				String id=anyProduct.getId();
				if(percentage>=1 && percentage<=100) {
					StoreDataLoader.store.putOnSaleElectronic(id, percentage);
					StoreDataLoader.writeObject();
					//SceneSwitcher.switchTo(View.Admin,false);
					refreshList();

				}
				else {
					showDialog("Invalid data entered.\n");
				}
			}
			else if(anyProduct.getCategory().equals(uapbazar.Store.Category.Food)){
				int percentage=saleDialogBox();
				Integer days=getDays();
				if(percentage>=1 && percentage<=100 || days>1) {
					StoreDataLoader.store.putOnSaleFood(days, percentage);
					StoreDataLoader.writeObject();
					//SceneSwitcher.switchTo(View.Admin,false);
					refreshList();
				}
				else {
					showDialog("Invalid data entered.\n");
				}
			}

			
			
			
			
		}
		if(tabNumber==1) {
			if (fList.size() > 0) {
				int percentage = saleDialogBox();
				Integer days = getDays();
				//System.out.println(foodProduct.getId());
				if (percentage >= 1 && percentage <= 100 || days > 1) {
					StoreDataLoader.store.putOnSaleFood(days, percentage);
					StoreDataLoader.writeObject();
					//SceneSwitcher.switchTo(View.Admin,false);
					refreshList();

				} else {
					showDialog("Invalid data entered.\n");
				}


			} else {
				showDialog("Not a single food item was added!");
			}
		}
		if(tabNumber==2 && electronicsProduct!=null) {

			int percentage=saleDialogBox();
			String id=electronicsProduct.getId();
			if(percentage>=1 && percentage<=100) {
				StoreDataLoader.store.putOnSaleElectronic(id, percentage);
				StoreDataLoader.writeObject();
				//SceneSwitcher.switchTo(View.Admin,false);
				refreshList();

			}
			else {
				showDialog("Invalid data entered.\n");
			}

		}
		if(tabNumber==3 && clothProduct!=null) {

			int percentage=saleDialogBox();
			String id=clothProduct.getId();
			if(percentage>=1 && percentage<=100) {
				StoreDataLoader.store.putOnSaleCloth(id, percentage);
				StoreDataLoader.writeObject();

				refreshList();


			}
			else {
				showDialog("Invalid data entered.\n");
			}

		}
		SceneSwitcher.switchTo(View.Admin,false);



		
	}
	
	public void backToLoginClicked() {
		SceneSwitcher.setScene(backToLogin.getScene());
		SceneSwitcher.switchTo(View.Front,false);
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
		tabPane.getSelectionModel().selectedIndexProperty().addListener((observable,oldValue,newValue)->{
			if(newValue.intValue()==0){
				setGiveSaleButton(false);
			}
			if(newValue.intValue()==1){
				setGiveSaleButton(true);
			}

			if(newValue.intValue()==2){
				setGiveSaleButton(false);
			}

			if(newValue.intValue()==3){
				setGiveSaleButton(false);
			}
		});
	}
	
	
	
}
