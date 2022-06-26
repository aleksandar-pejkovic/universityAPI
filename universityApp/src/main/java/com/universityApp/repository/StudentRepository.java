package com.universityApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.universityApp.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>{

	public List<Student> findByName(String name);
	public List<Student> findByCity(String city);
	public Student findByUniqueStudentNumber(String uniqueStudentNumber);
	public Student findByPhoneNumber(String phoneNumber);
	public Student findByEmail(String email);
	public List<Student> findByAge(int age);
	
}
