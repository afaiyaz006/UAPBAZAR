package uapbazar;
import uapbazar.product.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.fxml.FXML;
public class ChooseItemTypeController {
	public Button next;
	public Button back;
	public ComboBox<Category> comboBox=new ComboBox<Category>();
	
	public ObservableList<Category> itemTypes = FXCollections.observableArrayList(Category.values());

	public void nextClicked() {
		if(comboBox.getValue().equals(Category.Food)) {
			
			SceneSwitcher.switchTo(View.Food,false);
		
		}
		else if(comboBox.getValue().equals(Category.Electronics)) {
			SceneSwitcher.switchTo(View.Electronics,false);
		}
		else if(comboBox.getValue().equals(Category.Cloth)) {
			SceneSwitcher.switchTo(View.Cloth,false);
		}
		
	}
	public void backClicked() {
		Stage s=(Stage)next.getScene().getWindow();
		
		s.close();
		
		
	}
	
	
	@FXML
	public void initialize() {
		//comboBox.setItems(itemTypes);
		comboBox.getItems().setAll(itemTypes);
	}
}
