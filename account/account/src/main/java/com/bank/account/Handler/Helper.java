package com.bank.account.Handler;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.account.DTO.AccountDTO;
import com.bank.account.DTO.UserDTO;
import com.bank.account.model.Account;
import com.bank.account.model.User;
import com.bank.account.repositories.AccountRepository;

@Component
public class Helper {
	
	@Autowired
	AccountRepository accountRepository;

	public User FromDto(UserDTO userDTO){
        User user = new User();
        user.setUserid(userDTO.getUserid());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setRoleid(userDTO.getRole());        
        return user;
    } 
	
	public Account FromDto(AccountDTO accountDTO){
		String accountid = accountDTO.getAccountid();
		Account account =null;
		if(null != accountid && accountid.equalsIgnoreCase("")) {
			try { 
			account = accountRepository.getOne(accountid);
			}catch(EntityNotFoundException e) {
				/*
				 * account = new Account(); account.setAccountid(accountid);
				 * //account.setCreateddate(new DateTime());
				 */			
				return null;
			}
			 
		}else {
			account = new Account();
			account.setAccountid(UUID.randomUUID().toString());
			Date createdDate = new Date();
			account.setOpenedDate(createdDate);
			account.setAccounttype(accountDTO.getAccounttype());
	        account.setBranch(accountDTO.getBranch());
		}
        
		/*
		 * DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/mm/yyyy");
		 * DateTime parse = DateTime.parse(accountDTO.getDateofbirth(), formatter);
		 */

        //DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/mm/yyyy");
//DateTime parse = DateTime.parse(accountDTO.getDateofbirth(), formatter);
      //  account.setDateofbirth(parse);
        
      //  account.setMinor(isMinor(parse));
           
        return account;
    } 
	
	public AccountDTO fromDomain(Account account) {
		AccountDTO accountDto = new AccountDTO();
		accountDto.setAccountid(account.getAccountid());
		accountDto.setAccounttype(account.getAccounttype());
		accountDto.setBranch(account.getBranch());
		accountDto. setDateofbirth(account.getOpenedDate().toString());
		return accountDto;
	}
	
	/*
	 * Boolean isMinor(DateTime dob) { long dobMilliSeconds = dob.getMillis(); long
	 * currentMilliSeconds = new Date().getTime(); long currentAge =
	 * currentMilliSeconds-dobMilliSeconds; float age =
	 * currentAge/1000*60*60*24*30*12; System.out.println("age is::"+age);
	 * if(age>18) { return false; }else { return true; }
	 * 
	 * }
	 */
}
