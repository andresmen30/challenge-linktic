package com.linktic.challengelinktic.business.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.linktic.challengelinktic.business.dto.ProductDto;
import com.linktic.challengelinktic.infrastructure.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

   Product toEntity(final ProductDto dto);

   ProductDto toDto(final Product entity);

   List<ProductDto> toDto(final List<Product> entity);

}
