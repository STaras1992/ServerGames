package exeptions;

import java.io.Serializable;

public class UnknownIdException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public UnknownIdException(Throwable err) {
		super(err);
	}
	public UnknownIdException(Throwable err,String message) {
		super(message,err);		
	}

}
