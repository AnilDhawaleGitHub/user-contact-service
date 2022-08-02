package com.user.contact.service.service;

import com.user.contact.service.repository.UserContactServiceRepository;
import com.user.contact.service.dto.UserContactDto;
import com.user.contact.service.entity.UserContact;
import com.user.contact.service.exceptions.UserIdValidationException;
import com.user.contact.service.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserContactServiceImpl implements UserContactService {

  private static final Logger logger = LoggerFactory.getLogger(UserContactServiceImpl.class);

  private static final String USER_CONTACT_NOT_FOUND_IN_DB = "user contact not found in the database for Id : ";
  private static final String REQUESTED_USER_CONTACT_NOT_FOUND_IN_DB = "user contact for the requested id's not present in database : ";
  private static final String NO_USER_CONTACT_FOUND_IN_DB = "no user contact found in the database ";
  private static final String USER_CONTACT_DELETED = "user contact info has been deleted for Id : ";
  private static final String VALID_ID_ENTER = "please enter single Id only or comma separated id's eg: 1,2,3";

  @Autowired
  private UserContactServiceRepository userContactDetailsRepository;

  ModelMapper modelMapper = null;
  UserContact userContact = null;

  @Override
  public UserContact saveUserContactInfo(UserContactDto userContactDto) {
    modelMapper = new ModelMapper();
    userContact = modelMapper.map(userContactDto, UserContact.class);
    logger.debug("user contact  dto mapped to entity for persisting");
    return userContactDetailsRepository.save(userContact);
  }

  @Override
  public UserContact getUserContactInfoById(long id) {

    Optional<UserContact> userDetails = userContactDetailsRepository.findById(id);
    if (!userDetails.isEmpty()) {
      logger.debug("Fetched user contact for Id : {}", id);
      return userDetails.get();
    }
    logger.debug(USER_CONTACT_NOT_FOUND_IN_DB + id);
    throw new UserNotFoundException(USER_CONTACT_NOT_FOUND_IN_DB + id);
  }

  @Override
  public List<UserContact> getAllUserContactInfo() {
    List<UserContact> listOfUserContact = userContactDetailsRepository.findAll();
    if (!listOfUserContact.isEmpty()) {
      logger
          .debug("Fetched all user contact  from database size : {}", listOfUserContact.size());
      return listOfUserContact;
    }
    logger.info(NO_USER_CONTACT_FOUND_IN_DB);
    throw new UserNotFoundException(NO_USER_CONTACT_FOUND_IN_DB);
  }


  @Override
  public String deleteUserContactInfoById(long id) {

    UserContact userContractInfo = userContactDetailsRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(USER_CONTACT_NOT_FOUND_IN_DB + "" + id));
    userContactDetailsRepository.deleteById(id);
    logger.debug(USER_CONTACT_DELETED + id);
    return USER_CONTACT_DELETED + id;

  }


  @Override
  public List<UserContact> getUserContactInfoByIds(String ids) {
    Matcher matcher = Pattern.compile("^[0-9]+(,[0-9]+)*$").matcher(ids);
    if (!matcher.matches()) {
      throw new UserIdValidationException(VALID_ID_ENTER);
    }

    logger.debug("Expected pattern matched for the received id's");

    List<Long> listIds = Stream.of(ids.split(",")).map(Long::parseLong)
        .collect(Collectors.toList());

    logger.debug("Id's added into list to fetch the user contact info : {}", listIds);

    List<UserContact> listOfUserDetails = userContactDetailsRepository.findAllById(listIds);
    if (listOfUserDetails.isEmpty()) {
      logger.info(REQUESTED_USER_CONTACT_NOT_FOUND_IN_DB);
      throw new NoSuchElementException(REQUESTED_USER_CONTACT_NOT_FOUND_IN_DB + listIds);
    } else {
      logger.info("received user contact info for requested id's : {}", listIds);
      return listOfUserDetails;
    }

  }

  @Override
  public UserContact updateUserContactInfoById(long id, UserContactDto userContactDto) {

    Optional<UserContact> userContactInfo = userContactDetailsRepository.findById(id);
    if (!userContactInfo.isEmpty()) {
      UserContact userContactUpdate = userContactInfo.get();
      logger.info("user contact info is present in database for updating for Id : {}", id);
      return userContactDetailsRepository.save(updateUserContactInfo(userContactUpdate,
          userContactDto));
    } else {
      logger.info("USER_CONTACT_INFO_NOT_FOUND_IN_DB {}", id);
      throw new UserNotFoundException(USER_CONTACT_NOT_FOUND_IN_DB + id);
    }
  }


  private UserContact updateUserContactInfo(
      UserContact userContactUpdate,
      UserContactDto userContactDto) {

    userContactUpdate.setFirstName(userContactDto.getFirstName());
    userContactUpdate.setLastName(userContactDto.getLastName());
    userContactUpdate.setContactNo(userContactDto.getContactNo());
    userContactUpdate.setAltContactNo(userContactDto.getAltContactNo());
    userContactUpdate.getAddress().setDoorNo(userContactDto.getAddress().getDoorNo());
    userContactUpdate.getAddress()
        .setStreetName(userContactDto.getAddress().getStreetName());
    userContactUpdate.getAddress().setPostCode(userContactDto.getAddress().getPostCode());
    return userContactUpdate;

  }


}
