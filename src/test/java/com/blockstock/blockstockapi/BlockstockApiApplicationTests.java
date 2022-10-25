package com.blockstock.blockstockapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class BlockstockApiApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void canCreateOrders() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/orders").content(
				"{\"quantity\":2}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	void creatingOrdersReturnsReference() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/orders").content(
				"{\"quantity\":2}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(JsonUnitResultMatchers.json().node("reference").isPresent())
				.andExpect(JsonUnitResultMatchers.json().node("reference").isString());
	}

	@Test
	void contextLoads() {
	}

}
