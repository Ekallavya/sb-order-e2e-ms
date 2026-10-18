package org.universe.opensky.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.universe.opensky.order.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}