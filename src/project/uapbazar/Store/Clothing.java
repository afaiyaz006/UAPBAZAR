package uapbazar.Store;



public class Clothing extends Product  {
	
	
	

// make this a subclass of Product
	private String brand;
	private ClothingSubCategory subCategory; // Can use String as well--->Didn't
	private ClothingSize size; // Can avaoid enum if you want--->I didn't

	// constructor
	public Clothing(String name, String id, int quantity, String brand, ClothingSubCategory subCategory, ClothingSize size, double price) {
		super(name, id, Category.Cloth, quantity, price);
		this.brand = brand;
		this.subCategory = subCategory;
		this.size = size;
		
	}

	// getter/setter
	public String getbrand() {
		return this.brand;
	}
	public ClothingSubCategory getSubCategory() {
		return this.subCategory; 
	}
	public ClothingSize getSize() {
		return this.size;
	}


	// Override the putOnSale method
	// This method will put an item on sale and also set the sale percentage
	// The item id ans sale percentage will be decided by the admin.
	@Override
	public void putOnSale(Object id, int percentage) {
		if(this.getId().equals((String)id)) {
			this.setOnSale(true);
			this.setSalePercent(percentage);
		}
	}

	

	// This details method is for admin user. Admin should be able to see everything.
	// Call details method of parent class and add other info specific to this class
	public String details() {
		return super.details()+"\tSubCategory: " + this.subCategory +"\tSize: "+ " " + this.size +"\tBrand: "+ this.brand+"\n";
	}

}
