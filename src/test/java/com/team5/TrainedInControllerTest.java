package com.team5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.beans.AffiliatedWith;
import com.team5.beans.Physician;
import com.team5.beans.Procedures;
import com.team5.beans.TrainedIn;
import com.team5.controllers.TrainedInController;
import com.team5.services.TrainedInService;

import jakarta.servlet.ServletException;

@ExtendWith(MockitoExtension.class)
public class TrainedInControllerTest {

	@InjectMocks
	private TrainedInController trainedInController;

	@Mock
	TrainedInService trainedInService;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(trainedInController).build();
	}

	@Test
	void addNewCertificationTest() throws Exception {
		when(trainedInService.addNewCertification(any(TrainedIn.class))).thenReturn("New Certification Added");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/trained_in")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new AffiliatedWith()))
						.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("New Certification Added", response.getContentAsString());
	}

	@Test
	void getPhysiciansByDepartmentTest() throws Exception {
		Procedures procedures = buildProcedures();
		List<Procedures> proceduresList = new ArrayList<>();
		proceduresList.add(procedures);
		when(trainedInService.getAllProceduresByTreatment()).thenReturn(proceduresList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trained_in");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		List<Procedures> resultProcedures = objectMapper.readValue(response.getContentAsByteArray(),
				new TypeReference<List<Procedures>>() {
				});
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(2, resultProcedures.get(0).getCode());
		assertEquals("Nandini", resultProcedures.get(0).getName());
		assertEquals(100, resultProcedures.get(0).getCost());
	}

	@Test
	void getAllProceduresDoneByPhysicianTest() throws Exception {
		Procedures procedures = buildProcedures();
		List<Procedures> proceduresList = new ArrayList<>();
		proceduresList.add(procedures);
		when(trainedInService.getAllProceduresDoneByPhysician(anyInt())).thenReturn(proceduresList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trained_in/treatment/1");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		List<Procedures> resultProcedures = objectMapper.readValue(response.getContentAsByteArray(),
				new TypeReference<List<Procedures>>() {
				});
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(2, resultProcedures.get(0).getCode());
		assertEquals("Nandini", resultProcedures.get(0).getName());
		assertEquals(100, resultProcedures.get(0).getCost());

	}

	@Test
	void getAllProceduresDoneByPhysicianTestException() throws Exception {
		ServletException ex = assertThrows(ServletException.class, () -> {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trained_in/treatment/ab");
			mockMvc.perform(requestBuilder).andReturn();
		});
		assertTrue(ex.getMessage().contains("id must be Integer"));
	}

	@Test
	void getAllProceduresDoneByPhysicianTestInvalidValueException() throws Exception {
		ServletException ex = assertThrows(ServletException.class, () -> {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trained_in/treatment/-1");
			mockMvc.perform(requestBuilder).andReturn();
		});
		assertTrue(ex.getMessage().contains("id must be greater than 0"));
		
	}

	@Test
	void getAllPhysiciansByTreatmentTest() throws Exception {
		Physician physician = buildPhysician();
		List<Physician> physicianList = new ArrayList<>();
		physicianList.add(physician);
		when(trainedInService.getAllPhysiciansByTreatment(anyInt())).thenReturn(physicianList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trained_in/physians/10");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		List<Physician> resultPhysicians = objectMapper.readValue(response.getContentAsByteArray(),
				new TypeReference<List<Physician>>() {
				});
		assertEquals(1, resultPhysicians.get(0).getEmployeeId());
		assertEquals("Nandini", resultPhysicians.get(0).getName());
		assertEquals("Staff Internist", resultPhysicians.get(0).getPosition());
		assertEquals(11111111, resultPhysicians.get(0).getSsn());
	}

	@Test
	void getAllPhysiciansByTreatmentTestException() throws Exception {
		ServletException ex = assertThrows(ServletException.class, () -> {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trained_in/physians/-1");
			mockMvc.perform(requestBuilder).andReturn();
		});
		assertTrue(ex.getMessage().contains("procedureId must be greater than 0"));
	}

	@Test
	void getProceduresWithExpiredCertificationTest() throws Exception {
		Procedures procedures = buildProcedures();
		List<Procedures> proceduresList = new ArrayList<>();
		proceduresList.add(procedures);
		when(trainedInService.getProceduresExpiringSoonForPhysician(anyLong())).thenReturn(proceduresList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/trained_in/expiredsooncerti/10");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		List<Procedures> resultProcedures = objectMapper.readValue(response.getContentAsByteArray(),
				new TypeReference<List<Procedures>>() {
				});
		assertEquals(2, resultProcedures.get(0).getCode());
		assertEquals("Nandini", resultProcedures.get(0).getName());
		assertEquals(100, resultProcedures.get(0).getCost());
	}

	@Test
	void updateCertificationExpiryTestTrue() throws Exception {
		when(trainedInService.updateCertificationExpiry(anyInt(), anyInt(),any())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/trained_in/23&10")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString("2023-12-10T06:13:37.348Z"))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("true", response.getContentAsString());
	}

	@Test
	void updateCertificationExpiryTestFalse() throws Exception {
		when(trainedInService.updateCertificationExpiry(anyInt(), anyInt(),any())).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/trained_in/23&10")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString("2023-12-10T06:13:37.348Z"))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		assertEquals("false", response.getContentAsString());
	}

	@Test
	void updateCertificationExpiryTestException() throws Exception {
		ServletException ex = assertThrows(ServletException.class, () -> {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/trained_in/1&-10")
					.accept(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString("2023-12-10T06:13:37.348Z"))
					.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(requestBuilder).andReturn();
		});
		assertTrue(ex.getMessage().contains("id must be greater than 0"));
	}

	private Physician buildPhysician() {
		Physician physician = new Physician();
		physician.setEmployeeId(1);
		physician.setName("Nandini");
		physician.setPosition("Staff Internist");
		physician.setSsn(11111111);
		return physician;
	}

	private Procedures buildProcedures() {
		Procedures procedures = new Procedures();
		procedures.setCode(2);
		procedures.setName("Nandini");
		procedures.setCost(100);
		return procedures;
	}

}
