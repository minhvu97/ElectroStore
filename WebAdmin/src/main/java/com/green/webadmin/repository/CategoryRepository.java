package com.green.webadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.webmodels.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
