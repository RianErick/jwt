package com.example.crudwithdocker.repository;

import com.example.crudwithdocker.model.User;
import com.example.crudwithdocker.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {

    UserAuth findByUser(String username);
}
