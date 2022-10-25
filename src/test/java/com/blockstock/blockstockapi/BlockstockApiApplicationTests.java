package com.blockstock.blockstockapi;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
				new JSONObject()
						.put("quantity", quantity)
						.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions fetchOrder(String reference) throws Exception {
		return mvc.perform(MockMvcRequestBuilders.get("/orders/" + reference)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}

	private JSONObject toJSONObject(MvcResult result) throws UnsupportedEncodingException, JSONException {
		return new JSONObject(result.getResponse().getContentAsString());
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
	void canRetrieveOrder() throws Exception {
		JSONObject response = toJSONObject(postOrder(10).andReturn());
		String reference = response.getString("reference");

		fetchOrder(reference)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(JsonUnitResultMatchers.json().node("quantity").isEqualTo(10))
				.andExpect(JsonUnitResultMatchers.json().node("reference").isEqualTo(reference));
	}

	@Test
	void cannotRetrieveOrderWithInvalidOrderReference() throws Exception {
		String reference = "INVALID_REFERENCE";

		fetchOrder(reference)
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void canUpdateOrder() throws Exception {
		String reference = toJSONObject(postOrder(10).andReturn()).getString("reference");

		mvc.perform(MockMvcRequestBuilders.patch("/orders/" + reference).content(
				new JSONObject()
						.put("quantity", 15)
						.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		fetchOrder(reference).andExpect(
				JsonUnitResultMatchers.json().node("quantity")
						.isEqualTo(15));

	}

	@Test
	void contextLoads() {
	}

}
