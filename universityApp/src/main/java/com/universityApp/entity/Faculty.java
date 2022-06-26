package com.universityApp.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "faculties")
public class Faculty {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String name;
	private String city;
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "student_enrolled", joinColumns = @JoinColumn(name = "faculty_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private List<Student> enrolledStudents;
	private int numberOfStudents;

	public Faculty() {
	}

	public Faculty(Long id, String name, String city) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.setNumberOfStudents();
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Student> getEnrolledStudents() {
		return enrolledStudents;
	}

	public Faculty addStudent(Student student) {
		this.enrolledStudents.add(student);
		this.setNumberOfStudents();
		return this;
	}

	public Faculty removeStudent(Student student) {
		this.enrolledStudents.remove(student);
		this.setNumberOfStudents();
		return this;
	}

	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents() {
		this.numberOfStudents = this.enrolledStudents.size();
	}

}
