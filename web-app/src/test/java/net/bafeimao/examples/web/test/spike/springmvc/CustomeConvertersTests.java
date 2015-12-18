package net.bafeimao.examples.web.test.spike.springmvc;

import net.bafeimao.examples.web.domain.PhoneNumber;
import net.bafeimao.examples.web.util.converter.StringToPhoneNumberConverter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;

public class CustomeConvertersTests {
    private GenericConversionService conversionService = new DefaultConversionService();

    @Test
    public void testStringToPhoneNumberConvert() {
        conversionService.addConverter(new StringToPhoneNumberConverter());

        String tel = "010-12345678";
        PhoneNumber phoneNumber = conversionService.convert(tel, PhoneNumber.class);

        Assert.assertEquals("010", phoneNumber.getAreaCode());
    }

    @Test(expected = ConversionFailedException.class)
    public void testStringToPhoneConverterFailed() {
        conversionService.addConverter(new StringToPhoneNumberConverter());

        String tel = "error-number";
        conversionService.convert(tel, PhoneNumber.class);
    }

}
