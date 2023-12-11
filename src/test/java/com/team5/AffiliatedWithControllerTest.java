package com.team5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.team5.beans.Department;
import com.team5.beans.Physician;
import com.team5.controllers.AffiliatedWithController;
import com.team5.services.AffiliatedWithService;

import jakarta.servlet.ServletException;

@ExtendWith(MockitoExtension.class)
public class AffiliatedWithControllerTest {

	@InjectMocks
	private AffiliatedWithController affiliatedWithController;

	@Mock
	AffiliatedWithService affliatedWithService;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(affiliatedWithController).build();
	}

	@Test
	void addAfliatedWithExistingPhysicianTest() throws Exception {
		when(affliatedWithService.addAffliatedWithService(any(AffiliatedWith.class))).thenReturn(new Physician());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/affiliated_with/post")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new AffiliatedWith()))
						.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("Record Created Successfully", response.getContentAsString());
	}

	@Test
	void getPhysiciansByDepartmentTest() throws Exception {
		Physician physician = buildPhysician();
		List<Physician> physicianList = new ArrayList<>();
		physicianList.add(physician);
		when(affliatedWithService.getPhysiciansByDepartment(anyInt())).thenReturn(physicianList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/affiliated_with/physicians/1");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		List<Physician> resultPhysician = objectMapper.readValue(response.getContentAsByteArray(),
				new TypeReference<List<Physician>>() {
				});
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(1, resultPhysician.get(0).getEmployeeId());
		assertEquals("Nandini", resultPhysician.get(0).getName());
		assertEquals("Staff Internist", resultPhysician.get(0).getPosition());
		assertEquals(11111111, resultPhysician.get(0).getSsn());
	}

	@Test
	void getPhysiciansByDepartmentTestException() throws Exception {
		ServletException ex = assertThrows(ServletException.class, () -> {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/affiliated_with/physicians/-1");
			mockMvc.perform(requestBuilder).andReturn();
		});
		assertTrue(ex.getMessage().contains("id must greater than 0"));
	}

	@Test
	void getDepartmentsByPhysicianTest() throws Exception {
		Department department = buildDepartment();
		List<Department> departmentList = new ArrayList<>();
		departmentList.add(department);
		when(affliatedWithService.getDepartmentsByPhysician(anyInt())).thenReturn(departmentList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/affiliated_with/department/100");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		List<Department> resultDepartment = objectMapper.readValue(response.getContentAsByteArray(),
				new TypeReference<List<Department>>() {
				});
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(100, resultDepartment.get(0).getDepartmentId());
		assertEquals("Vikranth", resultDepartment.get(0).getName());
	}

	@Test
	void getDepartmentsByPhysicianTestException() throws Exception {
		ServletException ex = assertThrows(ServletException.class, () -> {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/affiliated_with/department/-1");
			mockMvc.perform(requestBuilder).andReturn();
		});
		assertTrue(ex.getMessage().contains("id must greater than 0"));
	}

	@Test
	void getPhysiciansCountByDepartmentTest() throws Exception {
		Physician physician = buildPhysician();
		List<Physician> physicianList = new ArrayList<>();
		physicianList.add(physician);
		when(affliatedWithService.getPhysiciansByDepartment(anyInt())).thenReturn(physicianList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/affiliated_with/countphysician/100");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("1", response.getContentAsString());
	}

	@Test
	void getPhysiciansCountByDepartmentTestException() throws Exception {
		ServletException ex = assertThrows(ServletException.class, () -> {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/affiliated_with/countphysician/-1");
			mockMvc.perform(requestBuilder).andReturn();
		});
		assertTrue(ex.getMessage().contains("id must greater than 0"));
	}

	@Test
	void isPrimaryAffiliationTest() throws Exception {
		when(affliatedWithService.isPrimaryAffiliation(anyInt())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/affiliated_with/primary/1");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("true", response.getContentAsString());
	}

	@Test
	void isPrimaryAffiliationTestException() throws Exception {
		ServletException ex = assertThrows(ServletException.class, () -> {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/affiliated_with/primary/-1");
			mockMvc.perform(requestBuilder).andReturn();
		});
		assertTrue(ex.getMessage().contains("id must greater than 0"));
	}

	@Test
	void updatePrimaryAffiliationTest() throws Exception {
		when(affliatedWithService.updatePrimaryAffiliation(anyInt(), anyBoolean())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/affiliated_with/primary/1/true");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("true", response.getContentAsString());
	}

	@Test
	void updatePrimaryAffiliationTestException() throws Exception {
		ServletException ex = assertThrows(ServletException.class, () -> {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/affiliated_with/primary/-1/true");
			mockMvc.perform(requestBuilder).andReturn();
		});
		assertTrue(ex.getMessage().contains("id must greater than 0"));
	}

	private Physician buildPhysician() {
		Physician physician = new Physician();
		physician.setEmployeeId(1);
		physician.setName("Nandini");
		physician.setPosition("Staff Internist");
		physician.setSsn(11111111);
		return physician;
	}

	private Department buildDepartment() {
		Department department = new Department();
		department.setDepartmentId(100);
		department.setHead(buildPhysician());
		department.setName("Vikranth");
		return department;
	}

}
