package com.user.contact.service.controller;

import com.user.contact.service.dto.UserContactDto;
import com.user.contact.service.entity.UserContact;
import com.user.contact.service.service.UserContactInfoService;
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
public class UserContactInfoController {

  private static final Logger logger = LoggerFactory.getLogger(UserContactInfoController.class);

  @Autowired
  private UserContactInfoService userContactInfoService;


  @PostMapping()
  public ResponseEntity saveUserContractInfo(
      @Valid @RequestBody UserContactDto userContractInfoDto) {
    logger.info("user contact info received : {}", userContractInfoDto);
    return new ResponseEntity(userContactInfoService.saveUserContactInfo(userContractInfoDto),
        HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}")
  public UserContact getUserContactInfoById(@PathVariable long id) {
    logger.info("Fetching user contact info for Id : {}", id);
    return userContactInfoService.getUserContactInfoById(id);
  }

  // It should be @GetMapping but as we already same @GetMapping and which is also
  // taking String so its throwing amibiguty issue so changed it tompost
  @PostMapping(value = "/{ids}")
  public List<UserContact> getUserContactInfoByIds(@RequestParam("ids") String ids) {
    logger.info("user contact ids received : {}", ids);
    return userContactInfoService.getUserContactInfoByIds(ids);
  }

  @GetMapping()
  public ResponseEntity getAllUserContactInfo() {
    logger.info("Fetching all user contact info from database");
    return new ResponseEntity(userContactInfoService.getAllUserContactInfo(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateUserContactInfoById(
      @Valid @RequestBody UserContactDto userContactDto, @PathVariable long id) {
    logger.info("updating user contact info for Id : {}", id);
    return new ResponseEntity(userContactInfoService.updateUserContactInfoById(id, userContactDto),
        HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteUserContactInfoById(@PathVariable long id) {
    logger.info("Deleting user contact info for Id : {}", id);
    return new ResponseEntity(userContactInfoService.deleteUserContactInfoById(id), HttpStatus.OK);
  }

}
