package com.bank.account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account.model.Account;
import com.bank.account.model.Role;

public interface AccountRepository extends JpaRepository<Account, String> {

}
