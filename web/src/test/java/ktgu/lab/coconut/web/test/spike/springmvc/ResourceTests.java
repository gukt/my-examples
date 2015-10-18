package ktgu.lab.coconut.web.test.spike.springmvc;

import ktgu.lab.coconut.web.test.common.TransactionalSpringContextTestsBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceTests extends TransactionalSpringContextTestsBase {

    @Test
    public void testByteArrayResource() {
        ByteArrayResource resource = new ByteArrayResource(new String("Hello ByteArrayResource!").getBytes());
        if (resource.exists()) {
            dumpStream(resource);
        }
    }

    @Test
    public void testInputStreamResource() {
        ByteArrayInputStream bis = new ByteArrayInputStream("Hello World!".getBytes());
        Resource resource = new InputStreamResource(bis);
        if (resource.exists()) {
            dumpStream(resource);
        }
        Assert.assertEquals(true, resource.isOpen());
    }

    private void dumpStream(Resource resource) {
        InputStream is = null;
        try {
            is = resource.getInputStream();
            byte[] descBytes = new byte[is.available()];
            is.read(descBytes);
            System.out.println(new String(descBytes));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ignored) {
            }
        }
    }
}
