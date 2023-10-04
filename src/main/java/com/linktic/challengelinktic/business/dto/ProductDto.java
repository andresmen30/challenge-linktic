package com.linktic.challengelinktic.business.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@Schema(description = "Dto model Product")
public class ProductDto {

   @Schema(description = "id product")
   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   private Long id;

   @Schema(description = "name product")
   @NotBlank
   private String name;

   @Schema(description = "description product")
   @NotBlank
   private String description;

   @Schema(description = "price product")
   @NotNull
   @DecimalMin(value = "0.01")
   private BigDecimal price;

}
