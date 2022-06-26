package com.universityApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.universityApp.entity.Faculty;

@Repository
public interface FacultyRepository extends CrudRepository<Faculty, Long> {
	
	public Faculty findByName(String name);
	public List<Faculty> findByCity(String city);
	public List<Faculty> findByNumberOfStudents(int numberOfStudents);

}
