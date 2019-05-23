package me.junseok.demobootweb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
		this.mockMvc.perform(get("/index.html"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(Matchers.containsString("hello index")));
		
	}
}
