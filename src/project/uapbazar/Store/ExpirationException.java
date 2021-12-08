package uapbazar.Store;

public class ExpirationException extends Exception{
	public ExpirationException(String message) {
		super(message);
	}
	public ExpirationException(int days) {
	
		super("Will expire in more than "+days+" days.\n");
	}
	public ExpirationException() {
		super("Product is already expired.\n");
	}
}