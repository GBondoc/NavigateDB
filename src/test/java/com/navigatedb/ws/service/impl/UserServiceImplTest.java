package com.navigatedb.ws.service.impl;

import com.navigatedb.ws.io.entity.ErdEntity;
import com.navigatedb.ws.io.entity.UserEntity;
import com.navigatedb.ws.repository.UserRepository;
import com.navigatedb.ws.shared.Utils;
import com.navigatedb.ws.shared.dto.ErdDto;
import com.navigatedb.ws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    String userId = "812738123";
    String encPassword = "82713hd89123";

    UserEntity userEntity;

    UserDto userDto;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserId(userId);
        userEntity.setUsername("Gaby");
        userEntity.setEncryptedPassword(encPassword);
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationToken("7812389iqywdiu");
        userEntity.setErds(getErdsEntity());

        userDto = new UserDto();
        userDto.setUsername("0821h3d1");
        userDto.setEmail("y81298@test.com");
        userDto.setPassword("1234");
        userDto.setErds(getErdsDto());
    }

    @Test
    final void testGetUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("test@test.com");

        assertNotNull(userDto);
        assertEquals("Gaby", userDto.getUsername());

    }

    @Test
    final void testGetUser_UsernameNotFoundException() {

        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,

                ()-> {
                    userService.getUser("test@test.com");
                }

                );
    }

    @Test
    final void testCreateUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateErdId(anyInt())).thenReturn("aiusd98uhio23q1h");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto storedUserDetails = userService.createUser(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getUsername(), storedUserDetails.getUsername());
        assertEquals(userEntity.getEmail(), storedUserDetails.getEmail());
        assertNotNull(storedUserDetails.getUserId());
        assertEquals(storedUserDetails.getErds().size(), userEntity.getErds().size());

        verify(utils, times(storedUserDetails.getErds().size())).generateErdId(30);
        verify(bCryptPasswordEncoder, times(1)).encode("1234");
        verify(utils, times(1)).generateUserId(30);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
/*
      if (userRepository.findByEmail(user.getEmail()) != null)
              throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        if (userRepository.findByUsername(user.getUsername()) != null)
                throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

*/

    private List<ErdDto> getErdsDto() {
        ErdDto erdDto = new ErdDto();
        erdDto.setName("db");

        ErdDto erdDto1 = new ErdDto();
        erdDto1.setName("db2");

        List<ErdDto> erds = new ArrayList<>();
        erds.add(erdDto);
        erds.add(erdDto1);

        return erds;
    }

    private List<ErdEntity> getErdsEntity() {
        List<ErdDto> erds = getErdsDto();

        Type listType = new TypeToken<List<ErdEntity>>() {}.getType();

        return new ModelMapper().map(erds, listType);

    }

}