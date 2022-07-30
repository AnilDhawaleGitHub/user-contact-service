package com.user.contact.service.exceptions;

import com.user.contact.service.controller.UserContactServiceController;
import com.user.contact.service.service.UserContactService;
import com.user.contact.service.util.UserContactUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.NoSuchElementException;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomExceptionTest {

  @MockBean
  private UserContactService userContactService;

  @Autowired
  private UserContactServiceController controller;

  @DisplayName("test  case for custom exception : UserNotFoundException")
  @Test
  public void shouldThrowUserNotFoundExceptionTest() {

    when(userContactService.getUserContactInfoById(1L))
        .thenThrow(
            new UserNotFoundException(UserContactUtil.USER_CONTACT_NOT_FOUND_IN_DB + 1L));
    assertThatExceptionOfType(UserNotFoundException.class)
        .isThrownBy(() -> controller.getUserContactInfoById(1L))
        .withMessageContaining(UserContactUtil.USER_CONTACT_NOT_FOUND_IN_DB);
  }

  @DisplayName("test  case for custom exception : UserNotFoundException")
  @Test
  public void shouldThrowUserNotFoundExceptionForAllUsers() {

    when(userContactService.getAllUserContactInfo())
        .thenThrow(new UserNotFoundException(UserContactUtil.NO_USER_CONTACT_FOUND_IN_DB));
    assertThatExceptionOfType(UserNotFoundException.class)
        .isThrownBy(() -> controller.getAllUserContactInfo())
        .withMessageContaining(UserContactUtil.NO_USER_CONTACT_FOUND_IN_DB);
  }

  @DisplayName("test  case for custom exception : UserNotFoundException")
  @Test
  public void shouldThrowUNoSuchElementException() {
    when(userContactService.getUserContactInfoByIds("1,2,3"))
        .thenThrow(new NoSuchElementException(
            UserContactUtil.REQUESTED_USER_CONTACT_NOT_FOUND_IN_DB));
    assertThatExceptionOfType(NoSuchElementException.class)
        .isThrownBy(() -> controller.getUserContactInfoByIds("1,2,3"))
        .withMessageContaining(UserContactUtil.REQUESTED_USER_CONTACT_NOT_FOUND_IN_DB);
  }

  @DisplayName("test  case for custom exception : UserIdValidationException")
  @Test
  public void shouldThrowUserIdValidationException() {

    when(userContactService.getUserContactInfoByIds("a,b,c"))
        .thenThrow(
            new UserIdValidationException(UserContactUtil.VALID_ID_ENTER));

    assertThatExceptionOfType(UserIdValidationException.class)
        .isThrownBy(() -> controller.getUserContactInfoByIds("a,b,c"))
        .withMessageContaining(UserContactUtil.VALID_ID_ENTER);
  }

}
