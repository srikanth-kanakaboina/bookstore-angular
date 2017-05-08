package com.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.UserPayment;
import com.bookstore.repository.UserPaymentRespository;
import com.bookstore.service.UserPaymentService;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

	@Autowired
	private UserPaymentRespository userPaymentRespository;
	
	@Override
	public UserPayment findById(Long id) {
		return userPaymentRespository.findOne(id);
	}

	@Override
	public void removeById(Long id) {
		userPaymentRespository.delete(id);
	}

}
