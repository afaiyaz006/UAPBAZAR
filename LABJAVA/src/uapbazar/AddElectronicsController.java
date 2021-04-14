package uapbazar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uapbazar.product.*;
public class AddElectronicsController {
	public Button cancel;
	public Button add;
	public TextField productQuantity;
	public TextField productName;
	public TextField productId;
	public TextField price;
	public TextField manufacturer;
	public TextField brand;
	public ChoiceBox<ElectCategory> subCategory;
	
	
	public void cancelClicked() {
		SceneSwitcher.switchTo(View.ChooseItem);
	}
	public void addClicked() {
		ElectCategory eCat=subCategory.getValue();
		String itemName=productName.getText();
		String pid=productId.getText();
		String mf=manufacturer.getText();
		String br=brand.getText();
		
		double p=Double.parseDouble((String)price.getText());
		int amount=Integer.parseInt((String)productQuantity.getText());
		StoreDataLoader.store.addProduct(itemName, pid, amount, mf, eCat, p);
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
		subCategory.getItems().addAll(ElectCategory.values());
	}
	
}
