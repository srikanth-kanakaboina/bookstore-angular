package com.bookstore.service;

import java.util.Set;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.domain.security.UserRole;

public interface UserService {
	
	User createUser(User user, Set<UserRole> userRoles);

	User findByUsername(String username);

	User findByEmail(String username);
	User save(User user);
	User findById(Long id);

	void setUserDefaultPayment(long parseLong, User user);

	void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user);
	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	void updateUserShipping(UserShipping userShipping, User user);

	void setUserDefaultShipping(long userShippingId, User user);

}
