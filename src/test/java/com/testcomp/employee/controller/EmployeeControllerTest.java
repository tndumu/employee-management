package com.testcomp.employee.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testcomp.employee.dto.EmployeeDto;
import com.testcomp.employee.entity.Employee;
import com.testcomp.employee.service.EmployeeService;

//@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	private EmployeeDto employeeDto;
	
	private Employee employee;
	
	private List<Employee> employeeList;
	
	@BeforeAll
	public void setUp(WebApplicationContext webApplicationContext) {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	     
		employee = new Employee();
		employee.setFirstName("Jane");
    	employee.setLastName("Doe");
    	employee.setEmailAddress("test@test.com");
    	employee.setBirthDate(LocalDate.of(1990, 01, 01));
    	employee.setPhone("3334447777");
    	employee.setDepartment("IT");
    	employee.setJobTitle("Software Engineer");
		
		employeeList = new ArrayList<Employee>();
		employeeList.add(employee);
		
		employeeDto = new EmployeeDto();
		employeeDto.setFirstName("Jane");
		employeeDto.setLastName("Doe");
		employeeDto.setEmailAddress("test@test.com");
		//employeeDto.setBirthDate(LocalDate.of(1990, 01, 01));
		employeeDto.setPhone("3334447777");
		employeeDto.setDepartment("IT");
		employeeDto.setJobTitle("Software Engineer");
	}
	
	@Test
	public void testGetAllEmployees() throws Exception
	{
		when(employeeService.getAllEmployees()).thenReturn(employeeList);
		
		mvc.perform(MockMvcRequestBuilders
	  			.get("/employees")
	  			.accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk());
	      //.andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
	      //.andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}
	
	
	@Test
	public void testGetEmployeeById() throws Exception
	{
		when(employeeService.getEmployeeById(Mockito.anyInt())).thenReturn(employee);
		
		mvc.perform(MockMvcRequestBuilders
	  			.get("/employees/1")
	  			.accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk());
	      //.andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
	      //.andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}
	
	@Test
	public void testDeleteEmployee() throws Exception
	{
		when(employeeService.getAllEmployees()).thenReturn(employeeList);
		
		mvc.perform(MockMvcRequestBuilders
	  			.delete("/employees/1")
	  			.accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk());
	      //.andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
	      //.andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}
	
	
	@Test
	public void testCreateEmployee() throws Exception 
	{
		employeeDto.setFirstName("Jane");
		
		when(employeeService.createEmployee(Mockito.any())).thenReturn(employeeDto);
		
		mvc.perform( MockMvcRequestBuilders
		      .post("/employees")
		      .content(asJsonString(employeeDto))
		      .contentType(MediaType.APPLICATION_JSON)
		      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated());
	     // .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").exists());
	}
	
	@Test
	public void testCreateEmployeeWithNoFirstName() throws Exception 
	{
		employeeDto.setFirstName(null);
		mvc.perform( MockMvcRequestBuilders
		      .post("/employees")
		      .content(asJsonString(employeeDto))
		      .contentType(MediaType.APPLICATION_JSON)
		      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testUpdateEmployee() throws Exception 
	{
		employeeDto.setFirstName("Jane");
		when(employeeService.updateEmployee(Mockito.any(), Mockito.anyInt())).thenReturn(employeeDto);
		
		mvc.perform( MockMvcRequestBuilders
		      .put("/employees/1")
		      .content(asJsonString(employeeDto))
		      .contentType(MediaType.APPLICATION_JSON)
		      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	     // .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").exists());
	}

	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
