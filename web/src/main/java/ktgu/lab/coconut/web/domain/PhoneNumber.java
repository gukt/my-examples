package ktgu.lab.coconut.web.domain;

public class PhoneNumber {
	private String areaCode;
	private String number;

	public PhoneNumber(String areaCode, String number) {
		this.areaCode = areaCode;
		this.number = number;
	}

	public PhoneNumber() {
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
