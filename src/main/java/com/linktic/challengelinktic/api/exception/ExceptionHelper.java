package com.linktic.challengelinktic.api.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.linktic.challengelinktic.business.dto.ResponseDto;
import com.linktic.challengelinktic.util.ResponseUtil;

@RestControllerAdvice
public class ExceptionHelper {

   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected @ResponseBody ResponseDto handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
      final List<String> details = new ArrayList<>();
      ex.getBindingResult().getAllErrors().forEach((error) -> {
         final String detail = ((FieldError) error).getField().concat(" : ").concat(Objects.requireNonNull(error.getDefaultMessage()));
         details.add(detail);
      });
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, details);
   }

   @ExceptionHandler(MethodArgumentTypeMismatchException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected @ResponseBody ResponseDto handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex) {
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, ex.getMessage());
   }

   @ExceptionHandler(ConstraintViolationException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected @ResponseBody ResponseDto handleConstraintViolationException(final ConstraintViolationException ex) {
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, ex.getMessage());

   }

   @ExceptionHandler(MissingServletRequestParameterException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected @ResponseBody ResponseDto handleMissingServletRequestParameter(final MissingServletRequestParameterException ex) {
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, ex.getMessage());
   }

   @ExceptionHandler(AlreadyExistException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected @ResponseBody ResponseDto alreadyExistException(final AlreadyExistException ex) {
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, ex.getMessage());
   }

   @ExceptionHandler(RecordNotFoundException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected @ResponseBody ResponseDto recordNotFound(final RecordNotFoundException ex) {
      return ResponseUtil.response(HttpStatus.BAD_REQUEST, ex.getMessage());
   }

   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   protected @ResponseBody ResponseDto handleException(final Exception ex) {
      return ResponseUtil.response(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
   }

}
