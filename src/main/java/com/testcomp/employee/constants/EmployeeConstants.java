package com.testcomp.employee.constants;

public interface EmployeeConstants {

	  String HTTP_RESPONSE_200 = "Success. Request completed";
	  String HTTP_RESPONSE_201 = "Created";
	  String HTTP_RESPONSE_400 = "BAD Request";
	  String HTTP_RESPONSE_404 = "Not Found";
	  String HTTP_RESPONSE_500 = "Internal Server error";
	  
	  enum Cities {	  
		  DC("DC"),
		  BALTIMORE("Baltimore"),
		  NEW_YORK("New York");
		  
		  private String city;
		  
		  private  Cities(String city) {
			this.city = city;  
		  }

		  public String getCity() {
			  return city;
		  }  
	  }
	  
}
