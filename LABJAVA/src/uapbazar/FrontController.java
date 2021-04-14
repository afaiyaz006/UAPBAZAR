package uapbazar;

import javafx.scene.control.Button;

public class FrontController {
	public Button admin;
	public Button customer;
	
	public void adminClicked() {
		SceneSwitcher.switchTo(View.Admin);///switching to adminpanel scene with caching enabled
	}
	public void customerClicked() {
		SceneSwitcher.switchTo(View.User,false);///switching to userpanel scene without caching
	}
	
}
