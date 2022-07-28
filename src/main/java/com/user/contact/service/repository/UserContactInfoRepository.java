package com.user.contact.service.repository;

import com.user.contact.service.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactInfoRepository extends JpaRepository<UserContact, Long> {


}
