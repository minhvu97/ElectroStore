package com.green.webadmin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.webadmin.repository.UserRepository;
import com.green.webmodels.entities.User;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	public User getUserById(Integer id){
		return userRepository.getById(id);
	}
	
	public Optional<User> findUserById(Integer id){
		return userRepository.findById(id);
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	
	public List<String> getAllUserName(){
		return userRepository.getAllUserName();
	}
}
