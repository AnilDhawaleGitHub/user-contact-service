package com.user.contact.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.contact.service.dto.UserContactDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.user.contact.service.util.UserContactInfoUtil.*;

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
        put("/v1/contacts/{id}","1")
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


}
