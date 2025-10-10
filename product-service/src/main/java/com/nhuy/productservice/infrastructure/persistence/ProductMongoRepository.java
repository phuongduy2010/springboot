package com.nhuy.productservice.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductMongoRepository extends MongoRepository<ProductEntity,String> {
}
