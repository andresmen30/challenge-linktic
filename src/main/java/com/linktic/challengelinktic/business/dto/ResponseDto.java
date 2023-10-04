package com.linktic.challengelinktic.business.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "time", "message", "data" })
@Schema(description = "Dto response generic")
public class ResponseDto {

   @Schema(description = "message name http status")
   private String message;

   @Schema(description = "time request")
   private LocalDateTime time;

   @Schema(description = "data generic")
   private Object data;

}
