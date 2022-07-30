package com.user.contact.service.repository;

import com.user.contact.service.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactServiceRepository extends JpaRepository<UserContact, Long> {


}
