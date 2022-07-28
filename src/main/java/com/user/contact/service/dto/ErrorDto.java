package com.user.contact.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "ErrorDto")
public class ErrorDto {

  public String status;
  public String message;
  public String time;
}
