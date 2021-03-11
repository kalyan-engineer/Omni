package com.bank.user.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bank.user.DTO.AllUsers;
import com.bank.user.DTO.Pagination;
import com.bank.user.DTO.UserDTO;
import com.bank.user.delegate.UserDelegate;
import com.bank.user.handler.Helper;
import com.bank.user.model.User;
import com.bank.user.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserServiceImpl {
	
	@Autowired
	UserDelegate userDelegate;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	Helper helper;

	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value="/register", method = RequestMethod.POST)
    public UserDTO saveUser(@RequestBody UserDTO userDTO){
		UserDTO savedUser = userDelegate.save(userDTO);
        return savedUser;
    }

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public UserDTO getUser(@PathVariable("id") String id) {
		UserDTO userDTO = userDelegate.getUserById(id);
		return userDTO;
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") String id) {
		String deleteuser = userDelegate.deleteuser(id);
		return deleteuser;
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/get/all", method = RequestMethod.POST)
	public AllUsers getAllAccountHolders(@RequestBody AllUsers users) {
		Pagination page = users.getPage();
		Pageable pageable = null;
		if(null == page) {
			pageable = PageRequest.of(0, 10);
		}else {
			int pageNum = page.getPage();
			int pageSize = page.getSize();
			pageable = PageRequest.of(pageNum, pageSize);
		}
		
		Page<User> allUsers = userRepo.findAll(pageable);
		List<UserDTO> usersDTO = new ArrayList<>();
		if(null != allUsers) {
			long totalElements = allUsers.getTotalElements();
			users.setTotalRecords(totalElements);
			List<User> usersList = allUsers.toList();
			for(User user :usersList ) {
				UserDTO fromDomain = helper.fromDomain(user);
				usersDTO.add(fromDomain);
			}
			users.setUserRecords(usersDTO);
			
		}
		return users;
	}

}
