package org.universe.opensky.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.universe.opensky.inventory.repository.InventoryRepository;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skucode, Integer quantity) {
        return inventoryRepository.existsBySkucodeAndQuantityIsGreaterThanEqual(skucode, quantity);
    }
}