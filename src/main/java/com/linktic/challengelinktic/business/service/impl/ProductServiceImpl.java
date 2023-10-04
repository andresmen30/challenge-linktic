package com.linktic.challengelinktic.business.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.linktic.challengelinktic.api.exception.AlreadyExistException;
import com.linktic.challengelinktic.api.exception.RecordNotFoundException;
import com.linktic.challengelinktic.business.dto.ProductDto;
import com.linktic.challengelinktic.business.mapper.ProductMapper;
import com.linktic.challengelinktic.business.service.ProductService;
import com.linktic.challengelinktic.infrastructure.entity.Product;
import com.linktic.challengelinktic.infrastructure.repository.ProductRepository;
import com.linktic.challengelinktic.util.ConstantUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

   private final ProductRepository productRepository;

   private final ProductMapper productMapper;

   @Override
   public List<ProductDto> getProducts() {
      return productMapper.toDto(productRepository.findAll());
   }

   @Override
   public ProductDto productById(Long id) {
      final Product product = productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ConstantUtil.FIELD_ID, id));
      return productMapper.toDto(product);
   }

   @Override
   public ProductDto save(final Long id, final ProductDto productDto) {
      productDto.setId(id);
      final Product product = productMapper.toEntity(productDto);
      if (productDto.getId() == null) {
         if (productRepository.findProductByName(productDto.getName()).isPresent()) {
            throw new AlreadyExistException(ConstantUtil.FIELD_NAME, productDto.getName());
         }
      } else {
         productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ConstantUtil.FIELD_ID, productDto.getId()));
         if (productRepository.findProductByNameWithoutId(productDto.getName(), id).isPresent()) {
            throw new AlreadyExistException(ConstantUtil.FIELD_NAME, productDto.getName());
         }
      }
      return productMapper.toDto(productRepository.save(product));
   }

   @Override
   public void delete(final Long id) {
      final Product product = productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ConstantUtil.FIELD_ID, id));
      productRepository.delete(product);
   }
}
