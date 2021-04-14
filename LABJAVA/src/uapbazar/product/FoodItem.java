package uapbazar.product;

import java.time.LocalDate;

public class FoodItem extends Product{	 
	

// make this a subclass of Product
	private LocalDate mfgDate, expirationDate;
	// constructor

	public FoodItem(String name, String id, int quantity, LocalDate mfg, LocalDate exp, double price) {
		super(name, id,Category.Food, quantity, price);
		this.mfgDate=mfg;
		this.expirationDate=exp;
		// TODO Auto-generated constructor stub
	}

	public LocalDate getMfgDate() {
		return mfgDate;
	}

	public void setMfgDate(LocalDate mfgDate) {
		this.mfgDate = mfgDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	// This method will put an item on sale if the item expires 
	// within next few days which will be decided by the admin.
	// -----------------------------//
	///Mam, I have counted the diff. between current date and expiry date if it is in the range
	///between 0---to--->expireWithin than putted  it up on the sale.
	///if the diff is -ve the  product is already expired
	@Override
	public void putOnSale(Object criterion, int percentage) throws ExpirationException{
		int days=(int)criterion;
		//System.out.println("Y\n");
		int remaining_days=this.getExpirationDate().compareTo(LocalDate.now());
		if(remaining_days>=0) {
			if(remaining_days<=days) {
				System.out.println("Putted on sale.\n");
				this.setOnSale(true);
				this.setSalePercent(percentage);
			}
			else {
				throw new ExpirationException(days);
			}
		}else {
			throw new ExpirationException();
		}
		
		
	}
	
	// This details method is for admin user. Admin should be able to see everything.
	public String details() {
		//return "name=" + this.getName() + "\tid=" + this.getId() + "\tcategory=" + this.getCategory() + "\tquantity=" + this.getQuantity() + "\tprice="
			//	+ this.totalPrice(this.getQuantity()) + "\tonSale=" + this.isOnSale() + "\tsalePercent=" + this.getSalePercent();
		return super.details()+"\tManufacturing Date: "+this.mfgDate+"\tExpiry Date: "+this.expirationDate;
	}	
}

