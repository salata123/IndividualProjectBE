package com.example.individualprojectbe.repository;

import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.domain.LoginToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginTokenRepository extends JpaRepository<LoginToken, Long> {
}
