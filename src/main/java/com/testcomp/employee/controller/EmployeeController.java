package com.testcomp.employee.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.testcomp.employee.constants.EmployeeConstants;
import com.testcomp.employee.dto.EmployeeDto;
import com.testcomp.employee.entity.Employee;
import com.testcomp.employee.exception.EmployeeException;
import com.testcomp.employee.service.EmployeeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class for incoming requests to handle Employee 
 * management operations 
 * 
 */
@RestController
public class EmployeeController {
	 
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	
    @GetMapping("/employees")
    @ApiOperation(value = "Get all empployees", notes = "Returns all employees of the company", nickname = "getAllEmployees")
    @ApiResponses({@ApiResponse(code = 200, message = EmployeeConstants.HTTP_RESPONSE_200)})
    public ResponseEntity<?> getAllEmployees() {    
    	logger.info("getAllEmployees: get all employees");
    	return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }
    
    @GetMapping("/employees/{id}")
    @ApiOperation(value = "Get employee by employee's ID", notes = "", nickname = "getEmployeeById")
    @ApiResponses({@ApiResponse(code = 200, message = EmployeeConstants.HTTP_RESPONSE_200),
        @ApiResponse(code = 404, message = EmployeeConstants.HTTP_RESPONSE_404)})
    public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id", required = true) Integer id) {
    	logger.info("getEmployeeById: get an employee by their ID");
    	Employee employee = employeeService.getEmployeeById(id);
    	if(employee != null) {
    		return new ResponseEntity<>(employee, HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/employees/{id}")
    @ApiOperation(value = "Delete an employee", notes = "Delete the employee with the parameter ID", nickname = "deleteEmployee")
    @ApiResponses({@ApiResponse(code = 200, message = EmployeeConstants.HTTP_RESPONSE_200),
        @ApiResponse(code = 500, message = EmployeeConstants.HTTP_RESPONSE_500)})
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id", required = true) Integer id) {
    	logger.info("deleteEmployee: deleting an employee");
    	try {
	    	employeeService.deleteEmployee(id);
	    	return new ResponseEntity<>(HttpStatus.OK);
    	}
    	catch(EmployeeException e) {
    		logger.error("{}", e);
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    @PostMapping("/employees")
    @ApiOperation(value = "Create an employee", notes = "Create a new employee with the provided information", nickname = "createEmployee")
    @ApiResponses({@ApiResponse(code = 201, message = EmployeeConstants.HTTP_RESPONSE_201),
        @ApiResponse(code = 400, message = EmployeeConstants.HTTP_RESPONSE_400), 
        @ApiResponse(code = 500, message = EmployeeConstants.HTTP_RESPONSE_500)})
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeDto employeeDto, BindingResult result) {
    	logger.info("createEmployee: creating new employee");
    	if(!result.hasErrors()) {
    		EmployeeDto emp = employeeService.createEmployee(employeeDto);
	    	if(emp != null) {
	    		return new ResponseEntity<>(emp, HttpStatus.CREATED);
	    	}	
	    	else {
	    		logger.info("createEmployee: Failed to save employee.");
	    		return new ResponseEntity<>("Failed to save employee", HttpStatus.INTERNAL_SERVER_ERROR);
	    	}
    	}
    	return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/employees/{id}")
    @ApiOperation(value = "Update employee information", notes = "Update the employee with the parameter ID", nickname = "updateEmployee")
    @ApiResponses({@ApiResponse(code = 201, message = EmployeeConstants.HTTP_RESPONSE_200),
        @ApiResponse(code = 400, message = EmployeeConstants.HTTP_RESPONSE_400), 
        @ApiResponse(code = 500, message = EmployeeConstants.HTTP_RESPONSE_500)})
    public ResponseEntity<?> updateEmployee(@PathVariable(value = "id", required = true) Integer id, @RequestBody @Valid EmployeeDto employeeDto, BindingResult result) {
    	logger.info("updateEmployee: updating new employee");
    	if(!result.hasErrors()) {
    		try {
    			EmployeeDto emp = employeeService.updateEmployee(employeeDto, id);
    			return new ResponseEntity<>(emp, HttpStatus.OK);
    		}
    		catch(EmployeeException e) {
    			logger.error("{}", e);
    			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	
    		}    		
    	}
    	return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
}