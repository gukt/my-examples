package ktgu.lab.coconut.web.test.common;

import org.junit.Before;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractClientTest {

	protected static RestTemplate restTemplate;
	protected ObjectMapper objectMapper; // JSON
	protected Jaxb2Marshaller marshaller; // XML
	protected String baseUri = "http://localhost:8080/users";

	@Before
	public void setUp() throws Exception {
		objectMapper = new ObjectMapper();

		marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(new String[] { "ktgu.coconut" });
		marshaller.afterPropertiesSet();

		restTemplate = new RestTemplate();
	}
}