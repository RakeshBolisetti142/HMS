package com.team5.secure.repository;
import org.springframework.data.repository.CrudRepository;

import com.team5.beans.Users;
public interface UserRepository extends CrudRepository<Users, Integer> {
    Users findByEmail(String username);
}