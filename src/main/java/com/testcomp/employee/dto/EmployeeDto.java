package com.testcomp.employee.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EmployeeDto {

	private Integer employeeId; 
	
	@NotBlank(message = "First Name is required")
	@Length(max = 100, message = " cannot exceed 100 characters")
	private String firstName;
	
	@NotBlank(message = "Last Name is required")
	@Length(max = 100, message = "Last Name cannot exceed 100 characters")
	private String lastName;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email format is wrong")
	@Length(max = 100, message = "Email cannot exceed 100 characters")
	private String emailAddress;
	
	@NotBlank(message = "Phone is required")
	@Length(max = 15, message = "Phone cannot exceed 15 characters")
	private String phone;
	
	//@NotNull(message = "Birth Date is required")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
		
	@Length(max = 50, message = "Job Title cannot exceed 50 characters")
	private String jobTitle;
		
	@Length(max = 20, message = "Location cannot exceed 20 characters")
	private String location;
	
	@Length(max = 100, message = "Department cannot exceed 100 characters")
	private String department;

	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
		
	private Integer reportingManagerId;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Integer getReportingManagerId() {
		return reportingManagerId;
	}

	public void setReportingManagerId(Integer reportingManagerId) {
		this.reportingManagerId = reportingManagerId;
	}

	@Override
	public String toString() {
		return "EmployeeDto [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", emailAddress=" + emailAddress + ", phone=" + phone + ", birthDate=" + birthDate + ", jobTitle="
				+ jobTitle + ", location=" + location + ", department=" + department + ", startDate=" + startDate
				+ ", reportingManagerId=" + reportingManagerId + "]";
	}
	
	
}
