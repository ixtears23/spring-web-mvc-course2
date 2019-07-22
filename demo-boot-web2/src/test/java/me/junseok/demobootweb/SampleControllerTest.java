package me.junseok.demobootweb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.junseok.demobootweb.Person;
import me.junseok.demobootweb.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	Marshaller marshaller;
	
	@Test
	public void jsonMessage() throws Exception {
		
		Person person = new Person();
		person.setId(2019l);
		person.setName("junseok");
		
		String jsonString = objectMapper.writeValueAsString(person);
		
		this.mockMvc.perform(get("/jsonMessage")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.content(jsonString))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2019))
				.andExpect(jsonPath("$.name").value("junseok"));
	}
	
	
	@Test
	public void xmlMessage() throws Exception {
		
		Person person = new Person();
		person.setId(2019l);
		person.setName("junseok");
		
		StringWriter stringWriter = new StringWriter();
		Result result = new StreamResult(stringWriter);
		marshaller.marshal(person, result);
		String xmlString = stringWriter.toString();
		
		this.mockMvc.perform(get("/jsonMessage")
					.contentType(MediaType.APPLICATION_XML)
					.accept(MediaType.APPLICATION_XML)
					.content(xmlString))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(xpath("person/name").string("junseok"))
				.andExpect(xpath("person/id").string("2019"));
	}
	
	@Test
	public void hello() throws Exception {
		
		Person person = new Person();
		person.setName("junseok");
		Person savePerson = personRepository.save(person);
		
		
		this.mockMvc.perform(get("/hello")
					.param("id", savePerson.getId().toString()))
				.andDo(print())
				.andExpect(content().string("hello junseok"));
	}
	
	@Test
	public void hellostatic() throws Exception {
		this.mockMvc.perform(get("/mobile/index.html"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString("hello mobile")))
				.andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
	}
	
	@Test
	public void StringMessage() throws Exception {
		this.mockMvc.perform(get("/message").content("hello"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("hello"));
	}
	
}
