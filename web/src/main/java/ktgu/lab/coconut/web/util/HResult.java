package ktgu.lab.coconut.web.util;

public enum HResult {
	FAILED(0), SUCCESS(1), IDENTIFIER_NOT_EXITS(2), PASSWORD_NOT_MATCHED(3);

	private int code;

	private HResult(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}