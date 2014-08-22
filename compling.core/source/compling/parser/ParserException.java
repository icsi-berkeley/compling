// =============================================================================
// File        : ParserException.java
// Author      : emok
// Change Log  : Created on Feb 25, 2007
//=============================================================================

package compling.parser;

//=============================================================================

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = -349091335193012819L;

	public ParserException() {
		super();
	}

	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}

}