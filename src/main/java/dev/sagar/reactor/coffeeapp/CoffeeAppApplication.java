package dev.sagar.reactor.coffeeapp;

import dev.sagar.reactor.coffeeapp.handler.ProductHandler;
import dev.sagar.reactor.coffeeapp.model.Product;
import dev.sagar.reactor.coffeeapp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@SpringBootApplication
public class CoffeeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeAppApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductRepository productRepository){
		return args -> {
			Flux<Product> productFlux = Flux.just(
					new Product(null, "Big Latte", 2.99),
					new Product(null, "Big Decaf", 2.49),
					new Product(null,"Green Tea", 1.99)
			).flatMap(productRepository::save);

			productFlux.thenMany(productRepository.findAll())
					.subscribe(System.out::println);
		};
	}

	@Bean
	RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
//		return route()
//				.path("/supplies",
//						builder -> builder.nest(accept(MediaType.APPLICATION_JSON),
//								nestedBuilder ->
//									nestedBuilder
//											.GET("/products", productHandler::getAllProducts)
//											.GET("/products/{id}", productHandler::getProduct)
//						)
//				).build();

		//alternate way of routing. Both the above approach and the below approach works.
		return route()
				.GET("/supplies/products", accept(MediaType.APPLICATION_JSON), productHandler::getAllProducts)
				.GET("/supplies/products/{id}", accept(MediaType.APPLICATION_JSON), productHandler::getProduct)
				.build();
	}

}
