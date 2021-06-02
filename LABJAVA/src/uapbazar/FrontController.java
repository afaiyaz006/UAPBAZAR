package uapbazar;

import javafx.scene.control.Button;

public class FrontController {
	public Button admin;
	public Button customer;
	
	public void adminClicked() {
		SceneSwitcher.switchTo(View.Admin,false);///switching to adminpanel scene without caching
	}
	public void customerClicked() {
		StoreDataLoader.store.clearCart();
		SceneSwitcher.switchTo(View.User,false);///switching to userpanel scene without caching
	}
	
}
