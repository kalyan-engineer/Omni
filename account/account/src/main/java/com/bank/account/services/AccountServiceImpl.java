package com.bank.account.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account.DTO.AccountDTO;
import com.bank.account.DTO.AllAccounts;
import com.bank.account.DTO.Pagination;
import com.bank.account.Delegate.AccountDelegate;
import com.bank.account.Handler.Helper;
import com.bank.account.configuration.BankUserDetailsService;
import com.bank.account.model.Account;
import com.bank.account.model.AuthRequest;
import com.bank.account.model.Authresponse;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.utils.TokenUtils;


@RestController
@RequestMapping("/account")
public class AccountServiceImpl {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private BankUserDetailsService bankUserDetailsService;
	
	@Autowired
	TokenUtils tokenUtil;
	
	@Autowired
	AccountDelegate accountDelegate;
	
	@Autowired
	AccountRepository accountrepo;
	
	@Autowired
	Helper helper;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody AuthRequest authrequest) throws Exception {
		try {
		Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new Exception("Wrong credentils", e);
		}
		//after succesful authentication , we will generate JWT
		final UserDetails userDetails = bankUserDetailsService
				.loadUserByUsername(authrequest.getUsername());

		final String jwt = tokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new Authresponse(jwt));
		
	}

	/*
	 * @PreAuthorize("hasRole('MANAGER')")
	 * 
	 * @RequestMapping(value="/user/register", method = RequestMethod.POST) public
	 * UserDTO saveUser(@RequestBody UserDTO user){ return
	 * accountDelegate.save(user); }
	 */
	
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value="/save/account", method = RequestMethod.POST)
    public AccountDTO createAccount(@RequestBody AccountDTO account, @RequestHeader (name="Authorization") String token){
		AccountDTO accountDTO = accountDelegate.save(account,token);
		if(null == accountDTO) {
			account.setSaveStatus("account non existing");
		}else {
			account.setSaveStatus("Success");
		}
		return accountDTO;
    }
	
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public AccountDTO getAccountById(@PathVariable("id") String id) {
		AccountDTO userDTO = accountDelegate.getAccountById(id);
		return userDTO;
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@RequestMapping(value = "/get/all/", method = RequestMethod.POST)
	public List<AccountDTO> getAllAccounts(@RequestBody AllAccounts accounts) {
		Pagination page = accounts.getPage();
		Pageable pageable = null;
		if(null == page) {
			pageable = PageRequest.of(0, 10);
		}else {
			int pageNum = page.getPage();
			int pageSize = page.getSize();
			pageable = PageRequest.of(pageNum, pageSize);
		}
		Page<Account> allAccounts = accountrepo.findAll(pageable);
		List<Account> accountlist = allAccounts.toList();
		List<AccountDTO> accountDTOs = new ArrayList<>();
		for(Account acct:accountlist) {
			AccountDTO fromDomain = helper.fromDomain(acct);
			accountDTOs.add(fromDomain);
		}
		return accountDTOs;
	}
	
	
	
	
	/*
	 * @PreAuthorize("hasRole('MANAGER')")
	 * 
	 * @RequestMapping(value="/delete/account", method = RequestMethod.POST) public
	 * AccountDTO updateAccount(@RequestBody AccountDTO account){
	 * 
	 * return accountDelegate.save(account); }
	 */
	
}
