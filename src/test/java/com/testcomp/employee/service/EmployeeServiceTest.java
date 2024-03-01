package com.testcomp.employee.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.testcomp.employee.dto.EmployeeDto;
import com.testcomp.employee.entity.Employee;
import com.testcomp.employee.exception.EmployeeException;
import com.testcomp.employee.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService employeeService;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	private EmployeeDto employeeDto;
	
	private Employee employee;
	
	private List<Employee> employeeList;
	
	
	@BeforeEach
	public void setUp() throws Exception {
		
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
		employeeDto.setBirthDate(LocalDate.of(1990, 01, 01));
		employeeDto.setPhone("3334447777");
		employeeDto.setDepartment("IT");
		employeeDto.setJobTitle("Software Engineer");
	}

	@Test
	public void testGetAllEmployees() {
		when(employeeRepository.findAll()).thenReturn(employeeList);
		assertNotNull(employeeService.getAllEmployees());
	}

	@Test
	public void getEmployeeById(){
		when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(employee));
		assertNotNull(employeeService.getEmployeeById(1));
	}
	
	@Test
	public void getEmployeeByIdReturnNull(){
		when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		assertNull(employeeService.getEmployeeById(1));
	}
	
	@Test
	public void deleteEmployee() throws EmployeeException {
		when(employeeRepository.existsById(Mockito.anyInt())).thenReturn(true);
		doNothing().when(employeeRepository).deleteById(1);
		employeeService.deleteEmployee(1);
	}
	
	@Test
	public void createEmployee() {
		when(employeeRepository.save(Mockito.any())).thenReturn(employee);
		assertNotNull(employeeService.createEmployee(employeeDto));
	}
	
	@Test
	public void updateEmployee() throws EmployeeException {
		when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(employee));
		when(employeeRepository.save(Mockito.any())).thenReturn(employee);
		assertNotNull(employeeService.updateEmployee(employeeDto, 1));
	}
}
