package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.io.entity.UserEntity;
import com.navigatedb.ws.repository.UserRepository;
import com.navigatedb.ws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    final void testGetUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserId("812738123");
        userEntity.setUsername("Gaby");
        userEntity.setEncryptedPassword("78192392173b4uuid");

        when( userRepository.findByEmail( anyString() ) ).thenReturn( userEntity );

        UserDto userDto = userService.getUser("test@test.com");

        assertNotNull(userDto);
        assertEquals("Gaby", userDto.getUsername());

    }

    @Test
    final void testGetUser_UsernameNotFoundException() {

        when( userRepository.findByEmail( anyString() ) ).thenReturn( null );

        assertThrows(UsernameNotFoundException.class,

                ()-> {
                    userService.getUser("test@test.com");
                }

                );
    }

}