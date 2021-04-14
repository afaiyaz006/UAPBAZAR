package uapbazar.product;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Store implements Serializable {
	String name;
	ArrayList<Product> products ;
	Cart cart;

	// Constructor
	public Store(String name) {
		this.name=name;
		this.products= new ArrayList<>();
		this.cart=new Cart();
	}
	
	
	// ************** Cart related methods *******************
	
	public void addProductToCart(String id, int amt) throws Exception {
		// Call findProduct. If the product exist and the store has enough quantity,
		// Call the addProduct method of Cart class. Otherwise show "Out of Stock" message
		// 
		int index = findProduct(id);
		Product p = (Product) products.get(index).clone();
		if(index>=0) {
			
			if (p.getQuantity()>= amt) {
				p.setQuantity(amt);
				cart.addProduct(p);
		
			}
			else {
				throw new OutOfStockException();
				
			}
		}
		else throw new Exception("Out of bound index");
		
		

	}
	public ArrayList<Product> showCart(){
		return cart.viewCart();
	}
	public void removeProductFromCart(String id) {
		// call removeProduct of Cart class--->DONE
		cart.removeProduct(id);
	}

	public void updateProductInCart(String id, int count) {
		// Call findProduct (String, ArrayList<>) and Call the updateProduct method of Cart class--->DONE		
		cart.updateProduct(id, count);
	
	}

	public void clearCart() {
		// Call the clearCart method of Cart class---->DONE
		cart.clearCart();
	}

	// Pay Bill
	public double payBill() {
		// Iterate through each of the products in the cart and do the following
		// a) reduce those products quantity, ---->DONE
		// b) If the item is on sale add the saleprice to the bill.---->DONE(in salePrice 
							//										of product class)
																						
		// c) if not onSale add the totalPrice to bill. ----->DONE(in salePrice of product class)
		// d) Call the clearCart method----->DONE
		double bill=0;
		
		for(Product item:cart.items) {
			int product_index=findProduct(item.getId());
			
			if(product_index>=0) {
				Product product=products.get(product_index);
				product.updateQuantity(-item.getQuantity());
				if(product.getQuantity()==0) {
					products.remove(product);
				}
				//product.setQuantity(product.getQuantity()-item.getQuantity());
				bill+=product.salePrice(item.getQuantity());
				
			}
			else {
				System.out.println("Invalid product.\n");
			}
			
		}
		clearCart();
		return bill;
		
	}

	// ****************View related methods ******************
	
	// Admin will see all the information about each product. 
	// Non-admin will be able to view just the name, id and price of the product
	// Display the items in groups (Food, Electronics, Clothing) 
	public ArrayList<Product> viewProducts(boolean isAdmin) {
		// if isAdmin is true call viewProductsAsAdmin for different categories
		// else call viewProducts for different categories
		ArrayList<Product> finalList=new ArrayList<>();
		if(isAdmin) {
			
			finalList.addAll(viewProductsAsAdmin(Category.Cloth));
			finalList.addAll (viewProductsAsAdmin(Category.Electronics));
			finalList.addAll(viewProductsAsAdmin(Category.Food));
		}
		else {
			finalList.addAll(viewProducts(Category.Cloth));
			finalList.addAll(viewProducts(Category.Electronics));
			finalList.addAll(viewProducts(Category.Food));
	
			
		}
		return finalList;
	}

	// View the list of items in a specific category. Show only name, id and price
	public ArrayList<Product> viewProducts(Category category) {
		ArrayList<Product> tempList=new ArrayList<>();
		
		for(Product product:products) {
			if(product.getCategory().equals(category)) {
				//return product.details()+"\n";
				tempList.add(product);
			}
		}
		return tempList;
		
	}

	// View the list of items in a specific category. Show all info about each product.
	// This functionality is for admin
	public ArrayList<Product> viewProductsAsAdmin(Category category) {
		ArrayList<Product> tempList=new ArrayList<>();
		for(Product product:products) {
			
			if(product.getCategory().equals(category)) {
				//return product.details()+"\n";
				tempList.add(product);
			}
		}
		return tempList;
	}
	
	// *************** Admin methods to put a item(s) on sale*************
	
	// Put all food items on sale that will expire within next expireWithin days
	public void putOnSaleFood(Integer expireWithin, int percentage) {
		// Search for the items that will expire within next expireWithin days and call putOnSale for that item
		for(Product product:products) {
			if(product.getCategory().equals(Category.Food)) {
				try {
					product.putOnSale(expireWithin, percentage);
				} catch (ExpirationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}
	
	// Put a specific cloth item on sale
	public void putOnSaleCloth(String  id, int percentage) {
		// Search for the items with the specific id and call putOnSale for that item
		for(Product product:products) {
			if(product.getCategory().equals(Category.Cloth)) {
				try {
					product.putOnSale(id, percentage);
				} catch (ExpirationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	// Put a specific cloth item on sale
	public void putOnSaleElectronic(String  id, int percentage) {
		// Search for the items with the specific id and call putOnSale for that item
		for(Product product:products) {
			if(product.getCategory().equals(Category.Electronics)) {
				try {
					product.putOnSale(id, percentage);
				} catch (ExpirationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	

	//****************************Admin methods to add items in the store*********

	// This method is for adding clothing item to the store
	public void addProduct(String name, String id, int quantity, String b, ClothingSubCategory sub, ClothingSize size, double price) {
		// Call the addProduct(Product p) method with Clothing object as parameter
		
		Product product=new Clothing(name,id,quantity,b,sub,size,price);
		addProduct(product);
		
	}

	// This method is for adding Electronics item to the store
	public void addProduct(String name, String id, int quantity, String manufacturer, ElectCategory subCategory, double price) {
		// Call the addProduct(Product p) method with Electronics object as parameter
		Product product=new Electronics(name,id,quantity,manufacturer,subCategory,price);
		addProduct(product);
	}

	// This method is for adding Food item to the store
	// I am adding this code as it is little tricky
	public void addProduct(String name, String id, int quantity, LocalDate mfg, LocalDate exp,double price) {
		int index = findProduct(id);
		if(index >=0) {
			Product pr= products.get(index);
			FoodItem item = (FoodItem)pr;
			if (item.getMfgDate().equals(mfg) && 
					item.getExpirationDate().equals(exp)) {
				item.updateQuantity(quantity);
				return;					
			}
		}
		products.add(new FoodItem(name, id, quantity, mfg, exp, price));
	}

	// ******************** private methods**************
	
	private int findProduct(String id) { 	 
		// search the product in the products list using id. 
		// If the product exists return the product, otherwise return -1
		for(int i=0;i<products.size();i++) {
			if(products.get(i).getId().equals(id)) {
				return i;
			}
		}
		return -1;
		
	}

	// the following method is a private method for only Clothing and Electronics/
	private void addProduct(Product p) {
		// Check if the item is available in the store.
		// If available increment the quantity. Else add the product in the list
		int product_index=findProduct(p.getId());
		if(product_index!=-1) {
			Product product=products.get(product_index);
			product.setQuantity(product.getQuantity()+1);
		}
		else {
			products.add(p);
		}
	}
}