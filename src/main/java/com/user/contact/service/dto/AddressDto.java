package com.user.contact.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "addressDtoBuilderWith")
public class AddressDto implements Serializable {

  public String doorNo;
  public String streetName;
  public String postCode;

}

