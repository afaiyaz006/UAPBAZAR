package uapbazar;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFoodController {
	public Button cancel;
	public Button add;
	public TextField quantity;
	public TextField productName;
	public TextField productId;
	public TextField price;
	public Button generate;
	public DatePicker datePicker=new DatePicker();
	public void generateClicked() {
		productId.setText(String.valueOf(RandomNumberGenerator.randNum()));
	}
	public void cancelClicked() {
		SceneSwitcher.switchTo(View.ChooseItem,false);
	}
	public static void showDialog(String msg) {
		Alert alert;
		if(msg.equals("Success")) {
			alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Added Successfully");
		}
		else {
			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Warning");
		}
		alert.setTitle(msg);
		
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	public void addClicked() {
		/*System.out.println(quantity.getText()+" "
				+productName.getText()+" "
				+price.getText()+" "
				+productId.getText()+" "+datePicker.getValue()
				);*/
		
		try {
			
			String foodName=productName.getText();
			String pid=productId.getText();
			double p=Double.parseDouble((String)price.getText());
			int amount=Integer.parseInt((String)quantity.getText());
			LocalDate dp=datePicker.getValue();
		
			boolean foodNameCheck=foodName.matches("[a-zA-Z]+");
			boolean pidCheck = pid.matches("[0-9]+");
		
			if(foodNameCheck && pidCheck) {
				StoreDataLoader.store.addProduct(foodName, pid, amount, LocalDate.now(), dp,p);
				StoreDataLoader.writeObject();
				//AdminPanelController.itemList.notifyAll();
				Stage s=(Stage)add.getScene().getWindow();
				SceneSwitcher.setScene(s.getOwner().getScene());
				SceneSwitcher.switchTo(View.Admin,false);
				SceneSwitcher.setScene(add.getScene());
				SceneSwitcher.switchTo(View.ChooseItem,false);
				
				
			}
			else {
				showDialog("Invalid Data Entered");
			}
			
			
		   
		}catch(Exception e) {
			showDialog("Invalid Input");
		}
		
		
	}
	@FXML
	public void initialize() {
		LocalDate minDate = LocalDate.now().plusDays(1);
		LocalDate maxDate = LocalDate.of(2999, 4, 16);
		datePicker.setDayCellFactory(d ->
		           new DateCell() {
		               @Override 
		               public void updateItem(LocalDate item, boolean empty) {
		                   super.updateItem(item, empty);
		                   setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
		               }
		 });
		          
	}
}
