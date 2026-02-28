package com.gaadix.user.service;

import com.gaadix.common.dto.UserDTO;
import com.gaadix.common.entity.User;
import com.gaadix.common.enums.UserRole;
import com.gaadix.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@gaadix.com");
        user.setPhone("9876543210");
        user.setRoles(Set.of(UserRole.BUYER));
        user.setActive(true);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@gaadix.com");
        userDTO.setPhone("9876543210");
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("john@gaadix.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        verify(userRepository).findById(1L);
        verify(modelMapper).map(user, UserDTO.class);
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(999L));
        verify(userRepository).findById(999L);
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    void getUserById_NullId() {
        assertThrows(Exception.class, () -> userService.getUserById(null));
    }

    @Test
    void getUserByEmail_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.getUserByEmail("john@gaadix.com");

        assertNotNull(result);
        assertEquals("john@gaadix.com", result.getEmail());
        verify(userRepository).findByEmail("john@gaadix.com");
    }

    @Test
    void getUserByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserByEmail("notfound@gaadix.com"));
    }

    @Test
    void getUserByEmail_EmptyEmail() {
        assertThrows(Exception.class, () -> userService.getUserByEmail(""));
    }

    @Test
    void getAllUsers_Success() {
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("jane@gaadix.com");

        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setEmail("jane@gaadix.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(modelMapper.map(user2, UserDTO.class)).thenReturn(userDTO2);

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("john@gaadix.com", result.get(0).getEmail());
        assertEquals("jane@gaadix.com", result.get(1).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    void getAllUsers_EmptyList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void updateUser_Success() {
        UserDTO updateDTO = new UserDTO();
        updateDTO.setFirstName("Jane");
        updateDTO.setLastName("Smith");
        updateDTO.setPhone("9999999999");
        updateDTO.setDob(LocalDate.of(1990, 1, 1));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(updateDTO);

        UserDTO result = userService.updateUser(1L, updateDTO);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_NotFound() {
        UserDTO updateDTO = new UserDTO();
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(999L, updateDTO));
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_PartialUpdate() {
        UserDTO updateDTO = new UserDTO();
        updateDTO.setFirstName("Jane");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        UserDTO result = userService.updateUser(1L, updateDTO);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_NotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(999L));
        verify(userRepository).existsById(999L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteUser_NullId() {
        assertThrows(Exception.class, () -> userService.deleteUser(null));
    }
}
