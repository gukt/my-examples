package net.bafeimao.examples.web.util.converter;

import net.bafeimao.examples.web.domain.PhoneNumber;

import org.springframework.core.convert.converter.Converter;

public class PhoneNumberToStringConverter implements Converter<PhoneNumber, String> {

	@Override
	public String convert(PhoneNumber source) {
		if (source != null) {
			return source.getAreaCode() + "-" + source.getNumber();
		}
		return null;
	}

}
