package com.linktic.challengelinktic.business.service;

import java.util.List;

import com.linktic.challengelinktic.business.dto.ProductDto;

public interface ProductService {

   List<ProductDto> getProducts();

   ProductDto productById(final Long id);

   ProductDto save(final Long id, final ProductDto productDto);

   void delete(final Long id);

}
