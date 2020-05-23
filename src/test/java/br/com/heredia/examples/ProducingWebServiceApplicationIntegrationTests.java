package br.com.heredia.examples;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import br.com.heredia.examples.dto.country.GetCountryRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProducingWebServiceApplicationIntegrationTests {

	private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

	@LocalServerPort
	private int port = 0;

	@BeforeEach
	public void init() throws Exception {
		marshaller.setPackagesToScan(ClassUtils.getPackageName(GetCountryRequest.class));
		marshaller.afterPropertiesSet();
	}

	@Test
	public void testSendAndReceive() {
		WebServiceTemplate ws = new WebServiceTemplate(marshaller);
		GetCountryRequest request = new GetCountryRequest();
		request.setName("Spain");

		System.out.println("http://localhost:" + port + "/ws" + request);
		System.out.println(ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request));

		assertThat(ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request) != null);
	}
}