package serialization.implementation.student;

@SuppressWarnings("serial")
public class WrongFileExtensionException extends Exception {
	
	public WrongFileExtensionException(String message) {
		super(message);
	}
	
}
