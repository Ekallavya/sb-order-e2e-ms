package org.universe.opensky.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.universe.opensky.product.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}