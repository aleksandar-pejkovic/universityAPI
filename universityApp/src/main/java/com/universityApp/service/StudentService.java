package com.universityApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universityApp.entity.Faculty;
import com.universityApp.entity.Student;
import com.universityApp.repository.FacultyRepository;
import com.universityApp.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	FacultyRepository facultyRepository;

	public Student createStudent(Student student) {
		if (exists(student)) {
			System.out.println("Student already exists!");
			throw new EntityExistsException();
		} else if (!isValid(student)) {
			System.out.println("Student is not valid!");
			return null;
		} else {
			return studentRepository.save(student);
		}
	}

	public Student updateStudent(Student student) {
		
		Student storedStudent = findByUniqueStudentNumber(student.getUniqueStudentNumber());
		
		copyNotNullProperties(student, storedStudent);
		
		if (!exists(storedStudent)) {
			System.out.println("Student doesn't exist!");
			throw new EntityExistsException();
		} else if (!isValid(storedStudent)) {
			System.out.println("Student is not valid!");
			return null;
		} else {
			return studentRepository.save(storedStudent);
		}
	}
	
	public String deleteStudent(String uniqueStudentNumber) {
		try {
			Student student = studentRepository.findByUniqueStudentNumber(uniqueStudentNumber);
			studentRepository.delete(student);
			return "Student "+ student.getName() + " deleted";
		} catch (Exception e) {
			e.getMessage();
			return "Student doesn't exist";
		}
		
	}

	public List<Student> findAll() {
		return (List<Student>) studentRepository.findAll();
	}

	public List<Student> findByName(String name) {
		return (List<Student>) studentRepository.findByName(name);
	}
	
	public Student findByUniqueStudentNumber(String uniqueStudentNumber) {
		try {
			return studentRepository.findByUniqueStudentNumber(uniqueStudentNumber);
		} catch (NoSuchElementException e) {
			System.out.println("No such element");
			return null;
		}
	}
	
	public List<Student> findByFaculty(String facultyName){
		Faculty faculty = facultyRepository.findByName(facultyName);
		return faculty.getEnrolledStudents();
	}

	public List<Student> findByCity(String city) {
		return studentRepository.findByCity(city);
	}

	public List<Student> findByAge(int age){
		return studentRepository.findByAge(age);
	}
	
	public List<Student> findByAge(char operator, int age) {
		List<Student> allStudents = findAll();
		List<Student> studentsShortlist = new ArrayList<Student>();
		
		if(operator=='<') {
			studentsShortlist = allStudents.stream()
				.filter(t -> t.getAge() < age)
				.collect(Collectors.toList());
		} else if (operator=='>') {
			studentsShortlist = allStudents.stream()
					.filter(t -> t.getAge() > age)
					.collect(Collectors.toList());
		} else {
			return null;
		}
		return studentsShortlist;
	}
	
	public List<Student> findByAgeAndCity(String city, char operator, int age) {
		List<Student> students = findByCity(city);
		List<Student> studentsShortlist = new ArrayList<Student>();
		
		if(operator=='<') {
			studentsShortlist = students.stream()
				.filter(t -> t.getAge() < age)
				.collect(Collectors.toList());
		} else if (operator=='>') {
			studentsShortlist = students.stream()
					.filter(t -> t.getAge() > age)
					.collect(Collectors.toList());
		} else {
			studentsShortlist = students.stream()
					.filter(t -> t.getAge() == age)
					.collect(Collectors.toList());
		}
		return studentsShortlist;
	}

	private boolean exists(Student student) {
		if (studentRepository.findByUniqueStudentNumber(student.getUniqueStudentNumber()) != null)
			return true;
		return false;
	}

	private boolean isValid(Student student) {
		if (nameIsLongEnough(student.getName()) && isOldEnough(student.getAge()))
			return true;
		return false;
	}

	private boolean nameIsLongEnough(String name) {
		if (name.length() < 3)
			return false;
		return true;
	}

	private boolean isOldEnough(int age) {
		if (age < 12)
			return false;
		return true;
	}

	private void copyNotNullProperties(Student src, Student dest) {
		if(src.getName()!=null && !src.getName().equals(""))
			dest.setName(src.getName());
		if(src.getBirthday()!=null)
			dest.setBirthday(src.getBirthday());
		if(src.getCity()!=null && !src.getCity().equals(""))
			dest.setCity(src.getCity());
		if(src.getPhoneNumber()!=null && !src.getPhoneNumber().equals(""))
			dest.setPhoneNumber(src.getPhoneNumber());
		if(src.getEmail()!=null && !src.getEmail().equals(""))
			dest.setEmail(src.getEmail());
	}
	
	
}
