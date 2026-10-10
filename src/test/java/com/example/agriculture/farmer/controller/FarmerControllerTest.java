package com.example.agriculture.farmer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.agriculture.farmer.dto.FarmerResponseDto;
import com.example.agriculture.farmer.service.FarmerService;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@WebMvcTest(FarmerController.class)
@AutoConfigureMockMvc(addFilters = false)
class FarmerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private FarmerService farmerService;

	@Test
	void testGetAllFarmers() throws Exception {

		FarmerResponseDto farmer = new FarmerResponseDto();
		farmer.setFarmerId(1);
		farmer.setFarmerName("Ravi");
		farmer.setEmail("ravi@gmail.com");

		when(farmerService.getAll()).thenReturn(List.of(farmer));

		mockMvc.perform(get("/api/farmers")).andExpect(status().isOk()).andExpect(jsonPath("$[0].farmerId").value(1))
				.andExpect(jsonPath("$[0].farmerName").value("Ravi"))
				.andExpect(jsonPath("$[0].email").value("ravi@gmail.com"));
	}
}
