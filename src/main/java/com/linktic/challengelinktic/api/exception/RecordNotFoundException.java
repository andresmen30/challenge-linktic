package com.linktic.challengelinktic.api.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RecordNotFoundException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   @Builder.Default
   private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

   @Builder.Default
   private String message = "the [%s]: %s not found";

   public RecordNotFoundException(final String field, final Object value) {
      this.message = String.format(this.message, field, value);
   }

}
