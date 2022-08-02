package com.user.contact.service.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "userContactInfoDtoWith")
public class UserContactDto implements Serializable {

  @NotEmpty(message = "first name should Not be empty")
  public String firstName;

  @NotEmpty(message = "last name should Not be empty")
  public String lastName;

  @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "invalid contact number, please enter valid contact number e.g : +919960627712")
  private String contactNo;

  @Pattern(regexp = "^$|^\\+(?:[0-9] ?){6,14}[0-9]$", message = "invalid alternate contact number, please enter valid alternate contact number e.g : +919960627712 or leave it blank")
  private String altContactNo;

  private AddressDto address;


}
