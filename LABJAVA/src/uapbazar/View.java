package uapbazar;
public enum View{
	Admin("fxml/AdminPanel.fxml"),
	User("fxml/UserPanel.fxml"),
	Food("fxml/AddFood.fxml"),
	Cloth("fxml/AddCloth.fxml"),
	Electronics("fxml/AddElectronics.fxml"),
	ChooseItem("fxml/ChooseItem.fxml"),
	Front("fxml/Front_Purple.fxml");
	
	private final String label;
	View(String label) {
		this.label=label;
	}
	public String getFileName() {
        return label;
    }
	
}
