package ktgu.lab.coconut.web.exception;

public class DuplicateEmailException extends Exception {
	private static final long serialVersionUID = -8118553580374525843L;

	public DuplicateEmailException() {
		super("Duplicated Email.");
	}
}
