package com.exalt.springboot.unittest.services;

import com.exalt.springboot.domain.aggregate.User;
import com.exalt.springboot.repository.adapter.UserRepositoryAdapter;
import com.exalt.springboot.repository.entity.UserEntity;
import com.exalt.springboot.repository.jpa.IUserJpaRepository;
import com.exalt.springboot.service.implementation.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServicesTests {
	private IUserJpaRepository iUserJpaRepository = Mockito.mock(IUserJpaRepository.class);

	private ModelMapper modelMapper = mock(ModelMapper.class);
	private UserRepositoryAdapter userRepositoryAdapter
			= Mockito.spy(new UserRepositoryAdapter(iUserJpaRepository,modelMapper));

	private UserServiceImplementation iUserService = Mockito.spy(new UserServiceImplementation(userRepositoryAdapter));

	@Test
	void shouldGetCurrentUser() {
		UserEntity expectedUser = initializeUserEntity();

		when(iUserJpaRepository.findById(anyInt())).thenReturn(Optional.of(expectedUser));
		assertEquals(initializeUser().toString(),iUserService.findById(anyInt()).toString());
	}

	@Test
	void shouldGetUserNotFound() {
		boolean thrown = false;
		try {
			iUserService.findById(anyInt());
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	void shouldUpdateUser() {
		UserEntity updatedUser = initializeUserEntity();
		updatedUser.setEmail("yaseen.asaliya22@gmail.com"); // Do some changes...

		when(iUserJpaRepository.save(any())).thenReturn(updatedUser);
		assertEquals("User saved",iUserService.saveObject(any()));
	}

	@Test
	void shouldDeleteUser() {
		// Here we don't need to mock the "deleteById" cause it's a void method, so we just verify it.

		assertEquals("User deleted",iUserService.deleteById(anyInt()));
		verify(iUserJpaRepository).deleteById(anyInt()); //check that the method was called successfully
	}

	@Test
	public void shouldLoadUserByUsername() {
		Optional<User> expectedUser = Optional.of(initializeUser());

		when(iUserJpaRepository.findByUsername(anyString())).thenReturn(expectedUser);
		assertEquals(expectedUser.get().getUsername(), iUserService.loadUserByUsername(anyString()).getUsername());
	}

	private User initializeUser() {
		return new User(1,"yaseen","123","yaseens@gmail.com","ys",false);
	}

	private UserEntity initializeUserEntity() {
		UserEntity user = new UserEntity(1,"yaseen","123","yaseens@gmail.com","ys");
		user.setSignout(false);
		return user;
	}
}
