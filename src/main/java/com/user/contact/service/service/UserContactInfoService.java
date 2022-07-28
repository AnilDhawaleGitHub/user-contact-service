package com.user.contact.service.service;

import com.user.contact.service.dto.UserContactDto;
import com.user.contact.service.entity.UserContact;
import java.util.List;

public interface UserContactInfoService {

  UserContact saveUserContactInfo(UserContactDto userContractInfoDto);

  UserContact getUserContactInfoById(long id);

  String deleteUserContactInfoById(long id);

  List<UserContact> getUserContactInfoByIds(String ids);

  List<UserContact> getAllUserContactInfo();

  UserContact updateUserContactInfoById(long id, UserContactDto userContactDto);
}
