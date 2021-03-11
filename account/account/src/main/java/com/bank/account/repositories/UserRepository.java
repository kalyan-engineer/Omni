package com.bank.account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	public User findByuserid(String userid);
	
	public User findByuseridAndStatus(String userid, String status);

}
