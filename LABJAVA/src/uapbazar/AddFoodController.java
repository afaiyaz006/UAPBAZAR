package uapbazar;

import java.time.LocalDate;

import javafx.scene.control.Button;
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
	
	public DatePicker datePicker=new DatePicker();
	
	public void cancelClicked() {
		SceneSwitcher.switchTo(View.ChooseItem);
	}
	public void addClicked() {
		/*System.out.println(quantity.getText()+" "
				+productName.getText()+" "
				+price.getText()+" "
				+productId.getText()+" "+datePicker.getValue()
				);*/
		String foodName=productName.getText();
		String pid=productId.getText();
		double p=Double.parseDouble((String)price.getText());
		int amount=Integer.parseInt((String)quantity.getText());
		LocalDate dp=datePicker.getValue();
		StoreDataLoader.store.addProduct(foodName, pid, amount, LocalDate.now(), dp,p);
		StoreDataLoader.writeObject();
		//AdminPanelController.itemList.notifyAll();
		Stage s=(Stage)add.getScene().getWindow();
		SceneSwitcher.setScene(s.getOwner().getScene());
		SceneSwitcher.switchTo(View.Admin,false);
		SceneSwitcher.setScene(add.getScene());
		SceneSwitcher.switchTo(View.ChooseItem);
		
	}
}
