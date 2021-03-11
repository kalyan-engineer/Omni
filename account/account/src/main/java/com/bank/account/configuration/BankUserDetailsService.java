package com.bank.account.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bank.account.DTO.UserDTO;
import com.bank.account.Handler.Helper;
import com.bank.account.repositories.UserRepository;

@Component
public class BankUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	Helper helper;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.bank.account.model.User user = findUser(username);
		if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
		
	}

	 private Set<SimpleGrantedAuthority> getAuthority(com.bank.account.model.User user) {
	        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	       String roleid = user.getRoleid();
	       if(null != roleid) {
	    	   authorities.add(new SimpleGrantedAuthority("ROLE_" + roleid));
	    	   //authorities.add(new SimpleGrantedAuthority(roleid));
	       }
	        return authorities;
	    }
	 
    public com.bank.account.model.User findUser(String userid) {
        return userRepo.findByuserid(userid);
    }
	
    public com.bank.account.model.User save(UserDTO userDTO) {
        com.bank.account.model.User nUser = helper.FromDto(userDTO);
        nUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        nUser.setRoleid(userDTO.getRole());
        com.bank.account.model.User user = userRepo.save(nUser);
        return user;
    }
}
