package com.user.contact.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "addressWith")
@Table(name = "Address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "door_no")
  private String doorNo;

  @Column(name = "street_name")
  private String streetName;

  @Column(name = "post_code")
  private String postCode;

}
