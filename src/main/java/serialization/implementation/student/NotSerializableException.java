package serialization.implementation.student;

@SuppressWarnings("serial")
public class NotSerializableException extends Exception{
	
	public NotSerializableException (String message) {
		super(message);
	}
	
}
