package dev.sagar.reactor.coffeeapp.repository;

import dev.sagar.reactor.coffeeapp.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
