package uapbazar.Store;

public class OutOfStockException extends Exception{

	public OutOfStockException(String message) {
		super(message);
	}
	public OutOfStockException() {
	
		super("Sorry We are out of stock");
	}
}
