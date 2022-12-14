package com.user.contact.service.service;

import static com.user.contact.service.util.UserContactUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.user.contact.service.dto.UserContactDto;
import com.user.contact.service.entity.UserContact;
import com.user.contact.service.exceptions.UserNotFoundException;
import com.user.contact.service.repository.UserContactServiceRepository;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class UserContactServiceTest {

  @InjectMocks
  UserContactService userContactService = new UserContactServiceImpl();

  @Mock
  UserContactServiceRepository userContactServiceRepository;

  List<UserContact> listOfUserContact = null;
  UserContact userContact = null;
  UserContactDto userContactDto = null;
  UserContact updatedUserContact = null;
  UserContactDto userContactDtoForSave = null;


  @BeforeEach
  public void setup() {
    listOfUserContact = buildUserContactInfo();
    userContact = buildUserContactInfoForFindById();
    userContactDto = buildUserInfoDto();
    updatedUserContact = buildReceivedUserContactInfoForFindById();
    userContactDtoForSave = buildUserInfoDtoForSave();

  }

  @DisplayName("get all user contact test case")
  @Test
  public void shouldReturnAllUserContactInfoTest() {
    when(userContactServiceRepository.findAll()).thenReturn(this.listOfUserContact);
    List<UserContact> listOfUserContact = userContactService.getAllUserContactInfo();
    assertNotNull(listOfUserContact);
    assertThat(listOfUserContact.size()).isEqualTo(2);
  }


  @DisplayName("get user contact info by id test case")
  @Test
  public void shouldReturnUserContactInfoByIdTest() {
    when(userContactServiceRepository.findById(1L))
        .thenReturn(java.util.Optional.ofNullable(userContact));
    UserContact userContact = userContactService.getUserContactInfoById(1L);
    assertNotNull(userContact);
    assertThat(userContact.getFirstName()).isEqualTo("FirstName");
    assertThat(userContact.getContactNo()).isEqualTo("+911234567890");
    assertThat(userContact.getAddress().getPostCode()).isEqualTo("XXZ123");

  }


  @DisplayName("get user contact by ids (1,2,3 etc) test case")
  @Test
  public void shouldReturnUserDetailsByIdsTest() {
    when(userContactServiceRepository.findAllById(Arrays.asList(1L, 2L)))
        .thenReturn(this.listOfUserContact);
    List<UserContact> listOfUserContact = userContactService.getUserContactInfoByIds("1,2");
    assertNotNull(listOfUserContact);
    assertThat(listOfUserContact.size()).isEqualTo(2);
  }


  @DisplayName("delete user contact by ids test case")
  @Test
  public void shouldDeleteUserContactInfoByIdTest() {
    when(userContactServiceRepository.findById(1L))
        .thenReturn(java.util.Optional.ofNullable(userContact));

    doNothing().when(userContactServiceRepository).deleteById(1L);
    String deleteMsg = userContactService.deleteUserContactInfoById(1L);
    assertNotNull(deleteMsg);
  }


  @DisplayName("save user contact test case")
  @Test
  public void shouldSaveUserContactInfoTest() {
    when(userContactServiceRepository.save(this.userContact)).thenReturn(this.userContact);
    UserContact userContact = userContactService
        .saveUserContactInfo(userContactDtoForSave);
    assertNotNull(userContact);
  }


  @DisplayName("update user contact by id test case")
  @Test
  public void shouldUpdateUserContactInfoByIdTest() {
    when(userContactServiceRepository.findById(1L))
        .thenReturn(java.util.Optional.ofNullable(this.userContact));
    when(userContactServiceRepository.save(this.userContact)).thenReturn(
        this.userContact);
    UserContact userContact = userContactService
        .updateUserContactInfoById(1L, userContactDto);
    Assert.assertNotNull(userContact);
    assertThat(userContact.getFirstName()).isEqualTo("UpdatedFirstName");
  }


  @DisplayName("test to fetch AllUserContactInfo if there is no data in DB")
  @Test
  public void shouldThrowUserNotFoundExceptionTest() {

    Exception exception = assertThrows(
        UserNotFoundException.class,
        () -> callAllUserContactInfo());
    assertTrue(exception.getMessage().contains(NO_USER_CONTACT_FOUND_IN_DB));
  }

  void callAllUserContactInfo() {
    userContactService.getAllUserContactInfo();
  }


}

