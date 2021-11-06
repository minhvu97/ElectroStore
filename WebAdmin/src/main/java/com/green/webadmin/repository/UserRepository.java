package com.green.webadmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webmodels.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	//@Query("SELECT u FROM User u WHERE u.username = ?1")
	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);
	
	@Query("SELECT username From User")
	public List<String> getAllUserName();
}
