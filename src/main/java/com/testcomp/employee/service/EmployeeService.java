package com.testcomp.employee.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testcomp.employee.dto.EmployeeDto;
import com.testcomp.employee.entity.Employee;
import com.testcomp.employee.exception.EmployeeException;
import com.testcomp.employee.repository.EmployeeRepository;

/**
 * 
 * @author tndumu
 * 
 * This service carries out employee management operations
 */
@Service
public class EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * Returns all employees
	 * 
	 * @return
	 */
	public List<Employee> getAllEmployees() {
		logger.info("getAllEmployees: returning all employes");
    	return employeeRepository.findAll();
    }
    
	/**
	 * Returns employee with parameter ID
	 * 
	 * @param id
	 * @return
	 */
    public Employee getEmployeeById(Integer id) {
    	logger.info("getEmployeeById: return employee with ID {}", id);
    	Optional<Employee> optionalEmployee =  employeeRepository.findById(id);
    	if(optionalEmployee.isPresent()) {
    		return optionalEmployee.get();
    	}
    	logger.info("getEmployeeById: Employee with ID {} was not found", id);
    	return null;
    }
    
    /**
     * Deletes the employee with parameter ID
     * 
     * @param id
     * @return
     * @throws EmployeeException
     */
    public Integer deleteEmployee(Integer id) throws EmployeeException{
    	logger.info("deleteEmployee: deleting employee with ID {}", id);
    	if(employeeRepository.existsById(id)) {
    		employeeRepository.deleteById(id);
    		return id;
    	}
    	logger.info("deleteEmployee: Employee with ID {} was not found", id);
    	throw new EmployeeException("");
    }
    
    /**
     * Creates a new employee using the parameter Dto
     * 
     * @param employeeDto
     * @return
     */
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
    	logger.info("createEmployee: Creating employee with {}", employeeDto);
    	
    	return createOrUpdateEmployee(new Employee(), employeeDto);
    }
    
    /**
     * Updates the employee with the parameter ID using the Dto
     * 
     * @param employeeDto
     * @param id
     * @return
     * @throws EmployeeException
     */
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Integer id) throws EmployeeException {
    	logger.info("updateEmployee: Updating employee with data - {}", employeeDto);
    	
    	Optional<Employee> optionalEmployee = employeeRepository.findById(id);
    	
    	if(optionalEmployee.isPresent()) {
    		return createOrUpdateEmployee(optionalEmployee.get(), employeeDto);
    	}
    	logger.info("updateEmployee: Employee with ID {} was not found", id);
    	throw new EmployeeException("");
    }
    
    /**
     * Copy data from dto object into persistable employee object and save
     * 
     * @param employee
     * @param employeeDto
     * @return
     */
    private EmployeeDto createOrUpdateEmployee(Employee employee, EmployeeDto employeeDto) {
    	
    	employee.setFirstName(employeeDto.getFirstName());
    	employee.setLastName(employeeDto.getLastName());
    	employee.setEmailAddress(employeeDto.getEmailAddress());
    	employee.setBirthDate(employeeDto.getBirthDate());
    	employee.setPhone(employeeDto.getPhone());
    	employee.setDepartment(employeeDto.getDepartment());
    	employee.setJobTitle(employeeDto.getJobTitle());
    	employee.setLocation(employeeDto.getLocation());
    	employee.setStartDate(employeeDto.getStartDate());
    	
    	if(employeeDto.getReportingManagerId() != null) {
    		employee.setReportingManager(employeeRepository.findById(employeeDto.getReportingManagerId()).get());
    	}
    	
    	employeeRepository.save(employee);
    	logger.info("Employee information successfully saved.");
    	employeeDto.setEmployeeId(employee.getEmployeeId());
    	return employeeDto;
    }
}
