package com.bank.account.Delegate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.bank.account.DTO.AccountDTO;
import com.bank.account.DTO.UserDTO;
import com.bank.account.Handler.Helper;
import com.bank.account.configuration.BankUserDetailsService;
import com.bank.account.model.Account;
import com.bank.account.model.User;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.UserRepository;

import reactor.core.publisher.Mono;

@Component
public class AccountDelegate {

	@Autowired
	BankUserDetailsService bankUserDetailsService;

	@Autowired
	Helper helper;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	WebClient.Builder webClientBuilder;

	public UserDTO save(UserDTO userDTO) {
		User save = bankUserDetailsService.save(userDTO);
		return userDTO;
	}


	public AccountDTO save(AccountDTO accountDTO,String token) {
		UserDTO userDTO = accountDTO.getUserDTO();
		if(null == userDTO) {
			return null;
		}
		String userid = userDTO.getUserid();
		User user = null;
		if(null != userid && !userid.isEmpty()) {
			user = userRepository.findByuseridAndStatus(userid,"Active");
		}
		if(null ==user) {
			//create new user
			UserDTO createduser = createUser(userDTO,token);
			Optional<User> findById = userRepository.findById(createduser.getUserid());
			user = findById.get();
			accountDTO.setUserid(userDTO.getUserid());
		}
		Account account = helper.FromDto(accountDTO);
		account.setUser(user);
		if(null != account) {
			account = accountRepository.save(account);
		}
		accountDTO.setAccountid(account.getAccountid());
		return accountDTO;
	}

	private UserDTO createUser(UserDTO user, String token) {
		StringBuilder bearer = new StringBuilder();
		//bearer.append("bearer ");
		bearer.append(token);
		//WebClient webClient = WebClient.create("http://USERSERVICE");
		UserDTO userDTO = webClientBuilder.build().post()
				.uri("http://USERSERVICE/user/register").contentType(MediaType.APPLICATION_JSON)
				//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", bearer.toString())
				.body(Mono.just(user), UserDTO.class)
				.retrieve()
				.bodyToMono(UserDTO.class)
				.block();

		return userDTO;
	}


	public AccountDTO getAccountById(String id) {
		Optional<Account> accountOptional = accountRepository.findById(id);
		Account account = accountOptional.get();
		AccountDTO fromDomain = helper.fromDomain(account);
		return fromDomain;
	}

	/*
	 * public com.bank.account.model.User save(UserDTO userDTO) {
	 * com.bank.account.model.User nUser = helper.FromDto(userDTO);
	 * nUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
	 * nUser.setRoleid(userDTO.getRole()); com.bank.account.model.User user =
	 * userRepo.save(nUser); return user; }
	 */
}
