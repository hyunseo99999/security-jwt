package com.jwt.web.user.service;

import com.jwt.domain.user.User;
import com.jwt.web.user.dto.UserDto;
import com.jwt.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(UserDto userDto) {
        User user = mapper.map(userDto, User.class);

        UserDto findUserDto = userRepository.findByUsername(user.getUsername());
        if (findUserDto != null) {
            throw new DuplicateKeyException("중복된 사용자가 있습니다.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
