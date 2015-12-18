package net.bafeimao.examples.web.test.spike.springmvc;

import net.bafeimao.examples.web.domain.PhoneNumber;
import net.bafeimao.examples.web.util.formatter.PhoneNumberFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.number.CurrencyStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Locale;

public class FormatterTest {

    private FormattingConversionService fcs;

    @Before
    public void setup() {
        fcs = new DefaultFormattingConversionService();
    }

    @Test
    public void testFormatterDirectly() throws ParseException {
        CurrencyStyleFormatter currencyFormatter = new CurrencyStyleFormatter();
        currencyFormatter.setFractionDigits(2);
        currencyFormatter.setRoundingMode(RoundingMode.CEILING);

        Assert.assertEquals(new BigDecimal("123.13"), currencyFormatter.parse("$123.125", Locale.US));
        Assert.assertEquals("$123.00", currencyFormatter.print(new BigDecimal("123"), Locale.US));
        Assert.assertEquals("￥123.00", currencyFormatter.print(new BigDecimal("123"), Locale.CHINA));
        Assert.assertEquals("￥123.00", currencyFormatter.print(new BigDecimal("123"), Locale.JAPAN));
    }

    @Test
    public void testWithDefaultFormattingConversionService() {
        CurrencyStyleFormatter currencyFormatter = new CurrencyStyleFormatter();
        currencyFormatter.setFractionDigits(2);
        currencyFormatter.setRoundingMode(RoundingMode.CEILING);

        fcs.addFormatter(currencyFormatter);

        LocaleContextHolder.setLocale(Locale.US);
        Assert.assertEquals("$1,234.13", fcs.convert(new BigDecimal("1234.128"), String.class));
        LocaleContextHolder.setLocale(null);

        LocaleContextHolder.setLocale(Locale.CHINA);
        Assert.assertEquals("￥1,234.13", fcs.convert(new BigDecimal("1234.128"), String.class));
        Assert.assertEquals(new BigDecimal("1234.13"), fcs.convert("￥1,234.13", BigDecimal.class));
        LocaleContextHolder.setLocale(null);
    }

//    @Test
//    public void testModel() throws SecurityException, NoSuchFieldException {
//        FormatterModel model = new FormatterModel();
//        model.setTotalCount(10000);
//        model.setDiscount(0.51);
//        model.setSumMoney(10000.13);
//        model.setRegisterDate(new Date(2012 - 1900, 4, 1));
//        model.setOrderDate(new Date(2012 - 1900, 4, 1, 20, 18, 18));
//
//        // 获取类型信息
//        TypeDescriptor descriptor = new TypeDescriptor(FormatterModel.class.getDeclaredField("totalCount"));
//        TypeDescriptor sDescriptor = TypeDescriptor.valueOf(String.class);
//
//        Assert.assertEquals("10,000", fcs.convert(model.getTotalCount(), descriptor, sDescriptor));
//        Assert.assertEquals(model.getTotalCount(), fcs.convert("10,000", sDescriptor, descriptor));
//
//        // descriptor = new TypeDescriptor(FormatterModel.class.getDeclaredField("registerDate"));
//        // Assert.assertEquals("2012-05-01", fcs.convert(model.getRegisterDate(), descriptor, sDescriptor));
//        // Assert.assertEquals(model.getRegisterDate(), fcs.convert("2012-05-01", sDescriptor, descriptor));
//
//        descriptor = new TypeDescriptor(FormatterModel.class.getDeclaredField("orderDate"));
//        Assert.assertEquals("2012-05-01 20:18:18", fcs.convert(model.getOrderDate(), descriptor, sDescriptor));
//        Assert.assertEquals(model.getOrderDate(), fcs.convert("2012-05-01 20:18:18", sDescriptor, descriptor));
//
//    }

    @Test
    public void test11() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addFormatter(new PhoneNumberFormatter());

        PhoneNumber phoneNumber = new PhoneNumber("010", "12345678");
        Assert.assertEquals("010-12345678", conversionService.convert(phoneNumber, String.class));

        Assert.assertEquals("010", conversionService.convert("010-12345678", PhoneNumber.class).getAreaCode());
    }
}
