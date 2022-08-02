package com.user.contact.service.controller;

import com.user.contact.service.dto.UserContactDto;
import com.user.contact.service.entity.UserContact;
import com.user.contact.service.service.UserContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = {"UserContactInfo"})
@SwaggerDefinition(tags = {
    @Tag(name = "UserContactInfoController", description = "User Contact Info REST API")})
@RequestMapping(value = "/v1/contacts")
public class UserContactServiceController {

  private static final Logger logger = LoggerFactory.getLogger(UserContactServiceController.class);

  @Autowired
  private UserContactService userContactService;


  @PostMapping()
  public ResponseEntity saveUserContractInfo(
      @Valid @RequestBody UserContactDto userContractInfoDto) {
    logger.info("user contact info received : {}", userContractInfoDto);
    return new ResponseEntity(userContactService.saveUserContactInfo(userContractInfoDto),
        HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}")
  public UserContact getUserContactInfoById(@PathVariable long id) {
    logger.info("Fetching user contact info for Id : {}", id);
    return userContactService.getUserContactInfoById(id);
  }

  // It should be @GetMapping but as we already have same @GetMapping and which is
  // taking String so its throwing ambiguity issue so changed it to Post
  @PostMapping(value = "/{ids}")
  public List<UserContact> getUserContactInfoByIds(@RequestBody String ids) {
    logger.info("user contact ids received : {}", ids);
    return userContactService.getUserContactInfoByIds(ids);
  }

  @GetMapping()
  public ResponseEntity getAllUserContactInfo() {
    logger.info("Fetching all user contact info from database");
    return new ResponseEntity(userContactService.getAllUserContactInfo(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateUserContactInfoById(
      @Valid @RequestBody UserContactDto userContactDto, @PathVariable long id) {
    logger.info("updating user contact info for Id : {}", id);
    return new ResponseEntity(userContactService.updateUserContactInfoById(id, userContactDto),
        HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteUserContactInfoById(@PathVariable long id) {
    logger.info("Deleting user contact info for Id : {}", id);
    return new ResponseEntity(userContactService.deleteUserContactInfoById(id), HttpStatus.OK);
  }

}
