package com.universityApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universityApp.entity.Student;
import com.universityApp.service.StudentService;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentService studentService;

	@PostMapping("/create")
	public Student createStudent(@RequestBody Student student) {
		return studentService.createStudent(student);
	}
	
	@PutMapping("/update")
	public Student updateStudent(@RequestBody Student student) {
		try {
			return studentService.updateStudent(student);
		} catch (Exception e) {
			System.out.println("Student doesn't exist!");
			return null;
		}
	}
	
	@DeleteMapping("/delete/{uniqueStudentNumber}")
	public String deleteStudent(@PathVariable String uniqueStudentNumber) {
		return studentService.deleteStudent(uniqueStudentNumber);
	}
	
	@GetMapping("/getAll")
	public List<Student> getAllStudents(){
		return studentService.findAll();
	}
	
	@GetMapping("/name/{name}")
	public List<Student> getStudentByName(@PathVariable String name){
		return studentService.findByName(name);
	}
	
	@GetMapping("/usn/{uniqueStudentNumber}")
	public Student getByUniqueStudentNumber(@PathVariable String uniqueStudentNumber){
		return studentService.findByUniqueStudentNumber(uniqueStudentNumber);
	}
	
	@GetMapping("/facultyName/{facultyName}")
	public List<Student> getByFaculty(@PathVariable String facultyName){
		return studentService.findByFaculty(facultyName);
	}
	
	@GetMapping("/city/{city}")
	public List<Student> getStudentByCity(@PathVariable String city){
		return studentService.findByCity(city);
	}
	
	@GetMapping("/age/{age}")
	public List<Student> getStudentByAge(@PathVariable int age){
		return studentService.findByAge(age);
	}
	
	@GetMapping("/operator/{operator}/age/{age}")
	public List<Student> getStudentsByAge(@PathVariable char operator, @PathVariable int age){
		return studentService.findByAge(operator, age);
	}
	
	@GetMapping("/city/{city}/operator/{operator}/age/{age}")
	public List<Student> getStudentsByAgeAndCity(@PathVariable String city, @PathVariable char operator, @PathVariable int age){
		return studentService.findByAgeAndCity(city, operator, age);
	}
	
}
