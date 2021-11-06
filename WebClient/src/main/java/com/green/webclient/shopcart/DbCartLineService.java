package com.green.webclient.shopcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webmodels.entities.DbCartInfo;

@Service
public class DbCartLineService {
	@Autowired
	private DbCartInfoRepository dbCartInfoRepository;

	public List<DbCartInfo> getCartInfoByCustomer(Integer id){
		return dbCartInfoRepository.getCartInfoByCustomer(id);
	}
}
