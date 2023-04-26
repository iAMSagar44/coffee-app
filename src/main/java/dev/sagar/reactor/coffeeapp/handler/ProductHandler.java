package dev.sagar.reactor.coffeeapp.handler;

import dev.sagar.reactor.coffeeapp.model.Product;
import dev.sagar.reactor.coffeeapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//This class is to demonstrate the use of Handler functions to expose REST APIs
@Component
public class ProductHandler {

    private  final ProductRepository productRepository;

    @Autowired
    public ProductHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest serverRequest) {
        Flux<Product> products = productRepository.findAll();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products, Product.class);
    }

    public Mono<ServerResponse> getProduct(ServerRequest serverRequest) {
        Mono<Product> product = productRepository.findById(serverRequest.pathVariable("id"));

        return product
                .flatMap(p ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(p))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
