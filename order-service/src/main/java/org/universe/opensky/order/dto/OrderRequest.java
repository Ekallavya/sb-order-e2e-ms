package org.universe.opensky.order.dto;


import java.math.BigDecimal;

public record OrderRequest(Long id, String skucode, BigDecimal price, Integer quantity,User user) {
}