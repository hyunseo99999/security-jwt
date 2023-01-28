package com.jwt.core.service;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserContext extends User {

    private com.jwt.domain.user.User user;

    public UserContext(com.jwt.domain.user.User user, List<GrantedAuthority> roles) {
        super(user.getUsername(), user.getPassword(), roles);
        this.user = user;
    }

    public com.jwt.domain.user.User getUser() {
        return user;
    }
}
