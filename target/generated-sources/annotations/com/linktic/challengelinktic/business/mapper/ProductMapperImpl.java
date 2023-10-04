package com.linktic.challengelinktic.business.mapper;

import com.linktic.challengelinktic.business.dto.ProductDto;
import com.linktic.challengelinktic.business.dto.ProductDto.ProductDtoBuilder;
import com.linktic.challengelinktic.infrastructure.entity.Product;
import com.linktic.challengelinktic.infrastructure.entity.Product.ProductBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-03T21:40:22-0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.19 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductBuilder product = Product.builder();

        product.id( dto.getId() );
        product.name( dto.getName() );
        product.description( dto.getDescription() );
        product.price( dto.getPrice() );

        return product.build();
    }

    @Override
    public ProductDto toDto(Product entity) {
        if ( entity == null ) {
            return null;
        }

        ProductDtoBuilder productDto = ProductDto.builder();

        productDto.id( entity.getId() );
        productDto.name( entity.getName() );
        productDto.description( entity.getDescription() );
        productDto.price( entity.getPrice() );

        return productDto.build();
    }

    @Override
    public List<ProductDto> toDto(List<Product> entity) {
        if ( entity == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( entity.size() );
        for ( Product product : entity ) {
            list.add( toDto( product ) );
        }

        return list;
    }
}
