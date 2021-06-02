package uapbazar;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uapbazar.Store.*;
public class AddElectronicsController {
	public Button cancel;
	public Button add;
	public TextField productQuantity;
	public TextField productName;
	public TextField productId;
	public TextField price;
	public TextField manufacturer;
	public TextField brand;
	public Button generate;
	public ChoiceBox<ElectCategory> subCategory;
	public void generateClicked() {
			productId.setText(String.valueOf(RandomNumberGenerator.randNum()));
	}
	public static void showDialog(String msg) {
		Alert alert;
		if(msg.equals("Success")) {
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Added Successfully");
		}
		else {
			alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText("Warning");
		}
		alert.setTitle(msg);

		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	public void cancelClicked() {
		SceneSwitcher.switchTo(View.ChooseItem,false);
	}
	public void addClicked() {
		try {
			ElectCategory eCat = subCategory.getValue();
			String itemName = productName.getText();
			String pid = productId.getText();
			String mf = manufacturer.getText();
			String br = brand.getText();
			double p = Double.parseDouble((String) price.getText());
			int amount = Integer.parseInt((String) productQuantity.getText());
			boolean itemNameCheck = itemName.matches("[A-Za-z ]+");
			boolean quantityCheck = amount > 0;
			boolean priceCheck = p > 0;
			boolean manufacturerCheck = mf.matches("[A-Za-z ]+");
			boolean brandCheck = br.matches("[A-Za-z ]+");
			if (itemNameCheck && quantityCheck && priceCheck && manufacturerCheck && brandCheck) {
				StoreDataLoader.store.addProduct(itemName, pid, amount, mf, eCat, p);
				StoreDataLoader.writeObject();
				//AdminPanelController.itemList.notifyAll();
				Stage s = (Stage) add.getScene().getWindow();
				SceneSwitcher.setScene(s.getOwner().getScene());
				SceneSwitcher.switchTo(View.Admin, false);
				SceneSwitcher.setScene(add.getScene());
				SceneSwitcher.switchTo(View.ChooseItem, false);
			}
			else{
				showDialog("One or more fields are invalid");

			}
		}
		catch(Exception e){
			showDialog("One or more fields are empty or invalid");
		}
	}
	@FXML
	public void initialize() {
		subCategory.getItems().addAll(ElectCategory.values());
		subCategory.setValue(ElectCategory.ELECTRONIC_DEVICE);
	}
	
}
