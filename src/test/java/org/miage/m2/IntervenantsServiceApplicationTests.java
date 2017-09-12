package org.miage.m2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.miage.m2.boundary.IntervenantRessource;
import org.miage.m2.entity.Intervenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntervenantsServiceApplicationTests {

	/*	@Test
		public void contextLoads() {
		}
	*/

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	IntervenantRessource ir;

	@Before
	public void setupContext() {
		ir.deleteAll();
	}

	@Test
	public void getOneApi() {
		Intervenant i1 = new Intervenant("Tom", "Sawyer", "Nancy", "54000");
		i1.setId(UUID.randomUUID().toString());
		ir.save(i1);

		ResponseEntity<String> response = restTemplate.getForEntity("/intervenants/" + i1.getId(), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("Tom");
	}

	@Test
	public void getAllApi() throws Exception {
		Intervenant i1 = new Intervenant("Tom", "Sawyer", "Nancy", "54000");
		i1.setId(UUID.randomUUID().toString());
		ir.save(i1);
		Intervenant i2 = new Intervenant("Robert", "Blanc", "Les Arcs", "73000");
		i2.setId(UUID.randomUUID().toString());
		ir.save(i2);

		ResponseEntity<String> response = restTemplate.getForEntity("/intervenants", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("Tom");
		assertThat(response.getBody()).contains("Blanc");
		List<String> list = JsonPath.read(response.getBody(), "$..intervenants..nom");
		assertThat(list).hasSize(2);
	}

	@Test
	public void notFoundApi() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity("/intervenants/150", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void postApi() throws Exception {
		Intervenant i1 = new Intervenant("Tom", "Sawyer", "Nancy", "54000");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(this.toJsonString(i1), headers);

		ResponseEntity<?> response = restTemplate.postForEntity("/intervenants", entity, ResponseEntity.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI location = response.getHeaders().getLocation();
		response = restTemplate.getForEntity(location, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void deleteApi() throws Exception {
		Intervenant i1 = new Intervenant("Tom", "Sawyer", "Nancy", "54000");
		i1.setId(UUID.randomUUID().toString());
		ir.save(i1);
		restTemplate.delete("/intervenants/" + i1.getId());

		ResponseEntity<?> response = restTemplate.getForEntity("/intervenants/" + i1.getId(), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void putApi() throws Exception {
		Intervenant i1 = new Intervenant("Tom", "Sawyer", "Nancy", "54000");
		i1.setId(UUID.randomUUID().toString());
		ir.save(i1);
		i1.setCommune("Arbois");
		i1.setCodepostal("39000");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(this.toJsonString(i1), headers);

		restTemplate.put("/intervenants/" + i1.getId(), entity);

		ResponseEntity<String> response = restTemplate.getForEntity("/intervenants/" + i1.getId(), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("Tom");
		String code = JsonPath.read(response.getBody(), "$.codepostal");
		assertThat(code).isEqualTo(i1.getCodepostal());
	}

	private String toJsonString(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r);
	}
}