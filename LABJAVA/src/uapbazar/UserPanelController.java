package uapbazar;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import uapbazar.product.OutOfStockException;
import uapbazar.product.Product;

public class UserPanelController {
	
	public TabPane tabPane;
	@FXML
	public ListView<Product> all;
	public ObservableList<Product> itemList=FXCollections.observableList(StoreDataLoader.store.viewProducts(false));
	@FXML
	public ListView<Product> foodList;
	public ObservableList<Product> fooList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Food));
	@FXML
	public ListView<Product> electronicsList;
	public ObservableList<Product>	elList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Electronics));
	@FXML
	public ListView<Product> clothList;
	public ObservableList<Product> cloList=FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Cloth));
	@FXML
	public ListView<Product> cartList;
	public ObservableList<Product> caList=FXCollections.observableList(StoreDataLoader.store.showCart());

	public Button back;
	public Button addItem;
	public Button removeItem;
	public Button clearCart;
	public Button payBill;
	public Label totalBill;
	public Double bill=0.0;
	public Spinner<Integer> spinner;
	public Spinner<Integer> spinner1;
	final SpinnerValueFactory<Integer> valueFactory = //
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
	public void refreshLists() {
		
		fooList.setAll(FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Food)));
		elList.setAll(FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Electronics)));
		cloList.setAll(FXCollections.observableList(StoreDataLoader.store.viewProducts(uapbazar.product.Category.Cloth)));
		itemList.setAll(FXCollections.observableList(StoreDataLoader.store.viewProducts(false)));
		caList.setAll(FXCollections.observableList(StoreDataLoader.store.showCart()));
		
	}
	
	public void backClicked() {
		SceneSwitcher.switchTo(View.Front);
	}
	public void addProductToCart(Product productInStore) {
		boolean found=false;
		boolean outOfStock=true;
		for(Product p:caList) {
			if(p.getId().equals(productInStore.getId())) {
				if((p.getQuantity()+spinner.getValue())<=productInStore.getQuantity()) {
					StoreDataLoader.store.updateProductInCart(productInStore.getId(), spinner.getValue());
					Double price=(Double) productInStore.salePrice(spinner.getValue());
					p.updateQuantity(spinner.getValue());
					
					refreshLists();
					bill+=price;
					totalBill.setText(bill+"tk");
					outOfStock=false;
				}
				found=true;
				break;
			}
		}
		if(found && outOfStock) {
			Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.WARNING, "Sorry We are out of stocks!", ButtonType.OK);
		        dialog.show();
		    });
		}
		if(!found){
			try {
		
				StoreDataLoader.store.addProductToCart(productInStore.getId(), spinner.getValue());
				Double price=(Double) productInStore.salePrice(spinner.getValue());
				bill+=price;
				totalBill.setText(bill+"tk");
				
			}catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Outof Stock!");
			    Platform.runLater(() -> {
			        Alert dialog = new Alert(AlertType.WARNING, "Sorry we are out of stocks!", ButtonType.OK);
			        dialog.show();
			    });
				///e.printStackTrace();
			}
		}
		
	}
	
	public void addItemClicked() {
		int tabNumber=tabPane.getSelectionModel().getSelectedIndex();
		if(tabNumber==0) {
			Product productInStore=all.getSelectionModel().getSelectedItem();
			if(productInStore!=null) {
				addProductToCart(productInStore);
				StoreDataLoader.writeObject();
				refreshLists();
				//SceneSwitcher.switchTo(View.User,false);
				//caList.setAll(FXCollections.observableList(StoreDataLoader.store.showCart()));
			}
		}
		else if(tabNumber==1) {
			
			Product foodInStore=foodList.getSelectionModel().getSelectedItem();
			if(foodInStore!=null) {
				addProductToCart(foodInStore);
				StoreDataLoader.writeObject();
				refreshLists();
				///SceneSwitcher.switchTo(View.User,false);
			}
			
				
			
		}
		else if(tabNumber==2) {
			Product clothInStore=clothList.getSelectionModel().getSelectedItem();
			if(clothInStore!=null) {
				addProductToCart(clothInStore);
				StoreDataLoader.writeObject();
				refreshLists();
				///SceneSwitcher.switchTo(View.User,false);
		
			}
		}
		else if(tabNumber==3) {
			Product electronicInStore=electronicsList.getSelectionModel().getSelectedItem();
			if(electronicInStore!=null) {
				addProductToCart(electronicInStore);
				StoreDataLoader.writeObject();
				refreshLists();
				///SceneSwitcher.switchTo(View.User,false);
		
			}
			
		}
	
	}
	
	public void removeItemClicked() {
			
			Product p=cartList.getSelectionModel().getSelectedItem();
			if(spinner1.getValue()==p.getQuantity()) {
				StoreDataLoader.store.removeProductFromCart(p.getId());
				
//			spinner1.setValueFactory(valueFactory);
				caList.remove(p);
				bill-=p.salePrice(p.getQuantity());
				totalBill.setText(bill+"tk");
				refreshLists();
				spinner1.getValueFactory().setValue(null);
			}
			else {
				StoreDataLoader.store.updateProductInCart(p.getId(),-spinner1.getValue());
				for(Product product:all.getItems()) {
					if(product.equals(p)) {
						p.updateQuantity(spinner1.getValue());
						break;
					}
				}
				p.updateQuantity(-spinner1.getValue());
				
				bill-=p.salePrice(spinner1.getValue());
				totalBill.setText(bill+"tk");
				refreshLists();
				spinner1.getValueFactory().setValue(null);
			}
			System.out.print(p.getQuantity());
			
	}
	
	public void clearCartClicked() {
			StoreDataLoader.store.clearCart();
			StoreDataLoader.writeObject();
			refreshLists();
			bill=0.0;
			totalBill.setText(bill+"tk");
			spinner1.getValueFactory().setValue(null);
			//SceneSwitcher.switchTo(View.User,false);
	}
	
	public void payBillClicked() {
			if(caList.size()==0) {
				Platform.runLater(()->{
					Alert dialog = new Alert(AlertType.INFORMATION, "Cart is empty", ButtonType.CLOSE);
					dialog.show();
				});
			}
			else {
				double paidBill=StoreDataLoader.store.payBill();
				
				refreshLists();
				
				Platform.runLater(() -> {
					Alert dialog = new Alert(AlertType.INFORMATION, "Bill paid "+paidBill+" tk", ButtonType.OK);
					dialog.show();
				});
				bill=0.0;
				totalBill.setText(bill+"tk");
			}
	}
	@FXML
	public void initialize() {
		all.setItems(itemList);
		//all.setStyle("-fx-control-inner-background:#485460");
		foodList.setItems(fooList);
		electronicsList.setItems(elList);
		clothList.setItems(cloList);
		cartList.setItems(caList);
		//spinner.setValueFactory(valueFactory);
		totalBill.setText(String.valueOf(bill)+"tk");
		foodList.setOrientation(Orientation.HORIZONTAL);
		electronicsList.setOrientation(Orientation.HORIZONTAL);
		clothList.setOrientation(Orientation.HORIZONTAL);
		all.setOrientation(Orientation.HORIZONTAL);
		///spinner defaults for cart
		cartList.setOnMouseClicked(e->{
			
			int quantity=cartList.getSelectionModel().getSelectedItem().getQuantity();
			SpinnerValueFactory<Integer> valueFactory = //
		            new SpinnerValueFactory.IntegerSpinnerValueFactory(1,quantity, 1);
			spinner1.setValueFactory(valueFactory);
		});
		////spinner defaults for store
		all.setOnMouseClicked(e->{
			int max_quantity=all.getSelectionModel().getSelectedItem().getQuantity();
			SpinnerValueFactory<Integer> valueFactory = //
		            new SpinnerValueFactory.IntegerSpinnerValueFactory(1,max_quantity, 1);
			spinner.setValueFactory(valueFactory);
		});

		foodList.setOnMouseClicked(e->{
			int max_quantity=foodList.getSelectionModel().getSelectedItem().getQuantity();
			SpinnerValueFactory<Integer> valueFactory = //
		            new SpinnerValueFactory.IntegerSpinnerValueFactory(1,max_quantity, 1);
			spinner.setValueFactory(valueFactory);
		});

		clothList.setOnMouseClicked(e->{
			int max_quantity=clothList.getSelectionModel().getSelectedItem().getQuantity();
			SpinnerValueFactory<Integer> valueFactory = //
		            new SpinnerValueFactory.IntegerSpinnerValueFactory(1,max_quantity, 1);
			spinner.setValueFactory(valueFactory);
		});
		electronicsList.setOnMouseClicked(e->{
			int max_quantity=electronicsList.getSelectionModel().getSelectedItem().getQuantity();
			SpinnerValueFactory<Integer> valueFactory = //
		            new SpinnerValueFactory.IntegerSpinnerValueFactory(1,max_quantity, 1);
			spinner.setValueFactory(valueFactory);
		});
	}
	
	
}
