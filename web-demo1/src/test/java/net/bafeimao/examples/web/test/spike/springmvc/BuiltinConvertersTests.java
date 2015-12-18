package net.bafeimao.examples.web.test.spike.springmvc;

import net.bafeimao.examples.web.util.enums.TaskStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.List;

public class BuiltinConvertersTests {

    private GenericConversionService conversionService;

    @Before
    public void setup() {
        conversionService = new DefaultConversionService();
    }

    @Test
    public void testToArray() {
        String[] stringArray = conversionService.convert("One,Two,Three", String[].class);
        for (String element : stringArray) {
            System.out.println("Element is " + element);
        }
        Assert.assertEquals(stringArray.length, 3);
    }

    @Test
    public void testToCollection() {
        List<?> list = conversionService.convert("One,Two,Three", List.class);
        Assert.assertTrue(list.size() == 3);
    }

    @Test
    public void testToBoolean() {
        Boolean result = null;

        result = conversionService.convert("true", Boolean.class);
        Assert.assertTrue(result);

        result = conversionService.convert("no", Boolean.class);
        Assert.assertFalse(result);
    }

    @Test
    public void testToCharacter() {
        Character data = conversionService.convert("A", Character.class);
        System.out.println("Character value is " + data);

        try {
            conversionService.convert("Exception", Character.class);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ConversionFailedException);
        }
    }

    @Test
    public void testToNumber() {
        Integer intData = conversionService.convert("124", Integer.class);
        System.out.println("Integer value is " + intData);

        Float floatData = conversionService.convert("215f", Float.class);
        System.out.println("Float value is " + floatData);
    }

    @Test
    public void testToEnum() {
        TaskStatus taskStatus = conversionService.convert("PENDING", TaskStatus.class);
        System.out.println("Task Status is " + taskStatus);
    }
}
