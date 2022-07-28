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

  @Pattern(regexp = "^\\d{10}$", message = "invalid contact number, please enter 10 digit no only")
  private String contactNo;

  @Pattern(regexp = "^$|^[0-9X]{10}$", message = "invalid alternate contact number, please enter 10 digit no only")
  private String altContactNo;

  private AddressDto address;


}
