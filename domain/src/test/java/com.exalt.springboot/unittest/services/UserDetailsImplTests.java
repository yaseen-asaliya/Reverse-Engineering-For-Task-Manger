package com.exalt.springboot.unittest.services;

import com.exalt.springboot.domain.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootConfiguration
@ContextConfiguration
public class UserDetailsImplTests {
    UserDetailsImpl userDetails = Mockito.mock(UserDetailsImpl.class);

    @Test
    void callGetters() {
        userDetails.getUsername();
        userDetails.getId();
        userDetails.getEmail();
        userDetails.getName();
        userDetails.getAuthorities();
        userDetails.getPassword();
        userDetails.setName("test");
    }

    @Test
    void unimplementedMethods() {
        userDetails.getAuthorities();
        userDetails.isAccountNonExpired();
        userDetails.isAccountNonLocked();
        userDetails.isCredentialsNonExpired();
        userDetails.isEnabled();
        userDetails.equals(null);
    }
}
