package com.universityApp.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

import com.universityApp.entity.Faculty;
import com.universityApp.service.FacultyService;

@CrossOrigin
@RestController
@RequestMapping("/faculty")
public class FacultyController {
	
	@Autowired
	FacultyService facultyService;

	@PostMapping("/create")
	public Faculty createFaculty(@RequestBody Faculty faculty) {
		return facultyService.createFaculty(faculty);
	}
	
	@PutMapping("/update")
	public Faculty updateFaculty(@RequestBody Faculty faculty) {
		try {
			Faculty storedFaculty = facultyService.findByName(faculty.getName());
			BeanUtils.copyProperties(faculty, storedFaculty, "id");
			return facultyService.updateFaculty(storedFaculty);
		} catch (Exception e) {
			System.out.println("Faculty doesn't exist!");
			return null;
		}
		
	}
	
	@DeleteMapping("/delete/{name}")
	public String deleteStudent(@PathVariable String name) {
		return facultyService.deleteFaculty(name);
	}
	
	@GetMapping("/getAll")
	public List<Faculty> getAllFaculties(){
		return facultyService.findAll();
	}
	
	@GetMapping("/name/{name}")
	public Faculty getFacultyByName(@PathVariable String name){
		return facultyService.findByName(name);
	}
	
	@GetMapping("/usn/{uniqueStudentNumber}")
	public List<Faculty> getByFaculty(@PathVariable String uniqueStudentNumber){
		return facultyService.findByStudent(uniqueStudentNumber);
	}
	
	@GetMapping("/city/{city}")
	public List<Faculty> getFacultiesByCity(@PathVariable String city){
		return facultyService.findByCity(city);
	}
	
	@GetMapping("/numberOfStudents/{numberOfStudents}")
	public List<Faculty> getFacultiesByNumberOfStudents(@PathVariable int numberOfStudents){
		return facultyService.findByNumberOfStudents(numberOfStudents);
	}	
	
	@GetMapping("/operator/{operator}/numberOfStudents/{numberOfStudents}")
	public List<Faculty> getFacultiesByNumberOfStudents(@PathVariable char operator, @PathVariable int numberOfStudents){
		return facultyService.findByNumberOfStudents(operator, numberOfStudents);
	}
	
	@GetMapping("/city/{city}/operator/{operator}/numberOfStudents/{numberOfStudents}")
	public List<Faculty> getFacultiesByNumberOfStudentsAndCity(@PathVariable String city, @PathVariable char operator, @PathVariable int numberOfStudents){
		return facultyService.findByNumberOfStudentsAndCity(city, operator, numberOfStudents);
	}
	
	@PutMapping("/enroll/{facultyName}/usn/{uniqueStudentNumber}")
	public Faculty enrollStudent(@PathVariable String facultyName, @PathVariable String uniqueStudentNumber) {
		return facultyService.enrollStudent(facultyName, uniqueStudentNumber);
	}
	
	@PutMapping("/remove/{facultyName}/usn/{uniqueStudentNumber}")
	public Faculty removeStudent(@PathVariable String facultyName, @PathVariable String uniqueStudentNumber) {
		return facultyService.removeStudent(facultyName, uniqueStudentNumber);
	}
	
}
