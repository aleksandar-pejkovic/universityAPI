package com.universityApp.entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@JsonFormat(pattern = "dd.MM.yyyy.")
	private LocalDate birthday;
	private int age;
	private String city;
	@Column(unique = true)
	private String uniqueStudentNumber;
	@Column(unique = true)
	private String phoneNumber;
	@Column(unique = true)
	private String email;
	@JsonIgnore
	@ManyToMany(mappedBy = "enrolledStudents")
	private List<Faculty> attendingFaculties;
	private int numberOfFacultiesAttending;

	public Student() {
	}

	public Student(Long id, String name, LocalDate birthday, String city,
			String uniqueStudentNumber, String phoneNumber, String email) {
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.setAge();
		this.city = city;
		this.uniqueStudentNumber = uniqueStudentNumber;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.setNumberOfFacultiesAttending();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
		this.setAge();
	}

	public int getAge() {
		return age;
	}

	public void setAge() {
		this.age = Period.between(birthday, LocalDate.now()).getYears();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUniqueStudentNumber() {
		return uniqueStudentNumber;
	}

	public void setUniqueStudentNumber(String uniqueStudentNumber) {
		this.uniqueStudentNumber = uniqueStudentNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Faculty> getAttendingFaculties() {
		return attendingFaculties;
	}

	public void enrollNewFaculty(Faculty newFaculty) {
		this.attendingFaculties.add(newFaculty);
		this.setNumberOfFacultiesAttending();
	}

	public void withdrawFromFaculty(Faculty faculty) {
		this.attendingFaculties.remove(faculty);
		this.setNumberOfFacultiesAttending();
	}

	public int getNumberOfFacultiesAttending() {
		return numberOfFacultiesAttending;
	}

	public void setNumberOfFacultiesAttending() {
		this.numberOfFacultiesAttending = attendingFaculties.size();
	}

}
