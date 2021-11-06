package com.green.webclient.shopcart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.green.webmodels.entities.DbCartInfo;

@Repository
public interface DbCartInfoRepository extends JpaRepository<DbCartInfo, Integer> {
	@Query(value = "SELECT * FROM .cartinfo where customer_id = ?1", nativeQuery = true)
	public List<DbCartInfo> getCartInfoByCustomer(Integer customer_id);
}
