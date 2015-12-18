package net.bafeimao.examples.web.util.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bafeimao.examples.web.domain.PhoneNumber;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class StringToPhoneNumberConverter implements Converter<String, PhoneNumber> {
	Pattern pattern = Pattern.compile("^(\\d{3,4})-(\\d{7,8})$");

	@Override
	public PhoneNumber convert(String source) {
		if (!StringUtils.hasLength(source)) {
			return null;
		}

		Matcher matcher = pattern.matcher(source);
		if (matcher.matches()) {
			PhoneNumber phoneNumber = new PhoneNumber();
			phoneNumber.setAreaCode(matcher.group(1));
			phoneNumber.setNumber(matcher.group(2));
			return phoneNumber;
		} else {
			throw new IllegalArgumentException(String.format("类型转换失败，需要格式[010-12345678]，但实际是：[%s]", source));
		}
	}
}
