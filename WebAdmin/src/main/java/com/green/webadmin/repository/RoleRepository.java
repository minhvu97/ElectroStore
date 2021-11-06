package com.green.webadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.webmodels.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
