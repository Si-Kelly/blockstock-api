package com.blockstock.blockstockapi;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class BlockstockApiApplicationTests {

	@Autowired
	private MockMvc mvc;

	private ResultActions postOrder(int quantity) throws Exception {
		return mvc.perform(MockMvcRequestBuilders.post("/orders").content(
				String.format("{\"quantity\":%1$d}", quantity)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}

	@Test
	void canCreateOrders() throws Exception {
		postOrder(2)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	void canCreateOrdersWithAnyQuantity() throws Exception {
		int[] quantities = new int[] { 1, 15, 100, -6 };
		for (int i : quantities) {
			postOrder(i)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		}
	}

	@Test
	void creatingOrdersReturnsReference() throws Exception {
		postOrder(2)
				.andExpect(JsonUnitResultMatchers.json().node("reference").isPresent())
				.andExpect(JsonUnitResultMatchers.json().node("reference").isNotEqualTo(""))
				.andExpect(JsonUnitResultMatchers.json().node("reference").isString());
	}

	@Test
	void creatingOrdersReturnsUniqueReference() throws Exception {
		String ref1 = new JSONObject(postOrder(2).andReturn().getResponse().getContentAsString())
				.getString("reference");
		String ref2 = new JSONObject(postOrder(2).andReturn().getResponse().getContentAsString())
				.getString("reference");
		assertNotEquals(ref1, ref2);
	}

	@Test
	void contextLoads() {
	}

}
