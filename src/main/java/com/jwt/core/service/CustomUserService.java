package com.jwt.core.service;

import com.jwt.domain.user.User;
import com.jwt.web.user.dto.UserDto;
import com.jwt.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("UserDetailsService")
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto userDto = userRepository.findByUsername(username);
        if (userDto == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        User user = mapper.map(userDto, User.class);

        return new UserContext(user, new ArrayList<>());
    }
}
