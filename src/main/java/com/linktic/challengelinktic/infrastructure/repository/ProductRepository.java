package com.linktic.challengelinktic.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.linktic.challengelinktic.infrastructure.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

   @Query("SELECT p FROM Product p WHERE UPPER(p.name) = UPPER(:name)")
   Optional<Product> findProductByName(@Param("name") final String name);

   @Query("SELECT p FROM Product p WHERE UPPER(p.name) = UPPER(:name) AND p.id <> :id ")
   Optional<Product> findProductByNameWithoutId(@Param("name") final String name, @Param("id") final Long id);
}
