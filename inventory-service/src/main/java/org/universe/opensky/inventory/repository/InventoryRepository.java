package org.universe.opensky.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.universe.opensky.inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkucodeAndQuantityIsGreaterThanEqual(String skuCode, int quantity);
}