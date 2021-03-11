package com.bank.account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
