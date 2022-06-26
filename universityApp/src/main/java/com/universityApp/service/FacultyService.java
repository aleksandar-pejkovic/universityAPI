package com.universityApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universityApp.entity.Faculty;
import com.universityApp.entity.Student;
import com.universityApp.repository.FacultyRepository;
import com.universityApp.repository.StudentRepository;

@Service
public class FacultyService {

	@Autowired
	FacultyRepository facultyRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	public Faculty enrollStudent(String facultyName, String uniqueStudentNumber) {
		
		try {
			Faculty faculty = findByName(facultyName);
			Student student = studentRepository.findByUniqueStudentNumber(uniqueStudentNumber);
			if(faculty.getEnrolledStudents().contains(student)) {
				System.out.println("Student is already enrolled!");
				return null;
			} else {
			student.enrollNewFaculty(faculty);
			return updateFaculty(faculty.addStudent(student));
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		return null;
		
	}
	
	public Faculty removeStudent(String facultyName, String uniqueStudentNumber) {
		
		try {
			Faculty faculty = findByName(facultyName);
			Student student = studentRepository.findByUniqueStudentNumber(uniqueStudentNumber);
			if(faculty.getEnrolledStudents().contains(student)) {
				student.withdrawFromFaculty(faculty);
				return updateFaculty(faculty.removeStudent(student));
			} else {
				System.out.println("Student with unique student number " + student.getUniqueStudentNumber() + " is not enrolled in " + faculty.getName() + "!");
				return null;
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public Faculty createFaculty(Faculty faculty) {
		try {
			if (!exists(faculty)) 
				return facultyRepository.save(faculty);
			} catch (EntityExistsException e) {
				System.out.println(e.getMessage());
				System.out.println("Faculty already exists!");
			}
		return faculty;
		}

	public Faculty updateFaculty(Faculty faculty) {
		try {
			if (exists(faculty))
				return facultyRepository.save(faculty);
		} catch (EntityNotFoundException e) {
			System.out.println("Faculty doesn't exist!");
		}
		return new Faculty();
	}

	public String deleteFaculty(Faculty faculty) {
		if (exists(faculty)) {
			facultyRepository.delete(faculty);
			return "Faculty " + faculty.getName() + " deleted";
		}
		return "Faculty doesn't exist";
	}

	public String deleteFaculty(String name) {
		try {
			Faculty faculty = facultyRepository.findByName(name);
			facultyRepository.delete(faculty);
			return "Faculty " + faculty.getName() + " deleted";
		} catch (EntityNotFoundException e) {
			e.getMessage();
			return "Faculty doesn't exist";			
		}
	}
	
	public List<Faculty> findAll() {
		return (List<Faculty>) facultyRepository.findAll();
	}
	
	public Faculty findByName(String name) {
		return facultyRepository.findByName(name);
	}
	
	public List<Faculty> findByStudent(String uniqueStudentNumber){
		Student student = studentRepository.findByUniqueStudentNumber(uniqueStudentNumber);
		return student.getAttendingFaculties();
	}
	
	public List<Faculty> findByCity(String city) {
		return facultyRepository.findByCity(city);
	}
	
	public List<Faculty> findByNumberOfStudents(int numberOfStudents){
		return facultyRepository.findByNumberOfStudents(numberOfStudents);
	}
	
	public List<Faculty> findByNumberOfStudents(char operator, int numberOfStudents) {
		List<Faculty> allFaculties = findAll();
		List<Faculty> facultyShortlist = new ArrayList<Faculty>();
		
		if(operator=='<') {
			facultyShortlist = allFaculties.stream()
				.filter(t -> t.getNumberOfStudents() < numberOfStudents)
				.collect(Collectors.toList());
		} else if (operator=='>') {
			facultyShortlist = allFaculties.stream()
					.filter(t -> t.getNumberOfStudents() > numberOfStudents)
					.collect(Collectors.toList());
		}
		
		return facultyShortlist;
		
		
	}
	
	public List<Faculty> findByNumberOfStudentsAndCity(String city, char operator, int numberOfStudents) {
		List<Faculty> faculties = findByCity(city);
		List<Faculty> facultyShortlist = new ArrayList<Faculty>();
		
		if(operator=='<') {
			facultyShortlist = faculties.stream()
				.filter(t -> t.getNumberOfStudents() < numberOfStudents)
				.collect(Collectors.toList());
		} else if (operator=='>') {
			facultyShortlist = faculties.stream()
					.filter(t -> t.getNumberOfStudents() > numberOfStudents)
					.collect(Collectors.toList());
		} else {
			facultyShortlist = faculties.stream()
					.filter(t -> t.getNumberOfStudents() == numberOfStudents)
					.collect(Collectors.toList());
		}
		return facultyShortlist;
	}

	private boolean exists(Faculty faculty) {
		if (facultyRepository.findByName(faculty.getName()) != null)
			return true;
		return false;
	}

}
