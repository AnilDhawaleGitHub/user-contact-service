package com.user.contact.service.handler;

import com.user.contact.service.dto.ErrorDto;
import com.user.contact.service.exceptions.UserIdValidationException;
import com.user.contact.service.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserNotFoundException.class)

  public List<String> handleUserNotFoundException(UserNotFoundException ex) {
    logger.info("Handling UserNotFoundException ..");
    List<String> errors = new ArrayList<>();
    errors.add(ex.getMessage());
    return errors;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserIdValidationException.class)
  public ResponseEntity<ErrorDto> handleUserIdValidationException(UserIdValidationException ex) {
    logger.info("Handling UserIdValidationException ..");
    ErrorDto errorDto = new ErrorDto();
    errorDto.setMessage(ex.getMessage());
    errorDto.setStatus("404");
    errorDto.setTime(new Date().toString());
    return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorDto> handleNoSuchElementException(NoSuchElementException ex) {
    logger.info("Handling NoSuchElementException ..");
    ErrorDto errorDTO = new ErrorDto();
    errorDTO.setMessage(ex.getMessage());
    errorDTO.setStatus("404");
    errorDTO.setTime(new Date().toString());
    return new ResponseEntity<ErrorDto>(errorDTO, HttpStatus.NOT_FOUND);
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    logger.info("Handling MethodArgumentNotValidException ..");
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  @ResponseStatus(value = HttpStatus.CONFLICT)
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseBody
  public Map<String, String> handleDataIntegrityViolationException(HttpServletRequest req,
      DataIntegrityViolationException e) {
    logger.info("Handling DataIntegrityViolationException ..");
    Map<String, String> errors = new HashMap<>();
    errors.put("Duplicate phone no ", "Please enter unique phone no");
    return errors;
  }
}
