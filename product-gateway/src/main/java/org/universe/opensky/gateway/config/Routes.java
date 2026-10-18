//package org.universe.opensky.gateway.config;
//
//import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
//import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.function.RequestPredicates;
//import org.springframework.web.servlet.function.RouterFunction;
//import org.springframework.web.servlet.function.ServerResponse;
//
//import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;
//
//@Configuration
//public class Routes {
//    @Bean
//    public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
//        return GatewayRouterFunctions.route("product_service_swagger")
//                .route(RequestPredicates.path("/aggregate/product-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
//                //.filter(setPath("/api-docs"))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
//        return GatewayRouterFunctions.route("order_service_swagger")
//                .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
//                //.filter(setPath("/api-docs"))
//                .build();
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
//        return GatewayRouterFunctions.route("inventory_service_swagger")
//                .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8083"))
//                //.filter(setPath("/api-docs"))
//                .build();
//    }
//}
