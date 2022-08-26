package com.exalt.springboot.unittest.services;

import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.domain.exception.NotFoundException;
import com.exalt.springboot.repository.entity.UserEntity;
import com.exalt.springboot.repository.jpa.IUserJpaRepository;
import com.exalt.springboot.service.implementation.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServicesTests {
	@Mock
	private IUserJpaRepository userRepository;

	@InjectMocks
	private UserServiceImplementation service;

	@Test
	void shouldGetCurrentUser() {
		UserEntity expectedUser = initializeUser();

		when(userRepository.findById(anyInt())).thenReturn(Optional.of(expectedUser));
		assertEquals(expectedUser,service.findById(anyInt()));
	}

	@Test
	void shouldGetUserNotFound() {
		boolean thrown = false;
		try {
			service.findById(anyInt());
		} catch (NotFoundException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	void shouldUpdateUser() {
		UserEntity updatedUser = initializeUser();
		updatedUser.setEmail("yaseen.asaliya22@gmail.com"); // Do some changes...

		when(userRepository.save(any())).thenReturn(updatedUser);
		assertEquals("User saved",service.saveObject(any()));
	}

	@Test
	void shouldDeleteUser() {
		// Here we don't need to mock the "deleteById" cause it's a void method, so we just verify it.

		assertEquals("User deleted",service.deleteById(anyInt()));
		verify(userRepository).deleteById(anyInt()); //check that the method was called successfully
	}

	@Test
	public void shouldLoadUserByUsername() {
		Optional<User> expectedUser = Optional.of(initializeDomainUser());

		when(userRepository.findByUsername(anyString())).thenReturn(expectedUser);
		assertEquals(expectedUser.get().getUsername(), service.loadUserByUsername(anyString()).getUsername());
	}

	private User initializeDomainUser() {
		return new User(1,"yaseen","123","yaseens@gmail.com","ys",false);
	}

	private UserEntity initializeUser() {
		UserEntity user = new UserEntity(1,"yaseen","123","yaseens@gmail.com","ys");
		user.setSignout(false);
		return user;
	}
}
