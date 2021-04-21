package uapbazar;

import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import uapbazar.product.*;

public class AddClothController {
	public Button cancel;
	public Button add;
	public TextField productName;
	public TextField productId;
	public TextField price;
	public TextField productQuantity;
	public TextField brand;
	public ChoiceBox<ClothingSubCategory> clothSubCategory;
	public ChoiceBox<ClothingSize> clothSize;
	public Button generate;
	public static void showDialog(String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Invalid  Data");
		alert.setHeaderText("Warrning");
		alert.setContentText(msg);

		alert.showAndWait();
	}
	public void cancelClicked() {
		SceneSwitcher.switchTo(View.ChooseItem,false);
	
	}
	public void generateClicked() {
		productId.setText(String.valueOf(RandomNumberGenerator.randNum()));
	}
	public void addClicked() {
	    ///Resources: https://www.javatpoint.com/java-regex
			

		try {
				ClothingSubCategory csc = clothSubCategory.getValue();
				ClothingSize cs = clothSize.getValue();
				String itemName = productName.getText();
				String itemId = productId.getText();
				String itemBrand = brand.getText();
				String itemQuantity = productQuantity.getText();
				String ItemPrice = price.getText();
		
				String fullError = "";

				String itemNameError = "Invalid Product Name!\n";
				String itemIDError = "Invalid Product ID!\n";
				String itemBrandError = "Invalid Product Brand!\n";
				String itemQuantityError = "Invalid Product Qunatity!\n";
				String itemPriceError = "Invalid Product Price!\n";
				String cscError="Cloth subcategory was not selected.\n";
				String csError="Cloth size was not selected.\n";
				boolean itemNameLogic = Pattern.matches("[a-zA-Z ]+", itemName);

				if (!itemNameLogic) {
					fullError += itemNameError;
				}

				boolean itemIdLogic = Pattern.matches("[0-9]*", itemId);
				if (!itemIdLogic) {
					fullError += itemIDError;
				}

				boolean itemBrandLogic = Pattern.matches("[a-zA-Z]*", itemBrand);
				if (!itemBrandLogic) {
					fullError += itemBrandError;
				}

				boolean itemQuantityLogic = Pattern.matches("[0-9]*", itemQuantity);
				if (!itemQuantityLogic) {
					fullError += itemQuantityError;
				}
				
				boolean ItemPriceLogic = Pattern.matches("^\\d{0,12}(\\.?\\d{1,10})", ItemPrice);
				if (!ItemPriceLogic) {
					fullError += itemPriceError;
				}
				if(csc==null) {
					fullError+=cscError;
				}
				if(cs==null) {
					fullError+=csError;
				}
				if(!itemNameLogic || !itemIdLogic || !itemBrandLogic || 
						!itemQuantityLogic || !ItemPriceLogic || csc==null || cs==null) {
					showDialog(fullError);
				}
				else {
					Double price = Double.parseDouble(ItemPrice);
					Integer quantity=Integer.parseInt(itemQuantity);
					//StoreDataLoader.store.addProduct(, itemIDError, 0, itemBrandError, csc, cs, price);
					StoreDataLoader.store.addProduct(itemName, itemId, quantity, itemBrand, csc, cs, price);
					
					StoreDataLoader.writeObject();
					//AdminPanelController.itemList.notifyAll();
					Stage s=(Stage)add.getScene().getWindow();
					SceneSwitcher.setScene(s.getOwner().getScene());
					SceneSwitcher.switchTo(View.Admin,false);
					SceneSwitcher.setScene(add.getScene());
					SceneSwitcher.switchTo(View.ChooseItem,false);
				}

			} catch (Exception e) {
				showDialog("Invalid input");
				//System.out.println("Colthing exception = " + e);
			}
			
		
		
		
		
		
	}
	
	@FXML
	public void initialize() {
		clothSubCategory.getItems().addAll(ClothingSubCategory.values());
		clothSize.getItems().addAll(ClothingSize.values());
		clothSubCategory.setValue(ClothingSubCategory.MEN);
		clothSize.setValue(ClothingSize.XL);
		
	}
}
