package com.linktic.challengelinktic.api.controller;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.linktic.challengelinktic.business.dto.ProductDto;
import com.linktic.challengelinktic.business.dto.ResponseDto;
import com.linktic.challengelinktic.business.service.ProductService;
import com.linktic.challengelinktic.util.ConstantUtil;
import com.linktic.challengelinktic.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

@Tag(name = "Product", description = "CRUD")
@RequestMapping(ConstantUtil.CONTEXT_MAPPING)
@RestController
@RequiredArgsConstructor
public class ProductController {

   private final ProductService productService;

   @GetMapping(ConstantUtil.ENDPOINT_PRODUCT)
   @ResponseStatus(HttpStatus.OK)
   @Operation(summary = "get all products", description = "gets all products in a list")
   private @ResponseBody ResponseDto getProducts() {
      return ResponseUtil.response(HttpStatus.OK, productService.getProducts());
   }

   @GetMapping(ConstantUtil.ENDPOINT_PRODUCT_VARIABLE)
   @ResponseStatus(HttpStatus.OK)
   @Operation(summary = "get product by id", description = "gets a single product filtered by id")
   private @ResponseBody ResponseDto productById(@Parameter(description = "key id product", required = true) @PathVariable final Long id) {
      return ResponseUtil.response(HttpStatus.OK, productService.productById(id));
   }

   @PostMapping(ConstantUtil.ENDPOINT_PRODUCT)
   @ResponseStatus(HttpStatus.CREATED)
   @Operation(summary = "create product", description = "create a new product")
   private @ResponseBody ResponseDto save(@Valid @RequestBody final ProductDto productDto) {
      return ResponseUtil.response(HttpStatus.CREATED, productService.save(null, productDto));
   }

   @PutMapping(ConstantUtil.ENDPOINT_PRODUCT_VARIABLE)
   @Operation(summary = "update product", description = "update a product")
   @ResponseStatus(HttpStatus.OK)
   private @ResponseBody ResponseDto update(@Parameter(description = "key id product", required = true) @PathVariable final Long id,
         @Valid @RequestBody final ProductDto productDto) {
      return ResponseUtil.response(HttpStatus.OK, productService.save(id, productDto));
   }

   @DeleteMapping(ConstantUtil.ENDPOINT_PRODUCT_VARIABLE)
   @Operation(summary = "delete product", description = "delete a product")
   @ResponseStatus(HttpStatus.ACCEPTED)
   private @ResponseBody ResponseDto delete(@Parameter(description = "key id product", required = true) @PathVariable final Long id) {
      productService.delete(id);
      return ResponseUtil.response(HttpStatus.ACCEPTED, null);
   }

}
