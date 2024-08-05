package com.org.project.shreyaa.repository;
import com.org.project.shreyaa.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}

