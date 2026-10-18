package org.universe.opensky.gateway.dto;

import java.math.BigDecimal;

public record OrderRequest(Long id, String skucode, BigDecimal price, Integer quantity,User user) {
}