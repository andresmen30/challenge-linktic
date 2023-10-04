package com.linktic.challengelinktic.business.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.linktic.challengelinktic.api.exception.AlreadyExistException;
import com.linktic.challengelinktic.api.exception.RecordNotFoundException;
import com.linktic.challengelinktic.business.dto.ProductDto;
import com.linktic.challengelinktic.business.mapper.ProductMapper;
import com.linktic.challengelinktic.infrastructure.entity.Product;
import com.linktic.challengelinktic.infrastructure.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

   @InjectMocks
   private ProductServiceImpl productService;

   @Mock
   private ProductRepository productRepository;

   @Mock
   private ProductMapper productMapper;

   private static final List<Product> GET_ALL_PRODUCTS_ENTITY = List.of(
         Product.builder().id(NumberUtils.LONG_ONE).name("eggs").description("food").price(NumberUtils.createBigDecimal("12.0")).build(),
         Product.builder().id(2L).name("rice").description("food").price(NumberUtils.createBigDecimal("20.0")).build());

   private static final List<ProductDto> GET_ALL_PRODUCTS_DTO = List.of(
         ProductDto.builder().name("eggs").description("food").price(NumberUtils.createBigDecimal("12.0")).build(),
         ProductDto.builder().name("rice").description("food").price(NumberUtils.createBigDecimal("20.0")).build());

   @Test
   @DisplayName("Junit test get products all")
   void getProducts() {
      given(productRepository.findAll()).willReturn(GET_ALL_PRODUCTS_ENTITY);
      given(productMapper.toDto(GET_ALL_PRODUCTS_ENTITY)).willReturn(GET_ALL_PRODUCTS_DTO);
      final List<ProductDto> products = productService.getProducts();
      assertThat(products).isNotNull();
      assertThat(products.size()).isEqualTo(NumberUtils.INTEGER_TWO);
   }

   @Test
   @DisplayName("Junit test get product by id")
   void productById() {
      final Product productEntity = GET_ALL_PRODUCTS_ENTITY.get(NumberUtils.INTEGER_ZERO);
      final ProductDto productDto = GET_ALL_PRODUCTS_DTO.get(NumberUtils.INTEGER_ZERO);
      given(productRepository.findById(NumberUtils.LONG_ONE)).willReturn(Optional.of(productEntity));
      given(productMapper.toDto(productEntity)).willReturn(productDto);
      final ProductDto product = productService.productById(NumberUtils.LONG_ONE);
      assertThat(product).isNotNull();
      assertThat(product.getPrice()).isNotNull();
      assertThat(product.getDescription()).isNotNull();
      assertThat(product.getName()).isNotNull();
   }

   @Test
   @DisplayName("Junit test get product by id not found record")
   void productByIdNotFoundRecord() {
      given(productRepository.findById(NumberUtils.LONG_ONE)).willReturn(Optional.empty());
      assertThrows(RecordNotFoundException.class, () -> productService.productById(NumberUtils.LONG_ONE));
   }

   @Test
   @DisplayName("Junit test save product")
   void save() {
      final Product productEntity = GET_ALL_PRODUCTS_ENTITY.get(NumberUtils.INTEGER_ZERO);
      final ProductDto productDto = GET_ALL_PRODUCTS_DTO.get(NumberUtils.INTEGER_ZERO);
      given(productMapper.toEntity(productDto)).willReturn(productEntity);
      given(productRepository.findProductByName(productEntity.getName())).willReturn(Optional.empty());
      given(productRepository.save(productEntity)).willReturn(productEntity);
      given(productMapper.toDto(productEntity)).willReturn(productDto);
      final ProductDto result = productService.save(null, productDto);
      assertThat(result).isNotNull();
      assertThat(result.getPrice()).isNotNull();
      assertThat(result.getDescription()).isNotNull();
      assertThat(result.getName()).isNotNull();

   }

   @Test
   @DisplayName("Junit test save product already exist name")
   void saveAlreadyExistName() {
      final Product productEntity = GET_ALL_PRODUCTS_ENTITY.get(NumberUtils.INTEGER_ZERO);
      final ProductDto productDto = GET_ALL_PRODUCTS_DTO.get(NumberUtils.INTEGER_ZERO);
      given(productMapper.toEntity(productDto)).willReturn(productEntity);
      given(productRepository.findProductByName(productEntity.getName())).willReturn(Optional.of(productEntity));
      assertThrows(AlreadyExistException.class, () -> productService.save(null, productDto));

   }

   @Test
   @DisplayName("Junit test delete product")
   void delete() {
      final Product productEntity = GET_ALL_PRODUCTS_ENTITY.get(NumberUtils.INTEGER_ZERO);
      given(productRepository.findById(anyLong())).willReturn(Optional.of(productEntity));
      productService.delete(anyLong());
      verify(productRepository, times(1)).delete(productEntity);
   }

   @Test
   @DisplayName("Junit test delete product record not found")
   void deleteRecordNotFound() {
      given(productRepository.findById(anyLong())).willReturn(Optional.empty());
      assertThrows(RecordNotFoundException.class, () -> productService.delete(anyLong()));
   }
}