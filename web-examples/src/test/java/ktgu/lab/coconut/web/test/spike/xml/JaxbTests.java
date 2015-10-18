package ktgu.lab.coconut.web.test.spike.xml;

import ktgu.lab.coconut.web.domain.User;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JaxbTests {

    @Test
    public void testXml() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(User.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        // URL url = this.getClass().getResource("user.xml");
        User user = (User) unmarshaller.unmarshal(new File(this.getClass().getResource("user.xml").getPath()));

        user.setName("updated");

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(user, System.out);
    }
}
