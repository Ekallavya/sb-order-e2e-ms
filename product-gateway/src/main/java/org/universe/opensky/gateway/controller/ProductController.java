package org.universe.opensky.gateway.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.universe.opensky.gateway.dto.OrderRequest;
import org.universe.opensky.gateway.dto.ProductResponse;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/home")
    public String home(){
        return "welcome to Product Gateway Service";
    }
    @GetMapping("/api/product/allProducts")
    public List<ProductResponse> fetchProducts(){
        RestClient restClient = RestClient.builder().build();

        List<ProductResponse> response = restClient.get()
                .uri("http://localhost:8081/product-service/api/product")
                .retrieve()
                //.body(ProductResponse.class);
        .body(new ParameterizedTypeReference<List<ProductResponse>>() {});
        return response;
    }
    @PostMapping("/api/order/saveOrder")
    public String saveOrder(@RequestBody OrderRequest orderRequest){
        RestClient restClient = RestClient.builder().build();

        ResponseEntity<String> response = restClient.post()
                .uri("http://localhost:8082/order-service/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderRequest) // Request body
                .retrieve()
                .toEntity(String.class); // Maps to a ResponseEntity

        return response.getBody();

        //return "welcome to Product Gateway Service";
    }
}
