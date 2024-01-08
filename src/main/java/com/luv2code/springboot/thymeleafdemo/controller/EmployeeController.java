package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}

	@GetMapping("/list")
	public ModelAndView listEmployees(ModelAndView theModel) {

		// get the employees from db
		List<Employee> theEmployees = employeeService.findAll();

		// add to the spring model
		theModel.addObject("employees", theEmployees);
		theModel.setViewName("/employees/list-employees");

		return theModel;
	}

	@GetMapping("/showFormForAdd")
	public ModelAndView showFormForAdd(ModelAndView theModel) {

		// create model attribute to bind form data
		Employee theEmployee = new Employee();

		theModel.addObject("employee", theEmployee);
		theModel.setViewName("/employees/employee-form");

		return theModel;
	}

	@GetMapping("/showFormForUpdate")
	public ModelAndView showFormForUpdate(@RequestParam("employeeId") int theId,
									ModelAndView theModel) {

		// get the employee from the service
		Employee theEmployee = employeeService.findById(theId);

		// set employee as a model attribute to pre-populate the form
		theModel.addObject("employee", theEmployee);
		theModel.setViewName("/employees/employee-form");

		// send over to our form
		return theModel;
	}

	@PostMapping("/save")
	public ModelAndView saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

		// save the employee
		employeeService.save(theEmployee);

		// use a redirect to prevent duplicate submissions
		return new ModelAndView("redirect:/employees/list");
	}

	@GetMapping("/delete")
	public ModelAndView delete(@RequestParam("employeeId") int theId) {

		// delete the employee
		employeeService.deleteById(theId);

		// redirect to /employees/list
		return new ModelAndView("redirect:/employees/list");
	}
}









