package com.user.contact.service.exceptions;

import com.user.contact.service.controller.UserContactInfoController;
import com.user.contact.service.service.UserContactInfoService;
import com.user.contact.service.util.UserContactInfoUtil;
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
  private UserContactInfoService userContactInfoService;

  @Autowired
  private UserContactInfoController controller;

  //test  case for custom exception : UserNotFoundException
  @Test
  public void shouldThrowUserNotFoundExceptionTest() {

    when(userContactInfoService.getUserContactInfoById(1L))
        .thenThrow(
            new UserNotFoundException(UserContactInfoUtil.USER_CONTACT_INFO_NOT_FOUND_IN_DB + 1L));
    assertThatExceptionOfType(UserNotFoundException.class)
        .isThrownBy(() -> controller.getUserContactInfoById(1L))
        .withMessageContaining(UserContactInfoUtil.USER_CONTACT_INFO_NOT_FOUND_IN_DB);
  }

  //test  case for custom exception : UserNotFoundException
  @Test
  public void shouldThrowUserNotFoundExceptionForAllUsers() {

    when(userContactInfoService.getAllUserContactInfo())
        .thenThrow(new UserNotFoundException(UserContactInfoUtil.NO_USER_CONTACT_INFO_FOUND_IN_DB));
    assertThatExceptionOfType(UserNotFoundException.class)
        .isThrownBy(() -> controller.getAllUserContactInfo())
        .withMessageContaining(UserContactInfoUtil.NO_USER_CONTACT_INFO_FOUND_IN_DB);
  }

  //test  case for custom exception : UserNotFoundException
  @Test
  public void shouldThrowUNoSuchElementException() {
    when(userContactInfoService.getUserContactInfoByIds("1,2,3"))
        .thenThrow(new NoSuchElementException(
            UserContactInfoUtil.REQUESTED_USER_CONTACT_INFO_NOT_FOUND_IN_DB));
    assertThatExceptionOfType(NoSuchElementException.class)
        .isThrownBy(() -> controller.getUserContactInfoByIds("1,2,3"))
        .withMessageContaining(UserContactInfoUtil.REQUESTED_USER_CONTACT_INFO_NOT_FOUND_IN_DB);
  }

  //test  case for custom exception : UserIdValidationException
  @Test
  public void shouldThrowUserIdValidationException() {

    when(userContactInfoService.getUserContactInfoByIds("a,b,c"))
        .thenThrow(
            new UserIdValidationException(UserContactInfoUtil.VALID_ID_ENTER));

    assertThatExceptionOfType(UserIdValidationException.class)
        .isThrownBy(() -> controller.getUserContactInfoByIds("a,b,c"))
        .withMessageContaining(UserContactInfoUtil.VALID_ID_ENTER);
  }

}
