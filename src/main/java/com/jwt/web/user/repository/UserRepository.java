package com.jwt.web.user.repository;

import com.jwt.domain.user.User;
import com.jwt.web.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public UserDto findByUsername(String username);
}
