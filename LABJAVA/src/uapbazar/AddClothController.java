package uapbazar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
	
	public void cancelClicked() {
		SceneSwitcher.switchTo(View.ChooseItem,false);
	
	}
	public void addClicked() {
		ClothingSubCategory csc=clothSubCategory.getValue();
		ClothingSize cs=clothSize.getValue();
		String itemName=productName.getText();
		String pid=productId.getText();
		String br=brand.getText();
		Double p=Double.parseDouble((String)price.getText());
		int amount=Integer.parseInt((String)productQuantity.getText());
		StoreDataLoader.store.addProduct(itemName, pid, amount, br, csc, cs, p);
		StoreDataLoader.writeObject();
		//AdminPanelController.itemList.notifyAll();
		Stage s=(Stage)add.getScene().getWindow();
		SceneSwitcher.setScene(s.getOwner().getScene());
		SceneSwitcher.switchTo(View.Admin,false);
		SceneSwitcher.setScene(add.getScene());
		SceneSwitcher.switchTo(View.ChooseItem);
		
	}
	
	@FXML
	public void initialize() {
		clothSubCategory.getItems().addAll(ClothingSubCategory.values());
		clothSize.getItems().addAll(ClothingSize.values());
		
	}
}
