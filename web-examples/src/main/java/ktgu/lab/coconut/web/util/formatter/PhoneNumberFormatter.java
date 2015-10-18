package ktgu.lab.coconut.web.util.formatter;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ktgu.lab.coconut.web.domain.PhoneNumber;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

public class PhoneNumberFormatter implements Formatter<PhoneNumber> {
	Pattern pattern = Pattern.compile("^(\\d{3,4})-(\\d{7,8})$");

	@Override
	public String print(PhoneNumber phoneNumber, Locale locale) {// ①格式化
		if (phoneNumber == null) {
			return "";
		}
		return new StringBuilder().append(phoneNumber.getAreaCode()).append("-")
									.append(phoneNumber.getNumber()).toString();
	}

	@Override
	public PhoneNumber parse(String text, Locale locale) throws ParseException {// ②解析
		if (!StringUtils.hasLength(text)) {
			// ①如果source为空 返回null
			return null;
		}
		Matcher matcher = pattern.matcher(text);
		if (matcher.matches()) {
			// ②如果匹配 进行转换
			PhoneNumber phoneNumber = new PhoneNumber();
			phoneNumber.setAreaCode(matcher.group(1));
			phoneNumber.setNumber(matcher.group(2));
			return phoneNumber;
		} else {
			// ③如果不匹配 转换失败
			throw new IllegalArgumentException(String.format("类型转换失败，需要格式[010-12345678]，但格式是[%s]", text));
		}
	}
}
