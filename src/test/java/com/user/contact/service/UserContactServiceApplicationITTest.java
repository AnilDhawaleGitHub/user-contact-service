package com.user.contact.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.contact.service.controller.UserContactServiceController;
import com.user.contact.service.dto.UserContactDto;
import com.user.contact.service.exceptions.UserNotFoundException;
import java.util.NoSuchElementException;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.user.contact.service.util.UserContactUtil.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserContactServiceApplicationITTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  UserContactDto userContactDto = null;

  @BeforeEach
  public void setup() {
    userContactDto = buildUserInfoDto();
  }

  @Autowired
  UserContactServiceController userContactServiceController;


  @Test
  void contextLoads() {
  }

  @Test
  public void checkUserContactInfoSaveTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.
        post("/v1/contacts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userContactDto)))
        .andExpect(status().isCreated());
  }


  @Test
  public void checkUserContactInfoSave1Test() throws Exception {

    userContactDto = buildUserInfoDto();
    userContactDto.setContactNo("1234512345");

    mvc.perform(MockMvcRequestBuilders.
        post("/v1/contacts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userContactDto)))
        .andExpect(status().isCreated());
  }

  @Test
  public void checkUserContactInfoSave2Test() throws Exception {

    userContactDto = buildUserInfoDto();
    userContactDto.setFirstName("New FirstName");

    mvc.perform(MockMvcRequestBuilders.
        put("/v1/contacts/{id}", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userContactDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void checkUserContactInfoSaveConflictTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.
        post("/v1/contacts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userContactDto)))
        .andExpect(status().isConflict());
  }


  @Test
  public void checkUserContactInfoAllTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/v1/contacts"))
        .andExpect(status().isOk());
  }

  @Test
  public void ischeckUserContactInfoDeleteByIdTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.delete("/v1/contacts/1"))
        .andExpect(status().isOk());

  }


  @DisplayName("test to save empty object in DB")
  @Test
  public void shouldNotAllowToSaveTest() {

    Exception exception = assertThrows(
        ConstraintViolationException.class,
        () -> saveUserContractInfo(new UserContactDto()));

    assertTrue(exception.getMessage().contains("Validation failed"));
  }

  void saveUserContractInfo(UserContactDto userContactDto) {
    userContactServiceController.saveUserContractInfo(userContactDto);
  }


  @DisplayName("test to save User Contact with in valid contact no")
  @Test
  public void shouldNotAllowToSaveAsInValidContactNoTest() {

    UserContactDto userContactDto = buildUserInfoDto();
    userContactDto.setContactNo("abcd");

    Exception exception = assertThrows(
        ConstraintViolationException.class,
        () -> saveUserContractInfo(userContactDto));
    assertTrue(exception.getMessage().contains(INVALID_CONTACT_NO));
  }


  @DisplayName("test to fetch the User contact which is not present DB")
  @Test
  public void shouldFailedToFetchTest() {

    Exception exception = assertThrows(
        UserNotFoundException.class,
        () -> getUserContactInfoById(100L));

    assertTrue(exception.getMessage().contains(USER_CONTACT_NOT_FOUND_IN_DB));
  }

  void getUserContactInfoById(Long id) {
    userContactServiceController.getUserContactInfoById(100L);
  }


  @DisplayName("test to fetch list of User contact which is not present DB")
  @Test
  public void shouldFailedFetchForMultipleIdsTest() {

    Exception exception = assertThrows(
        NoSuchElementException.class,
        () -> getUserContactInfoByIds("10,11,12"));

    assertTrue(exception.getMessage().contains(REQUESTED_USER_CONTACT_NOT_FOUND_IN_DB));
  }

  void getUserContactInfoByIds(String ids) {
    userContactServiceController.getUserContactInfoByIds(ids);
  }


  @DisplayName("test to update the User contact which is not present DB")
  @Test
  public void shouldNotAllowToUpdateTest() {

    Exception exception = assertThrows(
        UserNotFoundException.class,
        () -> updateUserContactInfoById(buildUserInfoDto(), 100L));

    System.out.println("Test");
    assertTrue(exception.getMessage().contains(USER_CONTACT_NOT_FOUND_IN_DB));
  }

  void updateUserContactInfoById(UserContactDto userContactDto, Long id) {
    userContactServiceController.updateUserContactInfoById(userContactDto, id);
  }


  @DisplayName("test to delete the User contact which is not present DB")
  @Test
  public void shouldNotDeleteTest() {

    Exception exception = assertThrows(
        UserNotFoundException.class,
        () -> deleteUserContactInfoById(100));

    assertTrue(exception.getMessage().contains(USER_CONTACT_NOT_FOUND_IN_DB));
  }

  void deleteUserContactInfoById(long id) {
    userContactServiceController.deleteUserContactInfoById(id);
  }

}
