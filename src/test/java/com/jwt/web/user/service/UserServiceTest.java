package com.jwt.web.user.service;

import com.jwt.web.user.dto.UserDto;
import com.jwt.web.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("사용자 등록")
    public void createUser() {
        UserDto userDto = UserDto.builder()
                .username("user3")
                .password("1234")
                .build();

        userService.save(userDto);

        UserDto findByUserDto = userRepository.findByUsername(userDto.getUsername());

        assertThat(findByUserDto.getUsername()).isEqualTo(userDto.getUsername());
    }

    @Test
    @DisplayName("사용자 조회")
    public void userDetailsService() {
        UserDetails user = userDetailsService.loadUserByUsername("user");
        assertThat(user.getUsername()).isEqualTo("user");
    }

}