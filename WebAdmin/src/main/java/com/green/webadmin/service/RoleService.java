package com.green.webadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.webadmin.repository.RoleRepository;
import com.green.webmodels.entities.Role;

@Service
@Transactional
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	public List<Role> getAllRole(){
		return roleRepository.findAll();
	}
}
