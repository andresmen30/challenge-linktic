package com.linktic.challengelinktic.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.linktic.challengelinktic.business.dto.ProductDto;
import com.linktic.challengelinktic.business.service.ProductService;
import com.linktic.challengelinktic.util.ConstantUtil;
import com.linktic.challengelinktic.util.ResponseUtil;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private ProductService productService;

   private static final List<ProductDto> GET_ALL_PRODUCTS_DTO = List.of(
         ProductDto.builder().name("eggs").description("food").price(NumberUtils.createBigDecimal("12.0")).build(),
         ProductDto.builder().name("rice").description("food").price(NumberUtils.createBigDecimal("20.0")).build());

   @Test
   @DisplayName("Junit test operation all products")
   void testGetAllOk() throws Exception {
      final List<ProductDto> listAll = GET_ALL_PRODUCTS_DTO;
      when(productService.getProducts()).thenReturn(listAll);
      this.mockMvc
            .perform(get(ConstantUtil.CONTEXT_MAPPING + ConstantUtil.ENDPOINT_PRODUCT).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.time", Matchers.notNullValue()))
            .andExpect(jsonPath("$.message", is(HttpStatus.OK.name())))
            .andExpect(jsonPath("$.data", Matchers.notNullValue()))
            .andExpect(jsonPath("$.data.size()", is(listAll.size())));
   }

   @Test
   @DisplayName("Junit test operation product by id")
   void testGetProductByIdOk() throws Exception {
      final ProductDto productDto = GET_ALL_PRODUCTS_DTO.get(0);
      when(productService.productById(NumberUtils.LONG_ONE)).thenReturn(productDto);
      this.mockMvc
            .perform(get(ConstantUtil.CONTEXT_MAPPING + ConstantUtil.ENDPOINT_PRODUCT_VARIABLE.replace("{id}",
                  String.valueOf(NumberUtils.INTEGER_ONE))).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.time", Matchers.notNullValue()))
            .andExpect(jsonPath("$.message", is(HttpStatus.OK.name())))
            .andExpect(jsonPath("$.data", Matchers.notNullValue()));
   }

   @Test
   @DisplayName("Junit test operation save product")
   void testPostOk() throws Exception {
      final ProductDto.ProductDtoBuilder saveOk = ProductDto.builder().name("milk").description("food").price(NumberUtils.createBigDecimal("19.0"));
      when(productService.save(null, saveOk.build())).thenReturn(saveOk.id(NumberUtils.LONG_ONE).build());
      this.mockMvc
            .perform(post(ConstantUtil.CONTEXT_MAPPING + ConstantUtil.ENDPOINT_PRODUCT)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(ResponseUtil.objectToJsonString(saveOk.build())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.time", Matchers.notNullValue()))
            .andExpect(jsonPath("$.message", is(HttpStatus.CREATED.name())))
            .andExpect(jsonPath("$.data", Matchers.notNullValue()));
   }

   @Test
   @DisplayName("Junit test operation save product validation not null fields")
   void testValidationNotNull() throws Exception {
      final ProductDto.ProductDtoBuilder saveNotNull = ProductDto.builder().price(NumberUtils.createBigDecimal("22.0"));
      this.mockMvc
            .perform(post(ConstantUtil.CONTEXT_MAPPING + ConstantUtil.ENDPOINT_PRODUCT)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(ResponseUtil.objectToJsonString(saveNotNull.build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.time", Matchers.notNullValue()))
            .andExpect(jsonPath("$.message", is(HttpStatus.BAD_REQUEST.name())))
            .andExpect(jsonPath("$.data", Matchers.notNullValue()));
   }

   @Test
   @DisplayName("Junit test operation save product validation price > 0.0")
   void testValidationMinValuePrice() throws Exception {
      final ProductDto.ProductDtoBuilder saveMinValuePrice = ProductDto.builder().price(NumberUtils.createBigDecimal("00.0"));
      this.mockMvc
            .perform(post(ConstantUtil.CONTEXT_MAPPING + ConstantUtil.ENDPOINT_PRODUCT)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(ResponseUtil.objectToJsonString(saveMinValuePrice.build())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.time", Matchers.notNullValue()))
            .andExpect(jsonPath("$.message", is(HttpStatus.BAD_REQUEST.name())))
            .andExpect(jsonPath("$.data", Matchers.notNullValue()));
   }

   @Test
   @DisplayName("Junit test operation update product")
   void testPutOk() throws Exception {
      final ProductDto savePutOk = ProductDto.builder().name("potatoes").description("food").price(NumberUtils.createBigDecimal("12.0")).build();
      when(productService.save(NumberUtils.LONG_ONE, savePutOk)).thenReturn(savePutOk);
      this.mockMvc
            .perform(
                  put(ConstantUtil.CONTEXT_MAPPING + ConstantUtil.ENDPOINT_PRODUCT_VARIABLE.replace("{id}", String.valueOf(NumberUtils.INTEGER_ONE)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ResponseUtil.objectToJsonString(savePutOk)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.time", Matchers.notNullValue()))
            .andExpect(jsonPath("$.message", is(HttpStatus.OK.name())))
            .andExpect(jsonPath("$.data", Matchers.notNullValue()));
   }

   @Test
   @DisplayName("Junit test operation delete product")
   void testDeleteOk() throws Exception {
      this.mockMvc
            .perform(delete(ConstantUtil.CONTEXT_MAPPING + ConstantUtil.ENDPOINT_PRODUCT_VARIABLE.replace("{id}",
                  String.valueOf(NumberUtils.INTEGER_ONE))).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.time", Matchers.notNullValue()))
            .andExpect(jsonPath("$.message", is(HttpStatus.ACCEPTED.name())));
   }

}