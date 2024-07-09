package com.app.zware;


import com.app.zware.Controllers.AuthController;
import com.app.zware.Entities.User;
import com.app.zware.HttpEntities.CustomResponse;
import com.app.zware.Service.UserService;
import com.app.zware.Util.PasswordUtil;
import com.app.zware.Validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

	@InjectMocks
	AuthController authController;

	@Mock
	UserService userService;

	@Mock
	UserValidator userValidator;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoginSuccess() {
		User request = new User();
		request.setEmail("test@example.com");
		request.setPassword("password123");

		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword(PasswordUtil.hashPassword("password123"));

		when(userService.getByEmail("test@example.com")).thenReturn(user);

		ResponseEntity<?> response = authController.login(request);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		CustomResponse customResponse = (CustomResponse) response.getBody();
		assertEquals("Login success", customResponse.getMessage());
		assertEquals(true, customResponse.getSuccess());
	}

	@Test
	void testLoginInvalidEmail() {
		User request = new User();
		request.setEmail("invalid-email");
		request.setPassword("password123");

		ResponseEntity<?> response = authController.login(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		CustomResponse customResponse = (CustomResponse) response.getBody();
		assertEquals("Email or password is invalid", customResponse.getMessage());
		assertEquals(false, customResponse.getSuccess());
	}

	@Test
	void testLoginInvalidPassword() {
		User request = new User();
		request.setEmail("test@example.com");
		request.setPassword("short");

		ResponseEntity<?> response = authController.login(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		CustomResponse customResponse = (CustomResponse) response.getBody();
		assertEquals("Email or password is invalid", customResponse.getMessage());
		assertEquals(false, customResponse.getSuccess());
	}

	@Test
	void testLoginInvalidCredentials() {
		User request = new User();
		request.setEmail("test@example.com");
		request.setPassword("wrongpassword");

		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword(PasswordUtil.hashPassword("password123"));

		when(userService.getByEmail("test@example.com")).thenReturn(user);

		ResponseEntity<?> response = authController.login(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		CustomResponse customResponse = (CustomResponse) response.getBody();
		assertEquals("Invalid credentials", customResponse.getMessage());
		assertEquals(false, customResponse.getSuccess());
	}
}

